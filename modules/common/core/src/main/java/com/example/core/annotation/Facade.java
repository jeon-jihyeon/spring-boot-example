package com.example.core.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Bounded Context의 진입점(Facade)을 표시하는 애노테이션
 *
 * <p>이 애노테이션은 다음과 같은 목적으로 사용:
 * <ul>
 *   <li>Bounded Context 간 통신의 명시적 경계 표현</li>
 *   <li>AOP를 통한 cross-context 호출 로깅</li>
 *   <li>아키텍처 레이어의 명확한 구분</li>
 * </ul>
 *
 * <p>사용 예시:
 * <pre>
 * {@code
 * @Facade(boundedContext = "acquisition")
 * public class AcquisitionFacade {
 *     public List<Result> getResults(ResultCriteria criteria) { }
 * }
 * }
 * </pre>
 *
 * @see Component
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Facade {
    /**
     * Bounded Context 이름
     * <p>생략 시 클래스 이름에서 추론
     *
     * @return bounded context 식별자
     */
    String boundedContext() default "";
}