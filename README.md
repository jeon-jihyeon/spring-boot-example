# Spring Boot Example

This repository provides an illustrative example of a Spring Boot application, demonstrating a
modular architecture for scalable and maintainable development. The project is divided into three core modules

## Architecture

![alt text](https://github.com/jeon-jihyeon/spring-boot-example/blob/main/images/command.png?raw=true)

### module-domain

- Defines the core business logic and entities of the application.
- Responsible for encapsulating domain rules and operations.

### module-application

- Implements application-specific functionality by utilizing domain logic.
- Acts as the service layer that orchestrates operations between the domain and external interfaces.

### module-infrastructure

- Manages integration with external systems such as databases, messaging queues, or third-party APIs.
- Handles data persistence and retrieval.

## Prerequisites

- Java 17 or later
- Docker (optional for containerized setup)

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
INBOX_DB_DRIVER=
INBOX_DB_URL=
INBOX_DB_USERNAME=
INBOX_DB_PASSWORD=
OUTBOX_DB_DRIVER=
OUTBOX_DB_URL=
OUTBOX_DB_USERNAME=
OUTBOX_DB_PASSWORD=
QUERY_DB_HOST=
QUERY_DB_PORT=
QUERY_DB_USERNAME=
QUERY_DB_PASSWORD=

# message queue properties
MESSAGE_ENDPOINT_URL=
MESSAGE_DEFAULT_REGION=
MESSAGE_ACCESS_KEY_ID=
MESSAGE_SECRET_ACCESS_KEY=
TEAM_TOPIC_ARN=
PLAYER_TOPIC_ARN=

COMMAND_API_URL=
```