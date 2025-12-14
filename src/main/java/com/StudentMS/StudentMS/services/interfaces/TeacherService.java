package com.StudentMS.StudentMS.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.StudentMS.StudentMS.DTOs.Request.TeacherRequestDTO;
import com.StudentMS.StudentMS.DTOs.Response.TeacherResponseDTO;

public interface TeacherService {
    
    TeacherResponseDTO createTeacher(TeacherRequestDTO requestDTO);
    
    TeacherResponseDTO getTeacherById(Long id);
    
    Page<TeacherResponseDTO> getAllTeachers(Pageable pageable);
    
    TeacherResponseDTO updateTeacher(Long id, TeacherRequestDTO requestDTO);
    
    void deleteTeacher(Long id);
    
    TeacherResponseDTO getTeacherByEmail(String email);
    
    Page<TeacherResponseDTO> getTeachersByDepartment(String department, Pageable pageable);
    
    Page<TeacherResponseDTO> getTeachersBySpecialization(String specialization, Pageable pageable);
}