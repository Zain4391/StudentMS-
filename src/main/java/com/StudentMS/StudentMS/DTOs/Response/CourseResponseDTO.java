package com.StudentMS.StudentMS.DTOs.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponseDTO {
    
    private Long id;

    private String courseCode;

    private String name;

    private String description;

    private int creditHours;

    private int maxCapacity;

    private TeacherResponseDTO teacher;
}
