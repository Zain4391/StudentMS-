package com.StudentMS.StudentMS.mappers;

import org.springframework.stereotype.Component;

import com.StudentMS.StudentMS.DTOs.Request.TeacherRequestDTO;
import com.StudentMS.StudentMS.DTOs.Response.TeacherResponseDTO;

import com.StudentMS.StudentMS.models.Teacher;

@Component
public class TeacherMapper {
    
    public Teacher toEntity(TeacherRequestDTO dto) {

        if(dto == null) {
            return null;
        }

        Teacher teacher = new Teacher();
        teacher.setName(dto.getName());
        teacher.setEmail(dto.getEmail());
        teacher.setPassword(dto.getPassword());
        teacher.setDepartment(dto.getDepartment());
        teacher.setSpecialization(dto.getSpecialization());

        return teacher;
    }

    public TeacherResponseDTO toResponse(Teacher teacher) {

        if(teacher == null) {
            return null;
        }

        TeacherResponseDTO dto = new TeacherResponseDTO();
        dto.setId(teacher.getId());
        dto.setName(teacher.getName());
        dto.setEmail(teacher.getEmail());
        dto.setRole(teacher.getRole());
        dto.setDepartment(teacher.getDepartment());
        dto.setSpecialization(teacher.getSpecialization());

        return dto;
    }

    public void updateEntityFromRequest(Teacher teacher, TeacherRequestDTO dto) {
        
        if(teacher == null || dto == null) {
            return;
        }

        if(dto.getName() != null) {
            teacher.setName(dto.getName());
        }

        if(dto.getEmail() != null) {
            teacher.setEmail(dto.getEmail());
        }

        if(dto.getDepartment() != null) {
            teacher.setDepartment(dto.getDepartment());
        }

        if(dto.getSpecialization() != null) {
            teacher.setSpecialization(dto.getSpecialization());
        }

    }
}
