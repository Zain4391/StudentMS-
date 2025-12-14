package com.StudentMS.StudentMS.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.StudentMS.StudentMS.DTOs.Request.CourseRequestDTO;
import com.StudentMS.StudentMS.DTOs.Response.CourseResponseDTO;

public interface CourseService {
    
    CourseResponseDTO createCourse(CourseRequestDTO requestDTO);
    
    CourseResponseDTO getCourseById(Long id);
    
    Page<CourseResponseDTO> getAllCourses(Pageable pageable);
    
    CourseResponseDTO updateCourse(Long id, CourseRequestDTO requestDTO);
    
    void deleteCourse(Long id);
    
    CourseResponseDTO getCourseByCourseCode(String courseCode);
    
    Page<CourseResponseDTO> getCoursesByTeacherId(Long teacherId, Pageable pageable);
    
    Page<CourseResponseDTO> getCoursesWithAvailableCapacity(Pageable pageable);
}