package com.StudentMS.StudentMS.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.StudentMS.StudentMS.DTOs.Request.EnrollmentRequestDTO;
import com.StudentMS.StudentMS.DTOs.Response.EnrollmentResponseDTO;
import com.StudentMS.StudentMS.enums.Grade;
import com.StudentMS.StudentMS.exceptions.BusinessRuleException;
import com.StudentMS.StudentMS.exceptions.DuplicateResourceException;
import com.StudentMS.StudentMS.exceptions.ResourceNotFoundException;
import com.StudentMS.StudentMS.exceptions.UnauthorizedException;
import com.StudentMS.StudentMS.mappers.EnrollmentMapper;
import com.StudentMS.StudentMS.models.Course;
import com.StudentMS.StudentMS.models.Enrollment;
import com.StudentMS.StudentMS.models.Student;
import com.StudentMS.StudentMS.repository.CourseRepository;
import com.StudentMS.StudentMS.repository.EnrollmentRepository;
import com.StudentMS.StudentMS.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EnrollmentServiceImpl implements com.StudentMS.StudentMS.services.interfaces.EnrollmentService {
    
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentMapper enrollmentMapper;

    @Override
    public EnrollmentResponseDTO enrollStudent(EnrollmentRequestDTO requestDTO) {
        // 1. Check if student already enrolled in this course (throw DuplicateResourceException if yes)
        if (enrollmentRepository.existsByStudentIdAndCourseId(
                requestDTO.getStudentId(), requestDTO.getCourseId())) {
            throw new DuplicateResourceException("Enrollment", 
                "student-course combination", 
                requestDTO.getStudentId() + "-" + requestDTO.getCourseId());
        }

        // 2. Find the student (throw ResourceNotFoundException if not found)
        Student student = studentRepository.findById(requestDTO.getStudentId())
            .orElseThrow(() -> new ResourceNotFoundException("Student", "id", requestDTO.getStudentId()));

        // 3. Find the course (throw ResourceNotFoundException if not found)
        Course course = courseRepository.findById(requestDTO.getCourseId())
            .orElseThrow(() -> new ResourceNotFoundException("Course", "id", requestDTO.getCourseId()));

        // 4. Check if course has available capacity
        long currentEnrollments = enrollmentRepository.countByCourseId(requestDTO.getCourseId());
        if (currentEnrollments >= course.getMaxCapacity()) {
            throw new BusinessRuleException("Course has reached maximum capacity");
        }

        // 5. Convert DTO to entity using mapper
        Enrollment enrollment = enrollmentMapper.toEntity(requestDTO, student, course);

        // 6. Save to repository
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        // 7. Convert saved entity to ResponseDTO and return
        return enrollmentMapper.toResponse(savedEnrollment);
    }

    @Override
    public EnrollmentResponseDTO getEnrollmentById(Long id) {
        // 1. Find enrollment by ID (throw ResourceNotFoundException if not found)
        Enrollment enrollment = enrollmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Enrollment", "id", id));
        
        // 2. Convert to ResponseDTO and return
        return enrollmentMapper.toResponse(enrollment);
    }

    @Override
    public Page<EnrollmentResponseDTO> getAllEnrollments(Pageable pageable) {
        return enrollmentRepository.findAll(pageable).map(enrollmentMapper::toResponse);
    }

    @Override
    public EnrollmentResponseDTO updateEnrollment(Long id, EnrollmentRequestDTO requestDTO) {
        // 1. Find existing enrollment
        Enrollment enrollment = enrollmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Enrollment", "id", id));
        
        // 2. Update entity from request DTO
        enrollmentMapper.updateEntityFromRequest(enrollment, requestDTO);
        
        // 3. Save and return
        Enrollment updatedEnrollment = enrollmentRepository.save(enrollment);
        return enrollmentMapper.toResponse(updatedEnrollment);
    }

    @Override
    public void deleteEnrollment(Long id) {
        // 1. Check if enrollment exists
        Enrollment enrollment = enrollmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Enrollment", "id", id));
        
        // 2. Delete
        enrollmentRepository.delete(enrollment);
    }

    @Override
    public Page<EnrollmentResponseDTO> getEnrollmentsByStudentId(Long studentId, Pageable pageable) {
        return enrollmentRepository.findByStudentId(studentId, pageable)
            .map(enrollmentMapper::toResponse);
    }

    @Override
    public Page<EnrollmentResponseDTO> getEnrollmentsByCourseId(Long courseId, Pageable pageable) {
        return enrollmentRepository.findByCourseId(courseId, pageable)
            .map(enrollmentMapper::toResponse);
    }

    @Override
    public Page<EnrollmentResponseDTO> getEnrollmentsByTeacherId(Long teacherId, Pageable pageable) {
        return enrollmentRepository.findEnrollmentsByTeacherId(teacherId, pageable)
            .map(enrollmentMapper::toResponse);
    }

    @Override
    public EnrollmentResponseDTO updateGrade(Long enrollmentId, Grade grade, Long teacherId) {
        // 1. Find the enrollment
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
            .orElseThrow(() -> new ResourceNotFoundException("Enrollment", "id", enrollmentId));
        
        // 2. Verify that the teacher is assigned to this course
        if (!enrollment.getCourse().getTeacher().getId().equals(teacherId)) {
            throw new UnauthorizedException("Teacher is not authorized to grade this enrollment");
        }
        
        // 3. Update the grade
        enrollment.setGrade(grade);
        
        // 4. Save and return
        Enrollment updatedEnrollment = enrollmentRepository.save(enrollment);
        return enrollmentMapper.toResponse(updatedEnrollment);
    }
}
