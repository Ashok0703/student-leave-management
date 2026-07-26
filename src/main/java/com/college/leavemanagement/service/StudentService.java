package com.college.leavemanagement.service;

import com.college.leavemanagement.entity.*;
import com.college.leavemanagement.enums.LeaveStatus;
import com.college.leavemanagement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class StudentService {

    @Autowired private StudentRepository studentRepository;
    @Autowired private LeaveRequestRepository leaveRequestRepository;

    public Student getStudentByUsername(String username) {
        return studentRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public List<LeaveRequest> getLeaveHistory(Student student) {
        return leaveRequestRepository.findByStudent(student);
    }

    public void applyLeave(Student student, String reason, String leaveType,
                           LocalDate fromDate, LocalDate toDate, String address) {
        int totalDays = (int) ChronoUnit.DAYS.between(fromDate, toDate) + 1;

        LeaveRequest leave = LeaveRequest.builder()
                .student(student)
                .reason(reason)
                .leaveType(leaveType)
                .fromDate(fromDate)
                .toDate(toDate)
                .totalDays(totalDays)
                .address(address)
                .tutorStatus(LeaveStatus.PENDING)
                .appliedDate(LocalDate.now())
                .build();

        leaveRequestRepository.save(leave);
    }
}