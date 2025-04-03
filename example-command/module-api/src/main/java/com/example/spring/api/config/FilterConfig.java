package com.example.spring.api.config;

import com.example.spring.api.core.ApiLoggingFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<Filter> apiLoggingFilter() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new ApiLoggingFilter());
        bean.setOrder(1);
        bean.addUrlPatterns("/*");
        return bean;
    }
}
