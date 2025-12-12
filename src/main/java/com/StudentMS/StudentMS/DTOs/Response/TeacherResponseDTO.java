package com.StudentMS.StudentMS.DTOs.Response;

import com.StudentMS.StudentMS.enums.Roles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherResponseDTO {
    
    private Long id;
    private String name;
    private String email;
    private Roles role;
    private String department;
    private String specialization;
}
