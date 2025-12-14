package com.StudentMS.StudentMS.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.StudentMS.StudentMS.DTOs.Request.StudentRequestDTO;
import com.StudentMS.StudentMS.DTOs.Response.StudentResponseDTO;
import com.StudentMS.StudentMS.enums.Roles;
import com.StudentMS.StudentMS.exceptions.DuplicateResourceException;
import com.StudentMS.StudentMS.exceptions.ResourceNotFoundException;
import com.StudentMS.StudentMS.mappers.StudentMapper;
import com.StudentMS.StudentMS.models.Student;
import com.StudentMS.StudentMS.repository.StudentRepository;
import com.StudentMS.StudentMS.services.interfaces.StudentService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService{
    
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public StudentResponseDTO createStudent(StudentRequestDTO requestDTO) {
        
        // 1. Check if email already exists (throw DuplicateResourceException if yes)
        if(studentRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateResourceException("Student", "email", requestDTO.getEmail());
        }

        // 2. Convert DTO to entity using mapper
        Student student = studentMapper.toEntity(requestDTO);

        // 3. Set role to STUDENT
        student.setRole(Roles.STUDENT);
        
        // 4. Hash the password (for now just set it, we'll add BCrypt later)
        // student.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        // 5. Save to repository
        Student savedStudent = studentRepository.save(student);

        // 6. Convert saved entity to ResponseDTO and return
        return studentMapper.toResponse(savedStudent);
    }

    @Override
    public StudentResponseDTO getStudentById(Long id) {
        // 1. Find student by ID (throw ResourceNotFoundException if not found)
        Student student = studentRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Student", "id", id)
        );
        
        // 2. Convert to ResponseDTO and return
        return studentMapper.toResponse(student);
    }

    @Override
    public Page<StudentResponseDTO> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable).map(studentMapper::toResponse);
    }

    @Override
    public StudentResponseDTO updateStudent(Long id, StudentRequestDTO requestDTO) {
        // 1. Find existing student
        Student student = studentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
        
        // 2. Check if email is being changed and if new email already exists
        if (!student.getEmail().equals(requestDTO.getEmail()) && 
            studentRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateResourceException("Student", "email", requestDTO.getEmail());
        }
        
        // 3. Update entity from request DTO
        studentMapper.updateEntityFromRequest(student, requestDTO);
        
        // 4. Save and return
        Student updatedStudent = studentRepository.save(student);
        return studentMapper.toResponse(updatedStudent);
    }

    @Override
    public void deleteStudent(Long id) {
        // 1. Check if student exists
        Student student = studentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
        
        // 2. Delete
        studentRepository.delete(student);
    }

    @Override
    public StudentResponseDTO getStudentByEmail(String email) {
        Student student = studentRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Student", "email", email));
        
        return studentMapper.toResponse(student);
    }

    @Override
    public Page<StudentResponseDTO> getStudentsByMajor(String major, Pageable pageable) {
        return studentRepository.findByMajor(major, pageable)
            .map(studentMapper::toResponse);
    }
}
