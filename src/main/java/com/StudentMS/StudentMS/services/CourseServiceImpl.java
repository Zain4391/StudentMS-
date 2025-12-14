package com.StudentMS.StudentMS.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.StudentMS.StudentMS.DTOs.Request.CourseRequestDTO;
import com.StudentMS.StudentMS.DTOs.Response.CourseResponseDTO;
import com.StudentMS.StudentMS.exceptions.DuplicateResourceException;
import com.StudentMS.StudentMS.exceptions.ResourceNotFoundException;
import com.StudentMS.StudentMS.mappers.CourseMapper;
import com.StudentMS.StudentMS.models.Course;
import com.StudentMS.StudentMS.models.Teacher;
import com.StudentMS.StudentMS.repository.CourseRepository;
import com.StudentMS.StudentMS.repository.TeacherRepository;
import com.StudentMS.StudentMS.services.interfaces.CourseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseServiceImpl implements CourseService {
    
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final CourseMapper courseMapper;

    @Override
    public CourseResponseDTO createCourse(CourseRequestDTO requestDTO) {
        // 1. Check if course code already exists (throw DuplicateResourceException if yes)
        if (courseRepository.existsByCourseCode(requestDTO.getCourseCode())) {
            throw new DuplicateResourceException("Course", "courseCode", requestDTO.getCourseCode());
        }

        // 2. Find the teacher (throw ResourceNotFoundException if not found)
        Teacher teacher = teacherRepository.findById(requestDTO.getTeacherId())
            .orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", requestDTO.getTeacherId()));

        // 3. Convert DTO to entity using mapper
        Course course = courseMapper.toEntity(requestDTO, teacher);

        // 4. Save to repository
        Course savedCourse = courseRepository.save(course);

        // 5. Convert saved entity to ResponseDTO and return
        return courseMapper.toResponse(savedCourse);
    }

    @Override
    public CourseResponseDTO getCourseById(Long id) {
        // 1. Find course by ID (throw ResourceNotFoundException if not found)
        Course course = courseRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Course", "id", id));
        
        // 2. Convert to ResponseDTO and return
        return courseMapper.toResponse(course);
    }

    @Override
    public Page<CourseResponseDTO> getAllCourses(Pageable pageable) {
        return courseRepository.findAll(pageable).map(courseMapper::toResponse);
    }

    @Override
    public CourseResponseDTO updateCourse(Long id, CourseRequestDTO requestDTO) {
        // 1. Find existing course
        Course course = courseRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Course", "id", id));
        
        // 2. Check if course code is being changed and if new course code already exists
        if (!course.getCourseCode().equals(requestDTO.getCourseCode()) && 
            courseRepository.existsByCourseCode(requestDTO.getCourseCode())) {
            throw new DuplicateResourceException("Course", "courseCode", requestDTO.getCourseCode());
        }

        // 3. Find the teacher if provided
        Teacher teacher = null;
        if (requestDTO.getTeacherId() != null) {
            teacher = teacherRepository.findById(requestDTO.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", requestDTO.getTeacherId()));
        }
        
        // 4. Update entity from request DTO
        courseMapper.updateEntityFromRequest(course, requestDTO, teacher);
        
        // 5. Save and return
        Course updatedCourse = courseRepository.save(course);
        return courseMapper.toResponse(updatedCourse);
    }

    @Override
    public void deleteCourse(Long id) {
        // 1. Check if course exists
        Course course = courseRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Course", "id", id));
        
        // 2. Delete
        courseRepository.delete(course);
    }

    @Override
    public CourseResponseDTO getCourseByCourseCode(String courseCode) {
        Course course = courseRepository.findByCourseCode(courseCode)
            .orElseThrow(() -> new ResourceNotFoundException("Course", "courseCode", courseCode));
        
        return courseMapper.toResponse(course);
    }

    @Override
    public Page<CourseResponseDTO> getCoursesByTeacherId(Long teacherId, Pageable pageable) {
        return courseRepository.findByTeacherId(teacherId, pageable)
            .map(courseMapper::toResponse);
    }

    @Override
    public Page<CourseResponseDTO> getCoursesWithAvailableCapacity(Pageable pageable) {
        return courseRepository.findCoursesWithAvailableCapacity(pageable)
            .map(courseMapper::toResponse);
    }
}
