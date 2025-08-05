package com.bank.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Auth Service Routes
                .route("auth-service", r -> r
                        .path("/auth/**")
                        .uri("lb://auth-service"))
                
                // KYC Service Routes
                .route("kyc-service", r -> r
                        .path("/kyc/api/**")
                        .uri("lb://kyc-service"))
                
                // Customer Service Routes
                .route("customer-service", r -> r
                        .path("/customers/**")
                        .uri("lb://customer-service"))
                
                // Account Service Routes
                .route("account-service", r -> r
                        .path("/account-api/**")
                        .uri("lb://account-service"))
                
                // Admin Service Routes
                .route("admin-service", r -> r
                        .path("/admin/**")
                        .uri("lb://admin-service"))
                
                // Health check routes
                .route("auth-health", r -> r
                        .path("/health/auth")
                        .filters(f -> f.rewritePath("/health/auth", "/auth/health"))
                        .uri("lb://auth-service"))
                
                .route("kyc-health", r -> r
                        .path("/health/kyc")
                        .filters(f -> f.rewritePath("/health/kyc", "/kyc/api/health"))
                        .uri("lb://kyc-service"))
                
                .route("customer-health", r -> r
                        .path("/health/customer")
                        .filters(f -> f.rewritePath("/health/customer", "/customers/hello"))
                        .uri("lb://customer-service"))
                
                .route("account-health", r -> r
                        .path("/health/account")
                        .filters(f -> f.rewritePath("/health/account", "/account-api/accounts/health"))
                        .uri("lb://account-service"))
                
                .route("admin-health", r -> r
                        .path("/health/admin")
                        .filters(f -> f.rewritePath("/health/admin", "/admin/api/health"))
                        .uri("lb://admin-service"))
                
                .build();
    }
}
