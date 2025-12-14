package com.StudentMS.StudentMS.mappers;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.StudentMS.StudentMS.DTOs.Request.StudentRequestDTO;
import com.StudentMS.StudentMS.DTOs.Response.StudentResponseDTO;
import com.StudentMS.StudentMS.DTOs.Summary.StudentSummaryDTO;
import com.StudentMS.StudentMS.models.Student;

@Component
public class StudentMapper {
    
    public Student toEntity(StudentRequestDTO dto) {

        if(dto == null) {
            return null;
        }

        Student student = new Student();
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setPassword(dto.getPassword()); // hashed in service layer
        student.setMajor(dto.getMajor());
        student.setEnrollmentDate(LocalDate.now());


        return student;
    }

    public StudentResponseDTO toResponse(Student student) {
        
        if(student == null) {
            return null;
        }

        StudentResponseDTO dto = new StudentResponseDTO();
        dto.setId(student.getId());
        dto.setEmail(student.getEmail());
        dto.setName(student.getName());
        dto.setRole(student.getRole());
        dto.setEnrollmentDate(student.getEnrollmentDate());
        dto.setMajor(student.getMajor());

        return dto;
    }

    public StudentSummaryDTO toSummaryDTO(Student student) {

        if (student == null) {
            return null;
        }

        StudentSummaryDTO dto = new StudentSummaryDTO();
        dto.setId(student.getId());
        dto.setEmail(student.getEmail());
        dto.setName(student.getName());
        dto.setMajor(student.getMajor());
        
        return dto;
    }

    public void updateEntityFromRequest(Student student, StudentRequestDTO dto) {
        
        if(student == null || dto == null) {
            return;
        }

        if(dto.getName() != null) {
            student.setName(dto.getName());
        }

        if(dto.getEmail() != null) {
            student.setEmail(dto.getEmail());
        }

        if(dto.getMajor() != null) {
            student.setMajor(dto.getMajor());
        }

    }
}
