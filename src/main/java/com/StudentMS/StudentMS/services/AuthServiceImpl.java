package com.StudentMS.StudentMS.services;

import java.util.Collections;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.StudentMS.StudentMS.DTOs.Auth.AuthResponseDTO;
import com.StudentMS.StudentMS.DTOs.Auth.LoginRequestDTO;
import com.StudentMS.StudentMS.exceptions.UnauthorizedException;
import com.StudentMS.StudentMS.models.Student;
import com.StudentMS.StudentMS.models.Teacher;
import com.StudentMS.StudentMS.repository.StudentRepository;
import com.StudentMS.StudentMS.repository.TeacherRepository;
import com.StudentMS.StudentMS.security.JwtUtil;
import com.StudentMS.StudentMS.services.interfaces.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponseDTO loginStudent(LoginRequestDTO loginRequest) {
        Student student = studentRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), student.getPassword())) {
            throw new UnauthorizedException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(student.getEmail(), student.getRole().name());

        return new AuthResponseDTO(
                token,
                student.getId(),
                student.getEmail(),
                Collections.singletonList("ROLE_" + student.getRole().name())
        );
    }

    @Override
    public AuthResponseDTO loginTeacher(LoginRequestDTO loginRequest) {
        Teacher teacher = teacherRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), teacher.getPassword())) {
            throw new UnauthorizedException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(teacher.getEmail(), teacher.getRole().name());

        return new AuthResponseDTO(
                token,
                teacher.getId(),
                teacher.getEmail(),
                Collections.singletonList("ROLE_" + teacher.getRole().name())
        );
    }
}
