package com.college.leavemanagement.service;

import com.college.leavemanagement.entity.*;
import com.college.leavemanagement.enums.LeaveStatus;
import com.college.leavemanagement.enums.Role;
import com.college.leavemanagement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TutorService {

    @Autowired private TutorRepository tutorRepository;
    @Autowired private StudentRepository studentRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private LeaveRequestRepository leaveRequestRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    public Tutor getTutorByUsername(String username) {
        return tutorRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Tutor not found"));
    }

    public List<Student> getStudentsByTutor(Tutor tutor) {
        return studentRepository.findByTutor(tutor);
    }

    public void addStudent(String name, String regNo, String section,
                           String parentName, String parentMobile,
                           String studentMobile, String username,
                           String password, Tutor tutor) {
        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(Role.STUDENT)
                .build();
        userRepository.save(user);

        Student student = Student.builder()
                .name(name).regNo(regNo).section(section)
                .parentName(parentName).parentMobile(parentMobile)
                .studentMobile(studentMobile)
                .tutor(tutor).user(user)
                .build();
        studentRepository.save(student);
    }

    public List<LeaveRequest> getPendingLeaves(Tutor tutor) {
        return leaveRequestRepository
                .findByStudent_Tutor_IdAndTutorStatus(tutor.getId(), LeaveStatus.PENDING);
    }

    public List<LeaveRequest> getAllLeaves(Tutor tutor) {
        return leaveRequestRepository.findByStudent_Tutor_Id(tutor.getId());
    }

    public void approveLeave(Long leaveId) {
        LeaveRequest leave = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave not found"));
        leave.setTutorStatus(LeaveStatus.APPROVED);
        leaveRequestRepository.save(leave);
    }

    public void rejectLeave(Long leaveId, String remarks) {
        LeaveRequest leave = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave not found"));
        leave.setTutorStatus(LeaveStatus.REJECTED);
        leave.setTutorRemarks(remarks);
        leaveRequestRepository.save(leave);
    }
}