package com.namusd.messenger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthViewController {

    @GetMapping("/login")
    public String loginView() {
        return "login";
    }

    @GetMapping("/join")
    public String joinView() {
        return "join";
    }

}
