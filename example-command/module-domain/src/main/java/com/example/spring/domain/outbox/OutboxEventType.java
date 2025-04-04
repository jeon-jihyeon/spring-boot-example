package com.example.spring.domain.outbox;

/**
 * Outbox를 통해 발행되는 주요 도메인 이벤트 유형을 정의합니다.
 * 이벤트 타입은 메시지 큐 또는 이벤트 브로커의 소비자 측 분기 기준이 됩니다.
 */
public enum OutboxEventType {
    PLAYER_CREATED,
    PLAYER_POINT_ADDED,
    PLAYER_DELETED
}
