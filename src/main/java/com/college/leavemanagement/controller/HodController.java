package com.college.leavemanagement.controller;

import com.college.leavemanagement.entity.Hod;
import com.college.leavemanagement.service.HodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/hod")
public class HodController {

    @Autowired
    private HodService hodService;

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails,
                            Model model) {
        Hod hod = hodService.getHodByUsername(userDetails.getUsername());
        model.addAttribute("hod", hod);
        model.addAttribute("tutors", hodService.getTutorsByHod(hod));
        model.addAttribute("pendingLeaves", hodService.getPendingLeaveRequests(hod));
        model.addAttribute("allLeaves", hodService.getAllLeaveRequests(hod));
        return "hod/dashboard";
    }

    @PostMapping("/create-tutor")
    public String createTutor(@AuthenticationPrincipal UserDetails userDetails,
                              @RequestParam String name,
                              @RequestParam String username,
                              @RequestParam String password) {
        Hod hod = hodService.getHodByUsername(userDetails.getUsername());
        hodService.createTutor(name, username, password, hod);
        return "redirect:/hod/dashboard";
    }

    @PostMapping("/approve-leave/{id}")
    public String approveLeave(@PathVariable Long id) {
        hodService.approveLeave(id);
        return "redirect:/hod/dashboard";
    }

    @PostMapping("/reject-leave/{id}")
    public String rejectLeave(@PathVariable Long id,
                              @RequestParam String remarks) {
        hodService.rejectLeave(id, remarks);
        return "redirect:/hod/dashboard";
    }
}