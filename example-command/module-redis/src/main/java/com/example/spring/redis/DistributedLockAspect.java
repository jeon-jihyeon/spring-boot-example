package com.example.spring.redis;

import com.example.spring.domain.DistributedLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @see <a href="https://helloworld.kurly.com/blog/distributed-redisson-lock/">참고자료</a>
 */
@Aspect
@Component
public class DistributedLockAspect {
    private final RedissonClient client;
    private final DistributedLockTransaction transaction;

    public DistributedLockAspect(RedissonClient client, DistributedLockTransaction transaction) {
        this.client = client;
        this.transaction = transaction;
    }

    private static String getDynamicKey(String[] params, Object[] args, String key) {
        final ExpressionParser parser = new SpelExpressionParser();
        final StandardEvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < params.length; i++) context.setVariable(params[i], args[i]);
        return parser.parseExpression(key).getValue(context, String.class);
    }

    @Around("@annotation(com.example.spring.domain.DistributedLock)")
    public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
        final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        final Method method = signature.getMethod();
        final DistributedLock lock = method.getAnnotation(DistributedLock.class);
        final RLock rLock = client.getLock(getDynamicKey(signature.getParameterNames(), joinPoint.getArgs(), lock.key()));

        try {
            if (!rLock.tryLock(lock.waitTimeout(), lock.duration(), TimeUnit.MILLISECONDS)) return false;
            return transaction.proceed(joinPoint);
        } catch (InterruptedException e) {
            throw new InterruptedException();
        } finally {
            rLock.unlock();
        }
    }
}
