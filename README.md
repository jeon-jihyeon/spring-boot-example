# DDD 예제

이 프로젝트는 멀티 모듈 기반의 스프링 부트 서버 아키텍처를 DDD 방식으로 구성한 예시입니다.  
Bounded Context를 `acquisition`, `analysis`로 나누고, 각 컨텍스트를 `domain / application / infra / api` 레이어로 분리했습니다.

## 모듈

- `modules/common:core` 핵심 값 객체, enum, 예외 등 공통 도메인 요소
- `modules/common:contract` Bounded Context 간 통신을 위한 계약
- `modules/common:infra` 공통 인프라 설정
- `modules/common:app` 스프링 부트 엔트리 포인트 및 AOP/웹 설정

- `modules/bounded-context/acquisition:domain` 수집 도메인 모델, 집계 로직
- `modules/bounded-context/acquisition:application` 유스케이스, 인프라 인터페이스
- `modules/bounded-context/acquisition:infra` JPA 등 인프라 구현체
- `modules/bounded-context/acquisition:api` 계약 구현체(Facade)

- `modules/bounded-context/analysis:domain` 분석 지표/계산 로직
- `modules/bounded-context/analysis:application` 분석 유스케이스와 포트
- `modules/bounded-context/analysis:infra` 인프라 구현(미구현)
- `modules/bounded-context/analysis:api` API/Facade(미구현)

## 주요 의존성

- Spring Boot, Spring Context
- Spring Web, Spring AOP
- Spring Data JPA, Spring Data Redis
- MySQL Driver

## Bounded Context 간 통신

현재는 `common:contract`에 정의된 계약을 `acquisition:api`의 Facade가 구현하는 방식으로 통신합니다.  
필요 시 REST API, 메시지 기반 통신(Kafka/RabbitMQ 등), gRPC 같은 대안도 사용할 수 있습니다.
