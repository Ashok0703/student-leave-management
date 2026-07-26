package com.college.leavemanagement.controller;

import com.college.leavemanagement.entity.Student;
import com.college.leavemanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails,
                            Model model) {
        Student student = studentService.getStudentByUsername(userDetails.getUsername());
        model.addAttribute("student", student);
        model.addAttribute("leaveHistory", studentService.getLeaveHistory(student));
        return "student/dashboard";
    }

    @GetMapping("/apply-leave")
    public String applyLeaveForm(@AuthenticationPrincipal UserDetails userDetails,
                                 Model model) {
        Student student = studentService.getStudentByUsername(userDetails.getUsername());
        model.addAttribute("student", student);
        return "student/apply-leave";
    }

    @PostMapping("/apply-leave")
    public String applyLeave(@AuthenticationPrincipal UserDetails userDetails,
                             @RequestParam String reason,
                             @RequestParam String leaveType,
                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
                             @RequestParam String address) {
        Student student = studentService.getStudentByUsername(userDetails.getUsername());
        studentService.applyLeave(student, reason, leaveType, fromDate, toDate, address);
        return "redirect:/student/dashboard";
    }
}