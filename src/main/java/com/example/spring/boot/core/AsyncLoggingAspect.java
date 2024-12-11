package com.example.spring.boot.core;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AsyncLoggingAspect {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Around("@annotation(org.springframework.scheduling.annotation.Async)")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        String typeName = joinPoint.getSignature().getDeclaringTypeName();
        try {
            Object result = joinPoint.proceed();
            log.info(String.format("[Async] %s", typeName));
            return result;
        } catch (Exception e) {
            log.error(String.format("[Async] %s, %s", typeName, e.getMessage()));
            return e;
        }
    }
}