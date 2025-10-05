package com.sts_LoginAuthentication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model) {
        return "login"; // This should match your login.html template name
    }

    // Optional: a success redirect after login
    @GetMapping("/loginSuccess")
    public String loginSuccess() {
        return "redirect:/home";
    }
}
