package com.example.core.values;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Unix epoch timestamp in milliseconds.
 * UTC timezone만 지원
 */
public record EpochMillis(Long value) {
    public EpochMillis {
        if (value == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }
    }

    public static EpochMillis from(Long millis) {
        return new EpochMillis(millis);
    }

    public static EpochMillis from(LocalDateTime utc) {
        return new EpochMillis(utc.toInstant(ZoneOffset.UTC).toEpochMilli());
    }

    public LocalDateTime toDateTime() {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneOffset.UTC);
    }
}
