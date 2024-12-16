package com.namusd.messenger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatViewController {

    @GetMapping("/chat")
    public String home() {
        return "chat";
    }

    @GetMapping("/")
    public String index() {
        return "messenger";
    }

}
