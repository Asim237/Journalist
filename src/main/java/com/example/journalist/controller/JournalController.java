package com.example.journalist.controller;

import com.example.journalist.entity.JournalEntity;
import com.example.journalist.services.JournalService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalController {
    @Autowired
    private JournalService service;

    @GetMapping
    public ResponseEntity<List<JournalEntity>> getAll() {
        List<JournalEntity> journals = service.getAll();
        return new ResponseEntity<>(journals, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<JournalEntity> createEntity(@RequestBody JournalEntity myEntry) {
        myEntry.setDate(LocalDateTime.now());
        service.createEntity(myEntry);
        return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntity> findElementById(@PathVariable ObjectId id) {
        JournalEntity journal = service.findById(id).orElse(null);
        if (journal == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(journal, HttpStatus.OK);
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<Void> deleteById(@PathVariable ObjectId myId) {
        try {
            service.deleteById(myId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/id/{myId}") // Use PUT for updates instead of POST
    public ResponseEntity<JournalEntity> updateElementById(@PathVariable ObjectId myId, @RequestBody JournalEntity myEntry) {
        JournalEntity old = service.findById(myId).orElse(null);
        if (old == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        // Corrected logic for updating fields
        if (myEntry.getTitle() != null && !myEntry.getTitle().isEmpty()) {
            old.setTitle(myEntry.getTitle());
        }
        if (myEntry.getContent() != null && !myEntry.getContent().isEmpty()) {
            old.setContent(myEntry.getContent());
        }

        service.createEntity(old); // Update the existing entity
        return new ResponseEntity<>(old, HttpStatus.OK);
    }
}
