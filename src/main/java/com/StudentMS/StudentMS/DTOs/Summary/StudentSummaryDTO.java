package com.StudentMS.StudentMS.DTOs.Summary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentSummaryDTO {
    private Long id;
    private String name;
    private String email;
    private String major;
}
