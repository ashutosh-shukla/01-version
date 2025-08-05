package com.bank.auth.controller;

import com.bank.auth.dto.LoginRequest;
import com.bank.auth.dto.LoginResponse;
import com.bank.auth.dto.RegisterRequest;
import com.bank.auth.dto.RegisterResponse;
import com.bank.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Authentication", description = "Authentication management APIs")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new customer", description = "Register a new customer with the system")
    public ResponseEntity<RegisterResponse> registerCustomer(@Valid @RequestBody RegisterRequest request) {
        log.info("Registration request received for email: {}", request.getEmail());
        RegisterResponse response = authService.registerCustomer(request);
        
        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Login customer", description = "Authenticate customer and return JWT tokens")
    public ResponseEntity<LoginResponse> loginCustomer(@Valid @RequestBody LoginRequest request, 
                                                      HttpServletResponse response) {
        log.info("Login request received for email: {}", request.getEmail());
        LoginResponse loginResponse = authService.loginCustomer(request);
        
        if (loginResponse.getToken() != null) {
            // Set JWT token in HTTP-only cookie
            Cookie tokenCookie = new Cookie("jwt_token", loginResponse.getToken());
            tokenCookie.setHttpOnly(true);
            tokenCookie.setSecure(false); // Set to true in production with HTTPS
            tokenCookie.setPath("/");
            tokenCookie.setMaxAge(24 * 60 * 60); // 24 hours
            response.addCookie(tokenCookie);
            
            // Set refresh token in HTTP-only cookie
            Cookie refreshCookie = new Cookie("refresh_token", loginResponse.getRefreshToken());
            refreshCookie.setHttpOnly(true);
            refreshCookie.setSecure(false); // Set to true in production with HTTPS
            refreshCookie.setPath("/");
            refreshCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
            response.addCookie(refreshCookie);
            
            return ResponseEntity.ok(loginResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginResponse);
        }
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh JWT token", description = "Refresh JWT token using refresh token")
    public ResponseEntity<LoginResponse> refreshToken(@CookieValue(name = "refresh_token", required = false) String refreshToken,
                                                     HttpServletResponse response) {
        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new LoginResponse(null, null, null, null, null, null, null, "Refresh token not found"));
        }
        
        LoginResponse loginResponse = authService.refreshToken(refreshToken);
        
        if (loginResponse.getToken() != null) {
            // Set new JWT token in HTTP-only cookie
            Cookie tokenCookie = new Cookie("jwt_token", loginResponse.getToken());
            tokenCookie.setHttpOnly(true);
            tokenCookie.setSecure(false); // Set to true in production with HTTPS
            tokenCookie.setPath("/");
            tokenCookie.setMaxAge(24 * 60 * 60); // 24 hours
            response.addCookie(tokenCookie);
            
            // Set new refresh token in HTTP-only cookie
            Cookie refreshCookie = new Cookie("refresh_token", loginResponse.getRefreshToken());
            refreshCookie.setHttpOnly(true);
            refreshCookie.setSecure(false); // Set to true in production with HTTPS
            refreshCookie.setPath("/");
            refreshCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
            response.addCookie(refreshCookie);
            
            return ResponseEntity.ok(loginResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginResponse);
        }
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout customer", description = "Logout customer and invalidate tokens")
    public ResponseEntity<String> logout(@CookieValue(name = "jwt_token", required = false) String token,
                                        HttpServletRequest request,
                                        HttpServletResponse response) {
        if (token != null) {
            authService.logout(token);
        }
        
        // Clear cookies
        Cookie tokenCookie = new Cookie("jwt_token", null);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setSecure(false);
        tokenCookie.setPath("/");
        tokenCookie.setMaxAge(0);
        response.addCookie(tokenCookie);
        
        Cookie refreshCookie = new Cookie("refresh_token", null);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(false);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(0);
        response.addCookie(refreshCookie);
        
        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Check if the authentication service is running")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Auth Service is running");
    }
}