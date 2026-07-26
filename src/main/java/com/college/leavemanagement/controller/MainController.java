package com.college.leavemanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "index";   // loads templates/index.html
    }

    @GetMapping("/login")
    public String login() {
        return "login";   // loads templates/login.html
    }
}