package com.college.leavemanagement.controller;

import com.college.leavemanagement.entity.Tutor;
import com.college.leavemanagement.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tutor")
public class TutorController {

    @Autowired
    private TutorService tutorService;

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails,
                            Model model) {
        Tutor tutor = tutorService.getTutorByUsername(userDetails.getUsername());
        model.addAttribute("tutor", tutor);
        model.addAttribute("students", tutorService.getStudentsByTutor(tutor));
        model.addAttribute("pendingLeaves", tutorService.getPendingLeaves(tutor));
        model.addAttribute("allLeaves", tutorService.getAllLeaves(tutor));
        return "tutor/dashboard";
    }

    @PostMapping("/add-student")
    public String addStudent(@AuthenticationPrincipal UserDetails userDetails,
                             @RequestParam String name,
                             @RequestParam String regNo,
                             @RequestParam String section,
                             @RequestParam String parentName,
                             @RequestParam String parentMobile,
                             @RequestParam String studentMobile,
                             @RequestParam String username,
                             @RequestParam String password) {
        Tutor tutor = tutorService.getTutorByUsername(userDetails.getUsername());
        tutorService.addStudent(name, regNo, section, parentName, parentMobile,
                studentMobile, username, password, tutor);
        return "redirect:/tutor/dashboard";
    }

    @PostMapping("/approve-leave/{id}")
    public String approveLeave(@PathVariable Long id) {
        tutorService.approveLeave(id);
        return "redirect:/tutor/dashboard";
    }

    @PostMapping("/reject-leave/{id}")
    public String rejectLeave(@PathVariable Long id,
                              @RequestParam String remarks) {
        tutorService.rejectLeave(id, remarks);
        return "redirect:/tutor/dashboard";
    }
}