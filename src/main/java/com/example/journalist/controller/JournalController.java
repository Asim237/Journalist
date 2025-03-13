package com.example.journalist.controller;

import com.example.journalist.entity.JournalEntity;
import com.example.journalist.services.JournalService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalController {
    @Autowired
    private JournalService service;

    @GetMapping
    public List<JournalEntity> getAll() {
        return service.getAll();
    }

    @PostMapping
    public boolean createEntity(@RequestBody JournalEntity myEntry) {
        myEntry.setDate(LocalDateTime.now());
        service.createEntity(myEntry);
        return true;
    }

    @GetMapping("/id/{myId}")
    public JournalEntity findElementById(@PathVariable ObjectId id) {
        return service.findById(id).orElse(null);
    }

    @DeleteMapping("/id/{myId}")
    public void deleteById(@PathVariable ObjectId myId) {
        service.deleteById(myId);
    }
    @PostMapping("/id/{myId})")
    public boolean updateElementById(@PathVariable ObjectId myId,@RequestBody JournalEntity myEntry) {
        JournalEntity old = service.findById(myId).orElse(null);
        if (old != null) {
            old.setTitle(myEntry.getTitle() != null && myEntry.getTitle().isEmpty() ? myEntry.getTitle() : old.getTitle());
            old.setContent(myEntry.getContent() != null && myEntry.getTitle().isEmpty() ? myEntry.getContent() : old.getContent());
        }
        service.createEntity(myEntry);
        return true;
    }
}
