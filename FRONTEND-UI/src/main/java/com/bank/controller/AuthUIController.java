package com.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthUIController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String AUTH_SERVICE_URL = "http://localhost:8081/auth";
    private static final String API_GATEWAY_URL = "http://localhost:8080/auth";

    @GetMapping("/")
    public String homePage() {
        return "homepage";
    }

    @GetMapping("/auth/register-page")
    public String registerPage() {
        return "register-new";
    }

    @GetMapping("/auth/login-page")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/auth/register")
    public String registerCustomer(@RequestParam String firstName,
                                   @RequestParam String lastName,
                                   @RequestParam String email,
                                   @RequestParam String phoneNumber,
                                   @RequestParam String address,
                                   @RequestParam String dateOfBirth,
                                   @RequestParam String password,
                                   @RequestParam String confirmPassword,
                                   RedirectAttributes redirectAttributes) {
        
        try {
            // Validate password confirmation
            if (!password.equals(confirmPassword)) {
                redirectAttributes.addFlashAttribute("error", "Passwords do not match");
                return "redirect:/auth/register-page";
            }

            // Create registration request
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("firstName", firstName);
            requestBody.put("lastName", lastName);
            requestBody.put("email", email);
            requestBody.put("phoneNumber", phoneNumber);
            requestBody.put("address", address);
            requestBody.put("dateOfBirth", dateOfBirth);
            requestBody.put("password", password);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            // Call auth service through API gateway
            ResponseEntity<Map> response = restTemplate.postForEntity(
                API_GATEWAY_URL + "/register", 
                request, 
                Map.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                if (Boolean.TRUE.equals(responseBody.get("success"))) {
                    redirectAttributes.addFlashAttribute("success", 
                        "Registration successful! Please complete KYC verification to activate your account.");
                    return "redirect:/auth/login-page";
                } else {
                    redirectAttributes.addFlashAttribute("error", 
                        responseBody.get("message") != null ? responseBody.get("message").toString() : "Registration failed");
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "Registration failed. Please try again.");
            }

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Registration failed: " + e.getMessage());
        }

        return "redirect:/auth/register-page";
    }

    @PostMapping("/auth/login")
    public String loginCustomer(@RequestParam String email,
                                @RequestParam String password,
                                RedirectAttributes redirectAttributes) {
        
        try {
            // Create login request
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("email", email);
            requestBody.put("password", password);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            // Call auth service through API gateway
            ResponseEntity<Map> response = restTemplate.postForEntity(
                API_GATEWAY_URL + "/login", 
                request, 
                Map.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                if (responseBody.get("token") != null) {
                    // Store customer info in session or redirect to dashboard
                    redirectAttributes.addFlashAttribute("success", "Login successful!");
                    return "redirect:/customer/dashboard?customerId=" + responseBody.get("customerId");
                } else {
                    redirectAttributes.addFlashAttribute("error", 
                        responseBody.get("message") != null ? responseBody.get("message").toString() : "Login failed");
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "Login failed. Please check your credentials.");
            }

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Login failed: " + e.getMessage());
        }

        return "redirect:/auth/login-page";
    }

    @PostMapping("/auth/logout")
    public String logout(RedirectAttributes redirectAttributes) {
        try {
            // Call logout endpoint
            restTemplate.postForEntity(API_GATEWAY_URL + "/logout", null, String.class);
            redirectAttributes.addFlashAttribute("success", "Logged out successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Logout failed: " + e.getMessage());
        }
        
        return "redirect:/";
    }
}