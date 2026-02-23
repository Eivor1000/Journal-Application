package com.Ayush.JournalApp.controller;

import com.Ayush.JournalApp.Repository.JournalEntryRepository;
import com.Ayush.JournalApp.entity.JournalEntry;
import com.Ayush.JournalApp.services.JournalEntryServices;
import org.apache.coyote.Response;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    public ResponseEntity<?> getAll(){
        List<JournalEntry> all = journalEntryServices.getAll();
        if(all!= null && !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
       try{
           myEntry.setDate(LocalDateTime.now());
           journalEntryServices.saveJournalEntry(myEntry);
           return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
       }catch(Exception e){
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
    }

    @GetMapping("id/{myid}")
    public ResponseEntity<JournalEntry> Getid(@PathVariable ObjectId myid){
        Optional <JournalEntry> journalEntry = journalEntryServices.findById(myid);
        return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("id/{myid}")
    public ResponseEntity<JournalEntry> Deleteid(@PathVariable ObjectId myid){
      journalEntryRepository.deleteById(myid);
        return new ResponseEntity<JournalEntry>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("id/{id}")
    public ResponseEntity<?> updateJournalbyId(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
    JournalEntry old = journalEntryServices.findById(id).orElse(null);
    if(old != null){
        old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
        old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
        journalEntryServices.saveJournalEntry(old);
        return new ResponseEntity<>(old, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
