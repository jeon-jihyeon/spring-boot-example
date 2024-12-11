package com.example.spring.boot.core;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ApiLoggingFilter extends OncePerRequestFilter {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String path = request.getRequestURI();
        final String query = request.getQueryString();

        filterChain.doFilter(request, response);

        log.info(String.format(
                "[%s] %s (%s)",
                request.getMethod(),
                path + (query != null ? "?" + query : ""),
                response.getStatus()
        ));
    }
}
