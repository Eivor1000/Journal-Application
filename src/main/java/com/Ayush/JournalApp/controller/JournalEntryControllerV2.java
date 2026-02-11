package com.Ayush.JournalApp.controller;

import com.Ayush.JournalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @GetMapping
    public List<JournalEntry> getAll(){
        return null;
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry myEntry){

        return true;
    }

    @GetMapping("id/{myid}")
    public JournalEntry Getid(@PathVariable long myid){
        return null;
    }

    @DeleteMapping("id/{myid}")
    public JournalEntry Delereid(@PathVariable long myid){
        return null;
    }


    @PutMapping("id/{id}")
    public JournalEntry updateJournalbyId(@PathVariable Long id, @RequestBody JournalEntry myentry) {
        return null;
    }
}
