package com.StudentMS.StudentMS.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.StudentMS.StudentMS.models.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    Optional<Student> findByEmail(String email);

    Page<Student> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Student> findByEnrollmentDate(LocalDate enrollmentDate, Pageable pageable);

    // get students by major
    Page<Student> findByMajor(String major, Pageable pageable);

    boolean existsByEmail(String email);
}
