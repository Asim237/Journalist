package com.example.journalist.services;

import com.example.journalist.entity.JournalEntity;
import com.example.journalist.entity.UserEntity;
import com.example.journalist.repository.JournalRepository;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalService {
    @Autowired
    private JournalRepository repository;
    @Autowired
    private UserService userService;

    @Transactional
    public void createEntity(JournalEntity myEntry, String userName) {
        UserEntity user = userService.findByUserName(userName);
        myEntry.setDate(LocalDateTime.now());
        JournalEntity saved = repository.save(myEntry);
        user.getJournalEntities().add(saved);

        userService.createEntity(user);
    }

    public List<JournalEntity> getAll() {
        return repository.findAll();
    }

    public Optional<JournalEntity> findById(ObjectId id) {
        return repository.findById(id);
    }

    public void deleteById(ObjectId id, String userName) {
        UserEntity user = userService.findByUserName(userName);
        user.getJournalEntities().removeIf(journalEntity -> journalEntity.getId().equals(id));
        userService.createEntity(user);
        repository.deleteById(id);
    }

}

