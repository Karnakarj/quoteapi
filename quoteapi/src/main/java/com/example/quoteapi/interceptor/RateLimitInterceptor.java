package com.example.quoteapi.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.quoteapi.service.RateLimitService;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RateLimitInterceptor.class);
    private final RateLimitService rateLimitService;

    public RateLimitInterceptor(RateLimitService rateLimitService) {
        this.rateLimitService = rateLimitService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String ipAddress = extractClientIp(request);
        Bucket bucket = rateLimitService.getBucket(ipAddress);
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);

        if (probe.isConsumed()) {
            // allowed -- log OK (200). We'll log here; controller will still return 200.
            logger.info("IP: {} | PATH: {} | STATUS: {}", ipAddress, request.getRequestURI(), HttpStatus.OK.value());
            return true;
        } else {
            long waitForRefillSeconds = probe.getNanosToWaitForRefill() / 1_000_000_000L;
            if (waitForRefillSeconds < 1) waitForRefillSeconds = 1;
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json");
            String body = String.format("{\"error\": \"Rate limit exceeded. Try again in %d seconds.\"}", waitForRefillSeconds);
            response.getWriter().write(body);
            logger.info("IP: {} | PATH: {} | STATUS: {}", ipAddress, request.getRequestURI(), HttpStatus.TOO_MANY_REQUESTS.value());
            return false;
        }
    }

    private String extractClientIp(HttpServletRequest request) {
        String xf = request.getHeader("X-Forwarded-For");
        if (xf != null && !xf.isBlank()) {
            return xf.split(",")[0].trim();
        }
        String ip = request.getRemoteAddr();
        return ip == null ? "unknown" : ip;
    }
}
