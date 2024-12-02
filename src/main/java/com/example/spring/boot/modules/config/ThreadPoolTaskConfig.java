package com.example.spring.boot.modules.config;

import com.example.spring.boot.core.exception.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@EnableAsync
@Configuration
public class ThreadPoolTaskConfig implements AsyncConfigurer {
    private final ThreadPoolProperties properties;

    public ThreadPoolTaskConfig(ThreadPoolProperties properties) {
        this.properties = properties;
    }

    @Override
    public Executor getAsyncExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("async-");
        executor.setThreadGroupName("async-group");
        executor.setCorePoolSize(properties.coreSize);
        executor.setMaxPoolSize(properties.maxSize);
        executor.setQueueCapacity(properties.queueCapacity);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(10);
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new ThreadPoolTaskExceptionHandler();
    }

    public static final class ThreadPoolTaskExceptionHandler implements AsyncUncaughtExceptionHandler {
        private final Logger log = LoggerFactory.getLogger(getClass());

        @Override
        public void handleUncaughtException(@NonNull Throwable e, @NonNull Method method, @NonNull Object... params) {
            if (e instanceof ApiException) {
                switch (((ApiException) e).getLogLevel()) {
                    case ERROR -> log.error("ApiException : {}", e.getMessage(), e);
                    case WARN -> log.warn("ApiException : {}", e.getMessage(), e);
                    default -> log.info("ApiException : {}", e.getMessage(), e);
                }
            } else {
                log.error("Exception : {}", e.getMessage(), e);
            }
        }

    }

    @ConfigurationProperties(prefix = "spring.task.execution.pool")
    public record ThreadPoolProperties(Integer coreSize, Integer maxSize, Integer queueCapacity) {
    }
}
