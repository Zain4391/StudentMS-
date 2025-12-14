package com.StudentMS.StudentMS.mappers;

import org.springframework.stereotype.Component;

import com.StudentMS.StudentMS.DTOs.Request.EnrollmentRequestDTO;
import com.StudentMS.StudentMS.DTOs.Response.EnrollmentResponseDTO;
import com.StudentMS.StudentMS.models.Course;
import com.StudentMS.StudentMS.models.Enrollment;
import com.StudentMS.StudentMS.models.Student;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EnrollmentMapper {
    
    private final StudentMapper studentMapper;
    private final CourseMapper courseMapper;

    public Enrollment toEntity(EnrollmentRequestDTO dto, Student student, Course course) {

        if(dto == null || student == null || course == null) {
            return null;
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentDate(dto.getEnrollmentDate());
        enrollment.setGrade(dto.getGrade());
        enrollment.setStudent(student);
        enrollment.setCourse(course);

        return enrollment;
    }

    public EnrollmentResponseDTO toResponse(Enrollment enrollment) {

        if(enrollment == null) {
            return null;
        }

        EnrollmentResponseDTO dto = new EnrollmentResponseDTO();
        dto.setId(enrollment.getId());
        dto.setGrade(enrollment.getGrade());
        dto.setEnrollmentDate(enrollment.getEnrollmentDate());

        if(enrollment.getStudent() != null) {
            dto.setStudent(studentMapper.toSummaryDTO(enrollment.getStudent()));
        }

        if(enrollment.getCourse() != null) {
            dto.setCourse(courseMapper.toSummaryDTO(enrollment.getCourse()));
        }

        return dto;
    }

    public void updateEntityFromRequest(Enrollment enrollment, EnrollmentRequestDTO dto) {

        if( enrollment == null || dto == null ) {
            return;
        }

        if(dto.getEnrollmentDate() != null) {
            enrollment.setEnrollmentDate(dto.getEnrollmentDate());
        }

        if(dto.getGrade() != null) {
            enrollment.setGrade(dto.getGrade());
        }
    }
}
