# Spring Boot Example

이 레포지토리는 Spring Boot 예제 프로젝트를 포함하고 있습니다.

## 아키텍처

- Command와 Query를 분리하고, 메시지 큐로 이벤트를 전달해 데이터 동기화
- Zero-Payload 정책를 적용하고, API 클라이언트로 이벤트 데이터 요청
- 메시지 전송을 위한 Outbox Event 생성과 비즈니스 로직을 하나의 트랜잭션에서 실행해 원자성 보장
- HibernateEventListener에서 메시지 전송 메서드 호출
- 실패한 Outbox Event 재전송을 통해 At-Least Once 전송을 보장
- 이벤트에 고유 ID를 부여해 중복 처리 확인

### example-command

![alt text](https://github.com/jeon-jihyeon/spring-boot-example/blob/cqrs/images/example-command.png?raw=true)

### example-query

![alt text](https://github.com/jeon-jihyeon/spring-boot-example/blob/cqrs/images/example-query.png?raw=true)

### example-batch

(작성중)

## 기술

- Java 17 이후 버전
- JPA, QueryDSL, Batch, JDBC
- AWS SQS, MySQL, OpenFeign, MongoDB
- 클라우드 환경을 위한 LocalStack
