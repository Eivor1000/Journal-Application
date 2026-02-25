package com.Ayush.JournalApp.controller;

import com.Ayush.JournalApp.Repository.JournalEntryRepository;
import com.Ayush.JournalApp.entity.JournalEntry;
import com.Ayush.JournalApp.entity.User;
import com.Ayush.JournalApp.services.JournalEntryServices;
import com.Ayush.JournalApp.services.UserServices;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserEntryControllerV2 {

    @Autowired
    private UserServices userServices;

    @GetMapping
    public List<User> getAllUsers(){
        return userServices.getAll();
    }

    @PostMapping
    public void createUser(@RequestBody User user){
        userServices.saveUser(user);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        User userinDB = userServices.findByUsername(user.getUsername());
        if(userinDB!=null){
            userinDB.setUsername(user.getUsername());
            userinDB.setPassword(user.getPassword());
            userServices.saveUser(userinDB);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
