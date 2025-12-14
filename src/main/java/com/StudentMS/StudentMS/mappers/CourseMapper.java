package com.StudentMS.StudentMS.mappers;

import org.springframework.stereotype.Component;

import com.StudentMS.StudentMS.DTOs.Request.CourseRequestDTO;
import com.StudentMS.StudentMS.DTOs.Response.CourseResponseDTO;
import com.StudentMS.StudentMS.DTOs.Summary.CourseSummaryDTO;
import com.StudentMS.StudentMS.models.Course;
import com.StudentMS.StudentMS.models.Teacher;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CourseMapper {
    
    // Injected on runtime = Required Args Constructor
    private final TeacherMapper teacherMapper;

    public Course toEntity(CourseRequestDTO dto, Teacher teacher) {

        if(dto == null) {
            return null;
        }

        Course course = new Course();
        course.setCourseCode(dto.getCourseCode());
        course.setName(dto.getName());
        course.setCreditHours(dto.getCreditHours());
        course.setDescription(dto.getDescription());
        course.setMaxCapacity(dto.getMaxCapacity());
        course.setTeacher(teacher);

        return course;
    }

    public CourseResponseDTO toResponse(Course course) {

        if(course == null) {
            return null;
        }

        CourseResponseDTO dto = new CourseResponseDTO();
        dto.setId(course.getId());
        dto.setCourseCode(course.getCourseCode());
        dto.setName(course.getName());
        dto.setDescription(course.getDescription());
        dto.setCreditHours(course.getCreditHours());
        dto.setMaxCapacity(course.getMaxCapacity());

        // Map the teacher to course
        if(course.getTeacher() != null) {
            dto.setTeacher(teacherMapper.toResponse(course.getTeacher()));
        }

        return dto;
    }

    public CourseSummaryDTO toSummaryDTO(Course course) {
        if(course == null) {
            return null;
        }

        CourseSummaryDTO dto = new CourseSummaryDTO();
        dto.setId(course.getId());
        dto.setCourseCode(course.getCourseCode());
        dto.setName(course.getName());
        dto.setCreditHours(course.getCreditHours());
        
        return dto;
    }

    public void updateEntityFromRequest(Course course, CourseRequestDTO dto, Teacher teacher) {
        if(course == null || dto == null) {
            return;
        }

        if(dto.getCourseCode() != null) {
            course.setCourseCode(dto.getCourseCode());
        }
        
        if(dto.getName() != null) {
            course.setName(dto.getName());
        }
        
        if(dto.getDescription() != null) {
            course.setDescription(dto.getDescription());
        }
        
        if(dto.getCreditHours() != null) {
            course.setCreditHours(dto.getCreditHours());
        }
        
        if(dto.getMaxCapacity() != null) {
            course.setMaxCapacity(dto.getMaxCapacity());
        }
        
        if(teacher != null) {
            course.setTeacher(teacher);
        }
    }

}
