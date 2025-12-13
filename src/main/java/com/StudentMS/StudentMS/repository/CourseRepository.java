package com.StudentMS.StudentMS.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.StudentMS.StudentMS.models.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    
    Optional<Course> findByCourseCode(String courseCode);

    Page<Course> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Course> findByCreditHours(int creditHours, Pageable pageable);

    Page<Course> findByTeacherId(Long teacherId, Pageable pageable);

    boolean existsByCourseCode(String courseCode);

    @Query("SELECT c FROM Course c WHERE " +
       "(SELECT COUNT(e) FROM Enrollment e WHERE e.course = c) < c.maxCapacity")
    Page<Course> findCoursesWithAvailableCapacity(Pageable pageable);
}
