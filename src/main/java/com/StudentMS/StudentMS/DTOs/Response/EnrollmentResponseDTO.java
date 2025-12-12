package com.StudentMS.StudentMS.DTOs.Response;

import com.StudentMS.StudentMS.DTOs.Summary.CourseSummaryDTO;
import com.StudentMS.StudentMS.DTOs.Summary.StudentSummaryDTO;
import com.StudentMS.StudentMS.enums.Grade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentResponseDTO {
    
    private Long id;
    private StudentSummaryDTO student;
    private CourseSummaryDTO course;
    private Grade grade;
}
