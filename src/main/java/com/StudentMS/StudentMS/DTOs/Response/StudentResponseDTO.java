package com.StudentMS.StudentMS.DTOs.Response;

import java.time.LocalDate;

import com.StudentMS.StudentMS.enums.Roles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDTO {
    
    private Long id;
    private String name;
    private String email;
    private Roles role;
    private LocalDate enrollmentDate;
    private String major;
}
