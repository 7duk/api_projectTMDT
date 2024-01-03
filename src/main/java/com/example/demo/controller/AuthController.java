package com.example.demo.controller;


import com.example.demo.dto.login.LoginDto;
import com.example.demo.dto.user.UserTokenDto;
import com.example.demo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("")
    public ResponseEntity<?> login(@RequestBody @Validated LoginDto request) {
        System.out.println(request.toString());
        return authService.attemptLogin(request.getEmail(), request.getPassword());
    }

    @PostMapping("/google")
    public ResponseEntity<?> loginGoogle(@RequestBody Map<String, String> requestBody) {
        String authToken = requestBody.get("authToken");
        System.out.println(authToken);
        return authService.attemptLoginGoogle(authToken);
    }
}
