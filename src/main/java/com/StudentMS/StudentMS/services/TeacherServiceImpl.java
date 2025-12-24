package com.StudentMS.StudentMS.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.StudentMS.StudentMS.DTOs.Request.TeacherRequestDTO;
import com.StudentMS.StudentMS.DTOs.Response.TeacherResponseDTO;
import com.StudentMS.StudentMS.enums.Roles;
import com.StudentMS.StudentMS.exceptions.DuplicateResourceException;
import com.StudentMS.StudentMS.exceptions.ResourceNotFoundException;
import com.StudentMS.StudentMS.mappers.TeacherMapper;
import com.StudentMS.StudentMS.models.Teacher;
import com.StudentMS.StudentMS.repository.TeacherRepository;
import com.StudentMS.StudentMS.services.interfaces.TeacherService;

import lombok.RequiredArgsConstructor;


@Service
@Transactional
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService{
    
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public TeacherResponseDTO createTeacher(TeacherRequestDTO requestDTO) {

        if(teacherRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateResourceException("Teacher", "email", requestDTO.getEmail());
        }

        Teacher teacher = teacherMapper.toEntity(requestDTO);
        teacher.setRole(Roles.TEACHER);

        teacher.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        Teacher savedTeacher = teacherRepository.save(teacher);

        return teacherMapper.toResponse(savedTeacher);
    }

    @Override
    public TeacherResponseDTO getTeacherById(Long id) {

        Teacher teacher = teacherRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Teacher", "id", id)
        );

        return teacherMapper.toResponse(teacher);
    }

    @Override
    public Page<TeacherResponseDTO> getAllTeachers(Pageable pageable) {
        return teacherRepository.findAll(pageable).map(teacherMapper::toResponse);
    }

    @Override
    public TeacherResponseDTO updateTeacher(Long id, TeacherRequestDTO requestDTO) {
    
        Teacher teacher = teacherRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", id));
        
        // 2. Check if email is being changed and if new email already exists
        if (!teacher.getEmail().equals(requestDTO.getEmail()) && 
            teacherRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateResourceException("Teacher", "email", requestDTO.getEmail());
        }
        
        // 3. Update entity from request DTO
        teacherMapper.updateEntityFromRequest(teacher, requestDTO);
        
        // 4. Save and return
        Teacher updatedTeacher = teacherRepository.save(teacher);
        return teacherMapper.toResponse(updatedTeacher);
    }

    @Override
    public void deleteTeacher(Long id) {
        // 1. Check if teacher exists
        Teacher teacher = teacherRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", id));
        
        // 2. Delete
        teacherRepository.delete(teacher);
    }

    @Override
    public TeacherResponseDTO getTeacherByEmail(String email) {
        Teacher teacher = teacherRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Teacher", "email", email));
        
        return teacherMapper.toResponse(teacher);
    }

    @Override
    public Page<TeacherResponseDTO> getTeachersByDepartment(String department, Pageable pageable) {
        return teacherRepository.findByDepartment(department, pageable).map(teacherMapper::toResponse);
    }

    @Override
    public Page<TeacherResponseDTO> getTeachersBySpecialization(String specialization, Pageable pageable) {
        return teacherRepository.findBySpecialization(specialization, pageable).map(teacherMapper::toResponse);
    }
}
