package com.college.leavemanagement.controller;

import com.college.leavemanagement.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("hods", adminService.getAllHods());
        return "admin/dashboard";
    }

    @PostMapping("/create-hod")
    public String createHod(@RequestParam String name,
                            @RequestParam String department,
                            @RequestParam String username,
                            @RequestParam String password) {
        adminService.createHod(name, department, username, password);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/delete-hod/{id}")
    public String deleteHod(@PathVariable Long id) {
        adminService.deleteHod(id);
        return "redirect:/admin/dashboard";
    }
}