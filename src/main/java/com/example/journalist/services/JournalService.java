package com.example.journalist.services;

import com.example.journalist.entity.JournalEntity;
import com.example.journalist.repository.JournalRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalService {
    @Autowired
    private JournalRepository repository;

    public void createEntity(JournalEntity myEntry) {
        repository.save(myEntry);
    }

    public List<JournalEntity> getAll() {
        return repository.findAll();
    }

    public Optional<JournalEntity> findById(ObjectId id) {
        return repository.findById(id);
    }

    public void deleteById(ObjectId id) {
        repository.deleteById(id);
    }

}

