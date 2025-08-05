package com.bank.auth.service.impl;

import com.bank.auth.dto.LoginRequest;
import com.bank.auth.dto.LoginResponse;
import com.bank.auth.dto.RegisterRequest;
import com.bank.auth.dto.RegisterResponse;
import com.bank.auth.proxy.CustomerProxy;
import com.bank.auth.service.AuthService;
import com.bank.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final CustomerProxy customerProxy;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public RegisterResponse registerCustomer(RegisterRequest request) {
        try {
            log.info("Registering customer with email: {}", request.getEmail());
            
            // Call customer service to register
            ResponseEntity<Object> response = customerProxy.registerCustomer(request);
            
            if (response.getStatusCode().is2xxSuccessful()) {
                // Extract customer ID from response
                // This assumes the customer service returns a map with customerId
                @SuppressWarnings("unchecked")
                java.util.Map<String, Object> responseBody = (java.util.Map<String, Object>) response.getBody();
                
                if (responseBody != null && responseBody.containsKey("customerId")) {
                    String customerId = (String) responseBody.get("customerId");
                    return new RegisterResponse(customerId, "Customer registered successfully", true);
                }
            }
            
            return new RegisterResponse(null, "Failed to register customer", false);
            
        } catch (Exception e) {
            log.error("Error registering customer: {}", e.getMessage());
            return new RegisterResponse(null, "Registration failed: " + e.getMessage(), false);
        }
    }

    @Override
    public LoginResponse loginCustomer(LoginRequest request) {
        try {
            log.info("Attempting login for email: {}", request.getEmail());
            
            // Get customer by email from customer service
            ResponseEntity<Object> response = customerProxy.getCustomerByEmail(request.getEmail());
            
            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                return new LoginResponse(null, null, null, null, null, null, null, "Invalid email or password");
            }
            
            @SuppressWarnings("unchecked")
            java.util.Map<String, Object> customer = (java.util.Map<String, Object>) response.getBody();
            
            // Verify password
            String storedPassword = (String) customer.get("password");
            if (!passwordEncoder.matches(request.getPassword(), storedPassword)) {
                return new LoginResponse(null, null, null, null, null, null, null, "Invalid email or password");
            }
            
            // Check if customer is approved
            String status = (String) customer.get("status");
            if (!"APPROVED".equals(status)) {
                return new LoginResponse(null, null, null, null, null, null, null, "Account not approved. Please complete KYC verification.");
            }
            
            // Generate tokens
            String customerId = (String) customer.get("customerId");
            String email = (String) customer.get("email");
            String token = jwtUtil.generateToken(customerId, email);
            String refreshToken = jwtUtil.generateRefreshToken(customerId, email);
            
            // Store refresh token in Redis
            redisTemplate.opsForValue().set("refresh_token:" + customerId, refreshToken, 7, TimeUnit.DAYS);
            
            return new LoginResponse(
                token,
                refreshToken,
                customerId,
                (String) customer.get("firstName"),
                (String) customer.get("lastName"),
                email,
                status,
                "Login successful"
            );
            
        } catch (Exception e) {
            log.error("Error during login: {}", e.getMessage());
            return new LoginResponse(null, null, null, null, null, null, null, "Login failed: " + e.getMessage());
        }
    }

    @Override
    public LoginResponse refreshToken(String refreshToken) {
        try {
            if (!jwtUtil.isRefreshToken(refreshToken) || !jwtUtil.validateToken(refreshToken)) {
                return new LoginResponse(null, null, null, null, null, null, null, "Invalid refresh token");
            }
            
            String customerId = jwtUtil.extractCustomerId(refreshToken);
            String email = jwtUtil.extractEmail(refreshToken);
            
            // Verify refresh token is in Redis
            String storedRefreshToken = redisTemplate.opsForValue().get("refresh_token:" + customerId);
            if (!refreshToken.equals(storedRefreshToken)) {
                return new LoginResponse(null, null, null, null, null, null, null, "Invalid refresh token");
            }
            
            // Generate new tokens
            String newToken = jwtUtil.generateToken(customerId, email);
            String newRefreshToken = jwtUtil.generateRefreshToken(customerId, email);
            
            // Update refresh token in Redis
            redisTemplate.opsForValue().set("refresh_token:" + customerId, newRefreshToken, 7, TimeUnit.DAYS);
            
            return new LoginResponse(
                newToken,
                newRefreshToken,
                customerId,
                null,
                null,
                email,
                null,
                "Token refreshed successfully"
            );
            
        } catch (Exception e) {
            log.error("Error refreshing token: {}", e.getMessage());
            return new LoginResponse(null, null, null, null, null, null, null, "Token refresh failed");
        }
    }

    @Override
    public void logout(String token) {
        try {
            if (jwtUtil.validateToken(token)) {
                String customerId = jwtUtil.extractCustomerId(token);
                // Remove refresh token from Redis
                redisTemplate.delete("refresh_token:" + customerId);
                // Add token to blacklist
                redisTemplate.opsForValue().set("blacklist:" + token, "true", 24, TimeUnit.HOURS);
            }
        } catch (Exception e) {
            log.error("Error during logout: {}", e.getMessage());
        }
    }
}