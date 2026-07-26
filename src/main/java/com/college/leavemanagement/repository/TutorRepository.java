package com.college.leavemanagement.repository;

import com.college.leavemanagement.entity.Hod;
import com.college.leavemanagement.entity.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TutorRepository extends JpaRepository<Tutor, Long> {
    List<Tutor> findByHod(Hod hod);
    Optional<Tutor> findByUserUsername(String username);
}