package com.StudentMS.StudentMS.DTOs.Summary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseSummaryDTO {
    private Long id;
    private String courseCode;
    private String name;
    private int creditHours;
}
