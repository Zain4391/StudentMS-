package com.StudentMS.StudentMS.controllers;

import com.StudentMS.StudentMS.DTOs.Request.TeacherRequestDTO;
import com.StudentMS.StudentMS.DTOs.Response.TeacherResponseDTO;
import com.StudentMS.StudentMS.services.interfaces.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping
    public ResponseEntity<TeacherResponseDTO> createTeacher(@Valid @RequestBody TeacherRequestDTO teacherRequestDTO) {
        return new ResponseEntity<>(teacherService.createTeacher(teacherRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponseDTO> getTeacherById(@PathVariable Long id) {
        return ResponseEntity.ok(teacherService.getTeacherById(id));
    }

    @GetMapping
    public ResponseEntity<Page<TeacherResponseDTO>> getAllTeachers(Pageable pageable) {
        return ResponseEntity.ok(teacherService.getAllTeachers(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherResponseDTO> updateTeacher(@PathVariable Long id, @Valid @RequestBody TeacherRequestDTO teacherRequestDTO) {
        return ResponseEntity.ok(teacherService.updateTeacher(id, teacherRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<TeacherResponseDTO> getTeacherByEmail(@PathVariable String email) {
        return ResponseEntity.ok(teacherService.getTeacherByEmail(email));
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<Page<TeacherResponseDTO>> getTeachersByDepartment(@PathVariable String department, Pageable pageable) {
        return ResponseEntity.ok(teacherService.getTeachersByDepartment(department, pageable));
    }

    @GetMapping("/specialization/{specialization}")
    public ResponseEntity<Page<TeacherResponseDTO>> getTeachersBySpecialization(@PathVariable String specialization, Pageable pageable) {
        return ResponseEntity.ok(teacherService.getTeachersBySpecialization(specialization, pageable));
    }
}
