# Spring Boot Example

이 레포지토리는 Spring Boot 예제 프로젝트를 포함하고 있습니다.

## 프로젝트

- example-command:
- example-query:
- example-batch:

## 아키텍처

1. 도메인 단위로 분리하고, 도메인들은 메세지 큐를 통해 상호작용을 합니다.
2. Command와 Query를 분리하고, 서로 메세지 큐를 통해 상호작용을 합니다.
3. 각 서비스들이 분리되어 있다 가정하여, Zero-Payload 정책을 적용하였습니다.
4. 메시지 전송을 위한 Outbox Event 생성을 비즈니스 로직 내 하나의 트랜잭션으로 묶어 원자성을 보장하였습니다.
5. HibernateListener에서 메시지를 전송하여 성능 영향을 최소화 하였습니다.
6. Outbox Event 배치처리를 통해 At-Least Once 전송을 보장하였습니다.
7. 수신 완료 처리의 HibernateListener에서 Inbox Event를 생성하여 Exactly-Once 처리 여부를 추적할 수 있도록 하였습니다.

## 기술

- Java 17 이후 버전
- JPA, QueryDSL, Batch, JDBC
- AWS SQS, MySQL, OpenFeign, MongoDB
- 클라우드 환경을 위한 LocalStack
