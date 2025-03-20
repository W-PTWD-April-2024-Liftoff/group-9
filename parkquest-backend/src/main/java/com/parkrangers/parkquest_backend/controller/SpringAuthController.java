package com.parkrangers.parkquest_backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpringAuthController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
