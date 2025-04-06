# Example Command

명령 로직의 Command 프로젝트 입니다.

## 프로젝트 구조

- module-api: 컨트롤러 등의 외부 인터페이스
- module-domain: 도메인 계층
- module-mysql: JPA 구현체
- module-redis: 분산락을 위한 Redis 구현체
- module-sqs: 이벤트 전송을 위한 sqs 구현체

## 아키텍처

![alt text](https://github.com/jeon-jihyeon/spring-boot-example/blob/main/images/example-command.png?raw=true)

