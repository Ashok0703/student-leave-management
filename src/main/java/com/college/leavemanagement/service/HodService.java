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
public class HodService {

    @Autowired private HodRepository hodRepository;
    @Autowired private TutorRepository tutorRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private LeaveRequestRepository leaveRequestRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    public Hod getHodByUsername(String username) {
        return hodRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("HOD not found"));
    }

    public List<Tutor> getTutorsByHod(Hod hod) {
        return tutorRepository.findByHod(hod);
    }

    public void createTutor(String name, String username,
                            String password, Hod hod) {
        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(Role.TUTOR)
                .build();
        userRepository.save(user);

        Tutor tutor = Tutor.builder()
                .name(name)
                .hod(hod)
                .user(user)
                .build();
        tutorRepository.save(tutor);
    }

    // Only tutor-approved leaves that HOD hasn't acted on yet
    public List<LeaveRequest> getPendingLeaveRequests(Hod hod) {
        return leaveRequestRepository
                .findByStudent_Tutor_Hod_IdAndTutorStatusAndHodStatusIsNull(
                        hod.getId(), LeaveStatus.APPROVED);
    }

    public List<LeaveRequest> getAllLeaveRequests(Hod hod) {
        return leaveRequestRepository.findByStudent_Tutor_Hod_Id(hod.getId());
    }

    public void approveLeave(Long leaveId) {
        LeaveRequest leave = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave not found"));
        leave.setHodStatus(LeaveStatus.APPROVED);
        leaveRequestRepository.save(leave);
    }

    public void rejectLeave(Long leaveId, String remarks) {
        LeaveRequest leave = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave not found"));
        leave.setHodStatus(LeaveStatus.REJECTED);
        leave.setHodRemarks(remarks);
        leaveRequestRepository.save(leave);
    }
}