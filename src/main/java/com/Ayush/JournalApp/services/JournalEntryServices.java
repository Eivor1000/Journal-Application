package com.Ayush.JournalApp.services;

import com.Ayush.JournalApp.Repository.JournalEntryRepository;
import com.Ayush.JournalApp.entity.JournalEntry;
import com.Ayush.JournalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryServices {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserServices userServices;

    public void saveJournalEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    @Transactional
    public void saveJournalEntry(JournalEntry journalEntry, String username){
        User user = userServices.findByUsername(username);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(saved);
        userServices.saveUser(user);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id,String username){
        boolean removed = false;
        try{
        User user = userServices.findByUsername(username);
         removed = user.getJournalEntries().removeIf(x-> x.getId().equals(id));

        if(removed) {
            userServices.saveUser(user);
            journalEntryRepository.deleteById(id);
        }
    }catch(Exception e){
            throw new RuntimeException("Error deleting journal entry with id: " + id, e);
        }
        return removed;
    }
}
