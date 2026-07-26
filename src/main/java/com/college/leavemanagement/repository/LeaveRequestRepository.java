package com.college.leavemanagement.repository;

import com.college.leavemanagement.entity.LeaveRequest;
import com.college.leavemanagement.entity.Student;
import com.college.leavemanagement.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    // Get all leaves for a student
    List<LeaveRequest> findByStudent(Student student);

    // Get pending leaves for a specific tutor
    List<LeaveRequest> findByStudent_Tutor_IdAndTutorStatus(Long tutorId, LeaveStatus tutorStatus);

    // Get all leaves for a specific tutor
    List<LeaveRequest> findByStudent_Tutor_Id(Long tutorId);

    // Get tutor-approved leaves for HOD (awaiting HOD decision)
    List<LeaveRequest> findByStudent_Tutor_Hod_IdAndTutorStatusAndHodStatusIsNull(
            Long hodId, LeaveStatus tutorStatus);

    // Get all leaves in a HOD's department (two equivalent ways)
    List<LeaveRequest> findByStudent_Tutor_Hod_Id(Long hodId);
}