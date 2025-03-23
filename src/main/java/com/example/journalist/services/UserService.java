package com.example.journalist.services;

import com.example.journalist.entity.UserEntity;
import com.example.journalist.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepository repository;

    public void createEntity(UserEntity myEntry) {
        repository.save(myEntry);
    }

    public List<UserEntity> getAll() {
        return repository.findAll();
    }

    public Optional<UserEntity> findById(ObjectId id) {
        return repository.findById(id);
    }

    public void deleteById(ObjectId id) {
        repository.deleteById(id);
    }
    public UserEntity findByUserName(String userName) {
        return repository.findByUserName(userName);
    }

    public void deleteByUserName(String userName) {
        repository.deleteByUserName(userName);
    }
}

