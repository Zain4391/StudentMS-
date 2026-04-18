package com.StudentMS.StudentMS.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.StudentMS.StudentMS.DTOs.Request.StudentRequestDTO;
import com.StudentMS.StudentMS.DTOs.Response.StudentResponseDTO;
import com.StudentMS.StudentMS.mappers.StudentMapper;
import com.StudentMS.StudentMS.models.Student;
import com.StudentMS.StudentMS.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    
    // dependencies provided to the original service class will be mocked

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentMapper studentMapper;

    // Inject the mocked dependcies into the class being tested

    @InjectMocks
    private StudentServiceImpl studentServiceImpl;

    private StudentRequestDTO requestDto;
    private Student student;

    // before each test case
    @BeforeEach
    void setUp() {
        requestDto = new StudentRequestDTO("John Doe", "johnDoe@example.com", "password123", "CS");
        student = new Student();
        student.setId(1L);
        student.setEmail("johnDoe@example.com");
    }

    // Define the test case
    @Test
    void createStudent_Success(TestReporter reporter) {

        // logical flow to test
        when(studentRepository.existsByEmail(requestDto.getEmail())).thenReturn(false);
        when(studentMapper.toEntity(any())).thenReturn(student);
        when(passwordEncoder.encode(any())).thenReturn("hashed_password");
        when(studentRepository.save(any())).thenReturn(student);
        when(studentMapper.toResponse(any())).thenReturn(new StudentResponseDTO(1L, "John Doe", "johnDoe@example.com", null, null, "CS")); 

        // Act
        StudentResponseDTO result = studentServiceImpl.createStudent(requestDto);

        //Assert
        assertNotNull(result);
        assertEquals("johnDoe@example.com", result.getEmail());
        verify(studentRepository, times(1)).save(any());

        //reporter.publishEntry("Status", "PASSED");
    }

}
