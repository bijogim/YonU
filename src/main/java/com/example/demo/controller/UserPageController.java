package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserPageController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // templates/login.html
    }

    @GetMapping("/sign_up")
    public String registerPage() {
        return "sign_up"; // templates/register.html
    }

    @GetMapping("/reset-password")
    public String resetPasswordPage() {
        return "reset-password"; // templates/reset-password.html 로 랜더링됨
    }

    @GetMapping("/main")
    public String mainPage() {
        return "main"; // templates/main.html
    }
}