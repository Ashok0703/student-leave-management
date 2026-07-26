package com.college.leavemanagement.service;

import com.college.leavemanagement.entity.*;
import com.college.leavemanagement.enums.Role;
import com.college.leavemanagement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HodRepository hodRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Hod> getAllHods() {
        return hodRepository.findAll();
    }

    public void createHod(String name, String department,
                          String username, String password) {
        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(Role.HOD)
                .build();
        userRepository.save(user);

        Hod hod = Hod.builder()
                .name(name)
                .department(department)
                .user(user)
                .build();
        hodRepository.save(hod);
    }

    public void deleteHod(Long hodId) {
        hodRepository.deleteById(hodId);
    }
}