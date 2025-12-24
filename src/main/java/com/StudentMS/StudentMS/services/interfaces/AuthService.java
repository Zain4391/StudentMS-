package com.StudentMS.StudentMS.services.interfaces;

import com.StudentMS.StudentMS.DTOs.Auth.AuthResponseDTO;
import com.StudentMS.StudentMS.DTOs.Auth.LoginRequestDTO;

public interface AuthService {
    
    AuthResponseDTO loginStudent(LoginRequestDTO loginRequest);

    AuthResponseDTO loginTeacher(LoginRequestDTO loginRequest);

}
