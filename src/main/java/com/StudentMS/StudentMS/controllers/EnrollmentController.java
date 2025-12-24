package com.StudentMS.StudentMS.controllers;

import com.StudentMS.StudentMS.DTOs.Request.EnrollmentRequestDTO;
import com.StudentMS.StudentMS.DTOs.Response.EnrollmentResponseDTO;
import com.StudentMS.StudentMS.enums.Grade;
import com.StudentMS.StudentMS.services.interfaces.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<EnrollmentResponseDTO> enrollStudent(@Valid @RequestBody EnrollmentRequestDTO enrollmentRequestDTO) {
        return new ResponseEntity<>(enrollmentService.enrollStudent(enrollmentRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentResponseDTO> getEnrollmentById(@PathVariable Long id) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentById(id));
    }

    @GetMapping
    public ResponseEntity<Page<EnrollmentResponseDTO>> getAllEnrollments(Pageable pageable) {
        return ResponseEntity.ok(enrollmentService.getAllEnrollments(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnrollmentResponseDTO> updateEnrollment(@PathVariable Long id, @Valid @RequestBody EnrollmentRequestDTO enrollmentRequestDTO) {
        return ResponseEntity.ok(enrollmentService.updateEnrollment(id, enrollmentRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) {
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<Page<EnrollmentResponseDTO>> getEnrollmentsByStudentId(@PathVariable Long studentId, Pageable pageable) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentsByStudentId(studentId, pageable));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<Page<EnrollmentResponseDTO>> getEnrollmentsByCourseId(@PathVariable Long courseId, Pageable pageable) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentsByCourseId(courseId, pageable));
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<Page<EnrollmentResponseDTO>> getEnrollmentsByTeacherId(@PathVariable Long teacherId, Pageable pageable) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentsByTeacherId(teacherId, pageable));
    }

    @PatchMapping("/{id}/grade")
    public ResponseEntity<EnrollmentResponseDTO> updateGrade(
            @PathVariable Long id,
            @RequestParam Grade grade,
            @RequestParam Long teacherId) {
        return ResponseEntity.ok(enrollmentService.updateGrade(id, grade, teacherId));
    }
}
