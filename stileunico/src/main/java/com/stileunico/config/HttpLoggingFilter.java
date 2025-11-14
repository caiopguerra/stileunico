package com.stileunico.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Component
public class HttpLoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger("HTTP");

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        long start = System.currentTimeMillis();

        // Log da requisição
        logger.info("[REQUEST] {} {}", request.getMethod(), request.getRequestURI());

        // Continua a execução da requisição
        filterChain.doFilter(request, response);

        // Tempo de resposta
        long duration = System.currentTimeMillis() - start;

        // Log da resposta
        logger.info("[RESPONSE] {} {} ({} ms)", response.getStatus(), request.getRequestURI(), duration);
    }
}
