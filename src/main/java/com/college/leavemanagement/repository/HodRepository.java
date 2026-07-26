package com.college.leavemanagement.repository;

import com.college.leavemanagement.entity.Hod;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface HodRepository extends JpaRepository<Hod, Long> {
    Optional<Hod> findByUserUsername(String username);
}