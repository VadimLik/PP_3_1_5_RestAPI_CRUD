package ru.kata.spring.boot_security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.models.User;
import ru.kata.spring.boot_security.service.UsersService;

import java.security.Principal;

@RestController
@RequestMapping("user/api")
public class UserController {

    private final UsersService usersService;

    @Autowired
    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public User infoUser(Principal principal) {

        return usersService.findByUserName(principal.getName()).orElse(null);
    }
}
