package com.example.spring.domain;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see <a href="https://helloworld.kurly.com/blog/distributed-redisson-lock/">참고자료</a>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedLock {

    /**
     * 락의 이름
     */
    String key();

    /**
     * 락을 획득 대기 시간(ms)
     * 락 획득을 위해 waitTime 만큼 대기
     * (default : 3000)
     */
    int waitTimeout() default 3000;

    /**
     * 락 유지 시간(ms)
     * duration 만큼 지나면 락을 해제
     * (default : 3000)
     */
    int duration() default 3000;
}
