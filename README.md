# Spring Boot Example

이 레포지토리는 Spring Boot 예제 프로젝트를 포함하고 있습니다.

## 프로젝트 구조

- module-domain: 도메인 모델 및 핵심 비즈니스 로직들을 Command, Query 단위로 분리한 모듈.
- module-application: API, Batch 애플리케이션 관련 모듈
- module-infrastructure: DB, Queue, Client 등 인프라스트럭처 관련 모듈
- 도메인들과 Command 및 Query를 편의를 위해 하나의 프로젝로 표현하였습니다.

## 아키텍처

![alt text](https://github.com/jeon-jihyeon/spring-boot-example/blob/main/images/architecture.png?raw=true)

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

## 환경변수

```dotenv
# to expose ports in Docker on running containers
COMMAND_DB_PORT=
BATCH_DB_PORT=
INBOX_DB_PORT=
OUTBOX_DB_PORT=
LOCALSTACK_PORT=

# base path to mount external volumes in docker containers
COMMAND_VOLUME_BASE=
BATCH_VOLUME_BASE=
INBOX_VOLUME_BASE=
OUTBOX_VOLUME_BASE=
LOCALSTACK_VOLUME_BASE=
RDB_IMAGE=      # ex) mysql:8
RDB_OPTION=     # ex) --character-set-server=utf8mb4

# data source properties
COMMAND_DB_DRIVER=
COMMAND_DB_URL=
COMMAND_DB_USERNAME=
COMMAND_DB_PASSWORD=
BATCH_DB_DRIVER=
BATCH_DB_URL=
BATCH_DB_USERNAME=
BATCH_DB_PASSWORD=
BATCH_JOB_NAME=
QUERY_DB_URI=
QUERY_DB_HOST=
QUERY_DB_PORT=
QUERY_DB_USERNAME=
QUERY_DB_PASSWORD=

# message queue properties
MESSAGE_TEAM_QUEUE=
MESSAGE_PLAYER_QUEUE=
MESSAGE_DELETE_TEAM_QUEUE=
MESSAGE_TYPE_KEY=
MESSAGE_ENDPOINT_URL=
MESSAGE_DEFAULT_REGION=
MESSAGE_ACCESS_KEY_ID=
MESSAGE_SECRET_ACCESS_KEY=
COMMAND_API_URL=
QUERY_API_URL=
```