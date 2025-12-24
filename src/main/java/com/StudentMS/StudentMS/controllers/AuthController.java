package com.StudentMS.StudentMS.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.StudentMS.StudentMS.DTOs.Auth.AuthResponseDTO;
import com.StudentMS.StudentMS.DTOs.Auth.LoginRequestDTO;
import com.StudentMS.StudentMS.services.AuthServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/student/login")
    public ResponseEntity<AuthResponseDTO> loginStudent(@Valid @RequestBody LoginRequestDTO loginRequest) {
        return ResponseEntity.ok(authService.loginStudent(loginRequest));
    }

    @PostMapping("/teacher/login")
    public ResponseEntity<AuthResponseDTO> loginTeacher(@Valid @RequestBody LoginRequestDTO loginRequest) {
        return ResponseEntity.ok(authService.loginTeacher(loginRequest));
    }
}
