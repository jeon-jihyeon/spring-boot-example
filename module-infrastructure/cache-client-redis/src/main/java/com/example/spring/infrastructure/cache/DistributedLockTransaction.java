package com.example.spring.infrastructure.cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @see <a href="https://helloworld.kurly.com/blog/distributed-redisson-lock/">참고자료</a>
 */
@Component
public class DistributedLockTransaction {
    @Transactional(transactionManager = "commandTransactionManager", propagation = Propagation.REQUIRES_NEW)
    public Object proceed(final ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }
}
