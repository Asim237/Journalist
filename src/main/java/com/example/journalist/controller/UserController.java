package com.example.journalist.controller;

import com.example.journalist.entity.JournalEntity;
import com.example.journalist.entity.UserEntity;
import com.example.journalist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAll() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity userEntity) {
        userService.createEntity(userEntity);
        return new ResponseEntity<>(userEntity, HttpStatus.CREATED);
    }
    @PutMapping("/userName/{userName}")
    public ResponseEntity<UserEntity> updateUser(@RequestBody UserEntity userEntity, @PathVariable String userName) {
        UserEntity userInDB = userService.findByUserName(userName);
        if (userInDB != null) {
            userInDB.setUserName(userEntity.getUserName());
            userInDB.setUserPassword(userEntity.getUserPassword());
            userService.createEntity(userEntity);
            return new ResponseEntity<>(userEntity, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("{userName}")
    public ResponseEntity<?> deleteByUserName(@PathVariable String userName) {
        UserEntity user = userService.findByUserName(userName);
        if (user != null) {
            userService.deleteByUserName(userName);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
