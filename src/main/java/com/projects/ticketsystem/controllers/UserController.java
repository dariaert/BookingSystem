package com.projects.ticketsystem.controllers;

import com.projects.ticketsystem.models.User;
import com.projects.ticketsystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/registration")
    public String registration(User user) {
        userService.save(user);
        return "redirect:/login";
    }

}
