package com.Ayush.JournalApp.controller;

import com.Ayush.JournalApp.Repository.UserRepository;
import com.Ayush.JournalApp.entity.User;
import com.Ayush.JournalApp.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
            userServices.saveNewUser(userinDB);
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
