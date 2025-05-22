package com.shoestore.client.configs;

import io.github.resilience4j.retry.RetryRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResilienceDebugConfig {

    private final RetryRegistry retryRegistry;

    public ResilienceDebugConfig(RetryRegistry retryRegistry) {
        this.retryRegistry = retryRegistry;
    }

    @PostConstruct
    public void checkRetryConfig() {
        System.out.println("📢 [ResilienceDebug] Retry instances configured:");
        retryRegistry.getAllRetries().forEach(retry ->
                System.out.println("👉 Tên retry: " + retry.getName())
        );
    }
}

