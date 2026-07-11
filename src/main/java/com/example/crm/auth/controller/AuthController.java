package com.example.crm.auth.controller;

import com.example.crm.auth.dto.LoginRequest;
import com.example.crm.auth.dto.LoginResponse;
import com.example.crm.auth.service.AuthService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request) {

        return authService.login(request);
    }
}