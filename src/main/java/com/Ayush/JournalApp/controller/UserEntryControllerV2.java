package com.Ayush.JournalApp.controller;

import com.Ayush.JournalApp.Repository.JournalEntryRepository;
import com.Ayush.JournalApp.Repository.UserRepository;
import com.Ayush.JournalApp.entity.JournalEntry;
import com.Ayush.JournalApp.entity.User;
import com.Ayush.JournalApp.services.JournalEntryServices;
import com.Ayush.JournalApp.services.UserServices;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserEntryControllerV2 {

    @Autowired
    private UserServices userServices;

    @Autowired
    private UserRepository userRepository;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userinDB = userServices.findByUsername(username);
        if(userinDB!=null){
            userinDB.setUsername(user.getUsername());
            userinDB.setPassword(user.getPassword());
            userServices.saveUser(userinDB);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserByID(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
