package com.Ayush.JournalApp.controller;

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
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryServices journalEntryServices;

    @Autowired
    private UserServices userServices;

    @GetMapping("/{username}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String username){
        User user = userServices.findByUsername(username);
        List<JournalEntry> all = user.getJournalEntries();
        if(all!= null && !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{username}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry,@PathVariable String username){
       try{
           myEntry.setDate(LocalDateTime.now());
           journalEntryServices.saveJournalEntry(myEntry,username);
           return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
       }catch(Exception e){
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
    }

    @GetMapping("id/{myid}")
    public ResponseEntity<JournalEntry> GetJournalById (@PathVariable ObjectId myid){
        Optional <JournalEntry> journalEntry = journalEntryServices.findById(myid);
        return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("id/{username}/{myid}")
    public ResponseEntity<?> Deleteid(@PathVariable ObjectId myid,@PathVariable String username){
      journalEntryServices.deleteById(myid,username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("id/{username}/{id}")
    public ResponseEntity<?> updateJournalbyId(
            @PathVariable ObjectId id,
            @RequestBody JournalEntry newEntry,
            @PathVariable String username) {
    User user = userServices.findByUsername(username);
    if(user == null){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    JournalEntry old = journalEntryServices.findById(id).orElse(null);
    if(old != null){
        old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
        old.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : old.getContent());
        journalEntryServices.saveJournalEntry(old);
        return new ResponseEntity<>(old, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
