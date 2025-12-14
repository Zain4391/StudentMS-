package com.StudentMS.StudentMS.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.StudentMS.StudentMS.DTOs.Request.StudentRequestDTO;
import com.StudentMS.StudentMS.DTOs.Response.StudentResponseDTO;

public interface StudentService {
    
    StudentResponseDTO createStudent(StudentRequestDTO requestDTO);

    StudentResponseDTO getStudentById(Long id);

    Page<StudentResponseDTO> getAllStudents(Pageable pageable);

    StudentResponseDTO updateStudent(Long id, StudentRequestDTO requestDTO);

    void deleteStudent(Long id);

    StudentResponseDTO getStudentByEmail(String email);

    Page<StudentResponseDTO> getStudentsByMajor(String major, Pageable pageable);
}
