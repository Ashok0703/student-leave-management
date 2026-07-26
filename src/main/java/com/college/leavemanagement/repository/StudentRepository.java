package com.college.leavemanagement.repository;

import com.college.leavemanagement.entity.Student;
import com.college.leavemanagement.entity.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByTutor(Tutor tutor);
    Optional<Student> findByUserUsername(String username);
}