# University Course Management System - Scenario

## Overview
You are building a REST API for a university to manage their academic operations.

## Core Functionality

### 1. Course Management
The university offers various courses. Each course has:
- Course code
- Name
- Description
- Credit hours

Courses are taught by teachers and can have multiple students enrolled in them.

### 2. Teacher Management
Teachers work at the university and can teach multiple courses. Each teacher has:
- Name
- Email
- Department
- Specialization

A teacher should be able to:
- View all courses they're teaching
- View all students enrolled in their courses
- View grades of students in their courses

### 3. Student Management
Students enroll in the university and register for courses. Each student has:
- Name
- Email
- Enrollment date
- Major

Students can enroll in multiple courses, and the system should track their enrollments.

### 4. Enrollment Tracking
When a student enrolls in a course, the system needs to record:
- Enrollment date
- Student's grade for that course (can be updated later by the teacher)

## Business Rules
1. A course must have at least one teacher assigned
2. A student cannot enroll in the same course twice
3. A course has a maximum capacity (50 students)
4. Teachers can only update grades for courses they teach

## Entities

### Teacher
- id (Primary Key)
- name
- email
- department
- specialization

### Student
- id (Primary Key)
- name
- email
- enrollmentDate
- major

### Course
- id (Primary Key)
- courseCode
- name
- description
- creditHours
- maxCapacity
- teacherId (Foreign Key)

### Enrollment
- id (Primary Key)
- studentId (Foreign Key)
- courseId (Foreign Key)
- enrollmentDate
- grade

## Relationships

### Direct Relationships:

1. **Teacher → Course** = **One-to-Many**
   - One teacher teaches many courses
   - In Course entity: `@ManyToOne` to Teacher
   - In Teacher entity: `@OneToMany` to Course

2. **Course ↔ Student** = **Many-to-Many (via Enrollment)**
   - Many students enroll in many courses
   - Enrollment acts as the junction table
   - In Student entity: `@OneToMany` to Enrollment
   - In Course entity: `@OneToMany` to Enrollment
   - In Enrollment entity: `@ManyToOne` to Student AND `@ManyToOne` to Course

### Indirect Relationships:
- Teacher → Students (through the courses they teach)

## API Requirements

### Teacher Endpoints
- View all courses taught by a teacher
- View all students enrolled in a teacher's courses
- Update grades for students in their courses

### Student Endpoints
- View all courses a student is enrolled in
- Enroll in a course
- View grades

### Course Endpoints
- Create/Update/Delete courses
- View all students enrolled in a course
- Assign teacher to course

### Enrollment Endpoints
- Enroll student in course
- Update grade
- Remove enrollment

## Technical Requirements
- RESTful API design
- Proper HTTP methods (GET, POST, PUT, DELETE)
- DTOs for request/response
- Entity to DTO mapping
- Service layer with business logic
- Repository layer with custom queries
- Exception handling
- Input validation
- JWT-based authentication (to be implemented later)