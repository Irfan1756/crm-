package com.example.crm.auth.service;

import com.example.crm.auth.dto.LoginRequest;
import com.example.crm.auth.dto.LoginResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public LoginResponse login(LoginRequest request) {

        return new LoginResponse("dummy-token");
    }
}