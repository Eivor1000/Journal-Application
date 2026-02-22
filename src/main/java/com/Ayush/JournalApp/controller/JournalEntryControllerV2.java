package com.Ayush.JournalApp.controller;

import com.Ayush.JournalApp.Repository.JournalEntryRepository;
import com.Ayush.JournalApp.entity.JournalEntry;
import com.Ayush.JournalApp.services.JournalEntryServices;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryServices journalEntryServices;
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @GetMapping
    public List<JournalEntry> getAll(){
        return journalEntryServices.getAll();
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry myEntry){
        journalEntryServices.saveJournalEntry(myEntry);
        return true;
    }

    @GetMapping("id/{myid}")
    public Optional<JournalEntry> Getid(@PathVariable ObjectId myid){
        return Optional.ofNullable(journalEntryServices.findById(myid).orElse(null));
    }

    @DeleteMapping("id/{myid}")
    public boolean Deleteid(@PathVariable ObjectId myid){
      journalEntryRepository.deleteById(myid);
        return true;
    }


    @PutMapping("id/{id}")
    public JournalEntry updateJournalbyId(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
    JournalEntry old = journalEntryServices.findById(id).orElse(null);
    if(old != null){
        old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
        old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
    }
    journalEntryServices.saveJournalEntry(old);
    return old;

    }
}
