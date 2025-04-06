# Example Query

조회 로직의 Query 프로젝트 입니다.

## 프로젝트 구조

- module-api: 컨트롤러 등의 외부 인터페이스
- module-common: 공통 모듈
- module-domain: 도메인 계층
- module-feign: API 클라이언트 구현체
- module-mongodb: MongoDB 구현체
- module-sqs: 이벤트 수신을 위한 sqs 구현체

## 아키텍처

![alt text](https://github.com/jeon-jihyeon/spring-boot-example/blob/main/images/example-query.png?raw=true)