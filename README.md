# Spring Boot DDD 멀티모듈 아키텍처

> **DDD(Domain-Driven Design)** 기반 멀티모듈 구조의 Spring Boot 예제 프로젝트
> 개인 프로젝트의 아키텍처를 학습용으로 단순화한 레퍼런스 구현체

```
Acquisition BC ──(implements)──▶ Contract ◀──(uses)── Derivation BC
      │                                                     │
      └──────────────▶ Common Modules ◀─────────────────────┘
                     (Core, Infra, App)
```

---

## 목차

- [프로젝트 개요](#프로젝트-개요)
- [기술 스택](#기술-스택)
- [아키텍처](#아키텍처)
- [모듈 구조](#모듈-구조)
- [도메인 플로우](#도메인-플로우)
- [고도화 포인트 (vs NOA)](#고도화-포인트-vs-noa)
- [테스트 전략](#테스트)
- [빌드 및 실행](#빌드-및-실행)

---

## 프로젝트 개요

이 프로젝트는 **DDD(Domain-Driven Design)** 원칙에 따라 설계된 Spring Boot 멀티모듈 아키텍처의 학습용 예제입니다.

### 핵심 특징

| 특징 | 설명 |
|------|------|
| **Bounded Context 분리** | 비즈니스 도메인을 독립적인 컨텍스트로 격리 |
| **4계층 아키텍처** | `domain` → `application` → `infra` → `api` 계층 분리 |
| **Contract 인터페이스** | BC 간 통신 계약 정의 |
| **Facade** | Contract를 구현하는 BC 진입점 |
| **테스트 커버리지** | JaCoCo 기반 라인 커버리지 100% |

---

## 기술 스택

- Java 17
- Spring Boot 3.3.3
- Gradle (Multi-Module)
- Spring Data JPA + Hibernate
- H2 (dev) / MySQL (prod)
- JUnit 5 + Mockito + JaCoCo

---

## 아키텍처

### 계층 구조 (Layered Architecture per BC)

각 Bounded Context는 **4계층 구조**를 따릅니다:

```
API Layer          @Facade, @Controller, DTO
     ↓
Application Layer  Use Case, Port(Interface)
     ↓
Infra Layer        JPA Adapter, Entity, Repository
     ↓
Domain Layer       Aggregate, Value Object, Exception
```

### 의존성 규칙

```
api ──────▶ application ──────▶ domain
  │              │                 ▲
  │              │                 │
  ▼              ▼                 │
infra ──────────────────────────────
                │
                ▼
             common
```

- **Domain**: 순수 비즈니스 로직, 외부 의존성 없음
- **Application**: 유스케이스 구현, 포트(인터페이스) 정의
- **Infra**: 포트 구현체, JPA/외부 API 연동
- **API**: REST 컨트롤러, Facade 구현

---

## 모듈 구조

### 전체 모듈 트리

```
modules/
├── common/                          # 공통 모듈
│   ├── core/                        # 핵심 값 객체, 예외, 어노테이션
│   ├── contract/                    # BC 간 통신 계약
│   ├── infrastructure/              # JPA, DataSource 설정
│   └── app/                         # Spring Boot 진입점
│
└── bounded-context/                 # 비즈니스 도메인
    ├── acquisition-domain/          # 수집 도메인 모델
    ├── acquisition-application/     # 수집 유스케이스
    ├── acquisition-infra/           # 수집 인프라 구현
    ├── acquisition-api/             # 수집 Facade
    │
    ├── derivation-domain/           # 분석 도메인 모델
    ├── derivation-application/      # 분석 유스케이스
    ├── derivation-infra/            # 분석 인프라 구현
    └── derivation-api/              # 분석 API 컨트롤러
```

### 모듈별 상세

#### Common Modules

| 모듈 | 역할 | 주요 컴포넌트 |
|------|------|--------------|
| `core` | 공유 도메인 요소 | `Price`, `Symbol`, `OHLCV`, `Timeframe`, `@Facade` |
| `contract` | BC 간 통신 인터페이스 | `AcquisitionContract`, Request/Response DTO |
| `infrastructure` | 기술 인프라 | `BaseJpaEntity`, `JpaConfiguration` |
| `app` | 애플리케이션 진입점 | `ExampleApplication`, `LogAdvice` |

#### Acquisition BC (데이터 수집)

| 계층 | 주요 클래스 | 설명 |
|------|------------|------|
| domain | `Candle`, `CandleAggregator` | 시장 캔들 데이터 및 집계 로직 |
| application | `GetCandles`, `CandlesFinder` | 캔들 조회 유스케이스 및 포트 |
| infra | `CandleJpaAdapter`, `CandleEntity` | JPA 영속성 구현 |
| api | `AcquisitionFacade` | Contract 구현체 (BC 진입점) |

#### Derivation BC (기술 지표 분석)

| 계층 | 주요 클래스 | 설명 |
|------|------------|------|
| domain | `Indicator`, `Ema`, `Macd` | 기술 지표 모델 |
| application | `GetIndicators`, `CandleFetcher` | 지표 계산 유스케이스 |
| infra | `AcquisitionCandleFetcher` | Acquisition BC 연동 어댑터 |
| api | `DerivationController` | REST API 엔드포인트 |

---

## 도메인 플로우

### Acquisition BC 플로우

```
Request → AcquisitionFacade → GetCandles → CandlesFinder → CandleJpaAdapter
                                  ↓
                           CandleAggregator
                                  ↓
                           List<Candle> → Response
```

### Derivation BC 플로우

```
Request → DerivationController → GetIndicators → CandleFetcher
                                       ↓              ↓
                                  Calculators    AcquisitionContract
                                  (EMA/MACD)          ↓
                                       ↓         AcquisitionFacade
                                 List<Indicator> → Response
```

### BC 간 통신

```
Derivation BC                          Acquisition BC
     │                                       ↑
     │  uses                      implements │
     ↓                                       │
   CandleFetcher ──→ AcquisitionContract ←── AcquisitionFacade
                     (common:contract)
```

---

## 고도화 포인트

> 개인 프로젝트의 아키텍처를 학습용으로 단순화한 버전입니다.

### 원조 프로젝트 vs 현재

| 항목 | 원조 프로젝트 | 현재 (학습용) |
|------|------------|--------------|
| **BC 수** | 9개 | 2개 |
| **오케스트레이션** | Orchestrator 중앙 제어 | Contract 기반 통신 |
| **상태 머신** | 주문 상태 관리 | 미구현 |
| **Idempotency** | Key 기반 재시도 | 미구현 |
| **감사 로그** | Append-Only Ledger | 미구현 |

### 원조 프로젝트의 오케스트레이션 방식

```
Scheduler → Orchestrator
                 │
    ┌────────────┼────────────┐
    ▼            ▼            ▼
Acquisition → Derivation → Inference → Ledger
```

- **Trading BC**가 전체 트레이딩 사이클을 조율
- 각 BC는 독립적이며 Orchestrator를 통해서만 호출됨
- 실패 시 Idempotency Key로 안전하게 재시도

### 고도화 방향

```
현재 (2 BC) → Orchestrator BC 추가 → Event-Driven + CQRS/ES
```

---

## 테스트

### 테스트 커버리지

```
┌────────────────────────────────────────────────────────────┐
│                    JaCoCo Coverage Report                  │
├────────────────────────────────────────────────────────────┤
│                                                            │
│   Module                           Line Coverage           │
│   ─────────────────────────────────────────────────        │
│   common:core                      ████████████████ 100%   │
│   acquisition-domain               ████████████████ 100%   │
│   acquisition-application          ████████████████ 100%   │
│   acquisition-infra                ████████████████ 100%   │
│   acquisition-api                  ████████████████ 100%   │
│   derivation-domain                ████████████████ 100%   │
│   derivation-application           ████████████████ 100%   │
│   derivation-infra                 ████████████████ 100%   │
│   derivation-api                   ████████████████ 100%   │
│   ─────────────────────────────────────────────────        │
│   TOTAL                            ████████████████ 100%   │
│                                                            │
└────────────────────────────────────────────────────────────┘
```

### 테스트 실행

```bash
# 전체 테스트 실행
./gradlew test

# 특정 모듈 테스트
./gradlew :modules:bounded-context:derivation-domain:test

# 커버리지 리포트 생성
./gradlew jacocoTestReport

# HTML 리포트 위치
# build/reports/jacoco/test/html/index.html
```

---

## 빌드 및 실행

### 요구사항

- Java 17+
- Gradle 8.x

### 빌드

```bash
# 전체 빌드
./gradlew build

# 클린 빌드
./gradlew clean build

# 테스트 제외 빌드
./gradlew build -x test
```

### 실행

```bash
# 애플리케이션 실행
./gradlew :modules:common:app:bootRun

# JAR 실행
java -jar modules/common/app/build/libs/app-*.jar
```

### 환경 변수 (MySQL 사용 시)

```bash
export RDB_HOST=localhost
export RDB_PORT=3306
export RDB_DATABASE=example
export RDB_USERNAME=root
export RDB_PASSWORD=password
```

---

## 라이선스

MIT License

---

<p align="center">
  <sub>Built with DDD principles, inspired by NOA architecture</sub>
</p>
