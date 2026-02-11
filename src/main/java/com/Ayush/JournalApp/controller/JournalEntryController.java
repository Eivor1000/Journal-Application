package com.Ayush.JournalApp.controller;

import com.Ayush.JournalApp.controller.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private final Map<Long, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAll(){
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry myEntry){
        journalEntries.put(myEntry.getId(), myEntry);
        return true;
    }

    @GetMapping("id/{myid}")
    public JournalEntry Getid(@PathVariable long myid){
        return journalEntries.get(myid);
    }

    @DeleteMapping("id/{myid}")
    public JournalEntry Delereid(@PathVariable long myid){
        return journalEntries.remove(myid);
    }


    @PutMapping("id/{id}")
    public JournalEntry updateJournalbyId(@PathVariable Long id, @RequestBody JournalEntry myentry) {
        return journalEntries.put(id, myentry);
    }
}
