package com.StudentMS.StudentMS.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.StudentMS.StudentMS.DTOs.Request.EnrollmentRequestDTO;
import com.StudentMS.StudentMS.DTOs.Response.EnrollmentResponseDTO;
import com.StudentMS.StudentMS.enums.Grade;

public interface EnrollmentService {
    
    EnrollmentResponseDTO enrollStudent(EnrollmentRequestDTO requestDTO);
    
    EnrollmentResponseDTO getEnrollmentById(Long id);
    
    Page<EnrollmentResponseDTO> getAllEnrollments(Pageable pageable);
    
    EnrollmentResponseDTO updateEnrollment(Long id, EnrollmentRequestDTO requestDTO);
    
    void deleteEnrollment(Long id);
    
    Page<EnrollmentResponseDTO> getEnrollmentsByStudentId(Long studentId, Pageable pageable);
    
    Page<EnrollmentResponseDTO> getEnrollmentsByCourseId(Long courseId, Pageable pageable);
    
    Page<EnrollmentResponseDTO> getEnrollmentsByTeacherId(Long teacherId, Pageable pageable);
    
    EnrollmentResponseDTO updateGrade(Long enrollmentId, Grade grade, Long teacherId);
}