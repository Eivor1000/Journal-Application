package com.Ayush.JournalApp.Repository;

import com.Ayush.JournalApp.entity.JournalEntry;
import com.Ayush.JournalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUsername(String username);

    void deleteByUserName(String name);
}
