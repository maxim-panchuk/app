package com.mpanchuk.app.controller;

import com.mpanchuk.app.model.Item;
import com.mpanchuk.app.domain.StashPair;
import com.mpanchuk.app.service.StashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stash")
public class StashController {
    private final StashService stashService ;

    @Autowired
    public StashController(StashService stashService) {
        this.stashService = stashService ;
    }

    @GetMapping("/")
    public ResponseEntity<List<StashPair<Item, Integer>>> getStash() {
        return new ResponseEntity<>(stashService.getStash(), HttpStatus.OK) ;
    }

    @PutMapping("/add")
    public ResponseEntity<List<StashPair<Item, Integer>>> addItem(@RequestParam int itemId, @RequestParam(defaultValue = "1") int amount) {
        return new ResponseEntity<>(stashService.addItem((long) itemId, amount), HttpStatus.CREATED) ;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<List<StashPair<Item, Integer>>> deleteItem(@RequestParam int itemId, @RequestParam(defaultValue = "1") int amount) {
        return new ResponseEntity<>(stashService.deleteItem((long) itemId, amount), HttpStatus.OK) ;
    }
}
