package com.Ayush.JournalApp.controller;

import com.Ayush.JournalApp.entity.User;
import com.Ayush.JournalApp.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserServices userServices;
    @GetMapping("/health-check")
    public String healthCheck() {
        return "OK";
    }

    @PostMapping("/create-user")
    public void CreateUser(@RequestBody User user){
        userServices.saveNewUser(user);
    }
}
