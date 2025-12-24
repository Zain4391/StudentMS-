package com.StudentMS.StudentMS.controllers;

import com.StudentMS.StudentMS.DTOs.Request.CourseRequestDTO;
import com.StudentMS.StudentMS.DTOs.Response.CourseResponseDTO;
import com.StudentMS.StudentMS.services.interfaces.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<CourseResponseDTO> createCourse(@Valid @RequestBody CourseRequestDTO courseRequestDTO) {
        return new ResponseEntity<>(courseService.createCourse(courseRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @GetMapping
    public ResponseEntity<Page<CourseResponseDTO>> getAllCourses(Pageable pageable) {
        return ResponseEntity.ok(courseService.getAllCourses(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseRequestDTO courseRequestDTO) {
        return ResponseEntity.ok(courseService.updateCourse(id, courseRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/code/{courseCode}")
    public ResponseEntity<CourseResponseDTO> getCourseByCourseCode(@PathVariable String courseCode) {
        return ResponseEntity.ok(courseService.getCourseByCourseCode(courseCode));
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<Page<CourseResponseDTO>> getCoursesByTeacherId(@PathVariable Long teacherId, Pageable pageable) {
        return ResponseEntity.ok(courseService.getCoursesByTeacherId(teacherId, pageable));
    }

    @GetMapping("/available")
    public ResponseEntity<Page<CourseResponseDTO>> getCoursesWithAvailableCapacity(Pageable pageable) {
        return ResponseEntity.ok(courseService.getCoursesWithAvailableCapacity(pageable));
    }
}
