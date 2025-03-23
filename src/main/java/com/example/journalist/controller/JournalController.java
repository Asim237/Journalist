package com.example.journalist.controller;

import com.example.journalist.entity.JournalEntity;
import com.example.journalist.entity.UserEntity;
import com.example.journalist.services.JournalService;
import com.example.journalist.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalController {
    @Autowired
    private JournalService journalService;
    @Autowired
    private UserService userService;
    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName) {
        UserEntity userName1 = userService.findByUserName(userName);
        List<JournalEntity> all = userName1.getJournalEntities();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("{userName}")
    public ResponseEntity<JournalEntity> createEntity(@RequestBody JournalEntity myEntry, @PathVariable String userName) {
        try {
            journalService.createEntity(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntity> findElementById(@PathVariable ObjectId myId) {
        JournalEntity journal = journalService.findById(myId).orElse(null);
        if (journal == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(journal, HttpStatus.OK);
    }

    @DeleteMapping("/id/{userName}/{myId}")
    public ResponseEntity<Void> deleteById(@PathVariable ObjectId myId,@PathVariable String userName) {
        try {
            journalService.deleteById(myId,userName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("id/{userName}/{myId}")
    public ResponseEntity<?> updateJournalById(@PathVariable String userName, @PathVariable ObjectId myId, @RequestBody JournalEntity newEntry) {
        try {
            JournalEntity old = journalService.findById(myId).orElse(null);
            if (old != null) {
                old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : old.getContent());
                journalService.createEntity(old, userName);
            }
            return new ResponseEntity<>(old, HttpStatus.OK);
        } catch (Exception ignored) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
