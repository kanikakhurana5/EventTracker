package com.example.eventtracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.eventtracker.services.EmailService;

@RestController
@RequestMapping("/api")
public class EmailController {

    @Autowired
    private com.example.eventtracker.services.EmailService emailService;

    @PostMapping("/send-email")
    public String sendEmail(@RequestParam String toEmail, @RequestParam String subject, @RequestParam String body) {
        return emailService.sendSimpleEmail(toEmail, subject, body);
    }
}
