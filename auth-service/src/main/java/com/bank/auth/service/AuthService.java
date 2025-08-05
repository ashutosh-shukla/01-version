package com.bank.auth.service;

import com.bank.auth.dto.LoginRequest;
import com.bank.auth.dto.LoginResponse;
import com.bank.auth.dto.RegisterRequest;
import com.bank.auth.dto.RegisterResponse;

public interface AuthService {
    RegisterResponse registerCustomer(RegisterRequest request);
    LoginResponse loginCustomer(LoginRequest request);
    LoginResponse refreshToken(String refreshToken);
    void logout(String token);
}