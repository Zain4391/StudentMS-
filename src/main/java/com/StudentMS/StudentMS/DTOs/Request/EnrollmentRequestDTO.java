package com.StudentMS.StudentMS.DTOs.Request;

import java.time.LocalDate;

import com.StudentMS.StudentMS.enums.Grade;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentRequestDTO {
    @NotNull(message = "Student ID is required")
    private Long studentId;
    
    @NotNull(message = "Course ID is required")
    private Long courseId;
    
    @NotNull(message = "Enrollment date is required")
    private LocalDate enrollmentDate;
    
    // grade is optional (null initially, updated later by teacher)
    private Grade grade;
}
