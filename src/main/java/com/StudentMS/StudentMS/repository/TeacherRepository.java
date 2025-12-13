package com.StudentMS.StudentMS.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.StudentMS.StudentMS.models.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findByEmail(String email);

    Page<Teacher> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // get all teachers by department
    Page<Teacher> findByDepartment(String department, Pageable pageable);

    // get all teachers by specialization
    Page<Teacher> findBySpecialization(String specialization, Pageable pageable);

    // Check if teacher exists
    boolean existsByEmail(String email);
}