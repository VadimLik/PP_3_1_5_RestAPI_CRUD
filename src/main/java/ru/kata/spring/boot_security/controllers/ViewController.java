package ru.kata.spring.boot_security.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class ViewController {

    @GetMapping("/admin")
    public String showAdminPage() {
        return "adminPage";
    }

    @GetMapping("/user")
    public String showUserPage(){
        return "userPage";
    }

//    @GetMapping("/login")
//    public String loginUserPage(){
//        return "login";
//    }
}
