package com.mpanchuk.app.controller;

import com.mpanchuk.app.model.Item;
import com.mpanchuk.app.service.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/favourite")
public class FavouriteController {
    FavouriteService favouriteService ;

    @Autowired
    public FavouriteController(FavouriteService favouriteService) {
        this.favouriteService = favouriteService ;
    }

    @GetMapping("/")
    public ResponseEntity<List<Item>> getStash() {
        return new ResponseEntity<>(favouriteService.getAll(), HttpStatus.OK) ;
    }

    @PutMapping("/add")
    public ResponseEntity<List<Item>> addItem(@RequestParam int itemId) {
        try {
            return new ResponseEntity<>(favouriteService.addItem((long) itemId), HttpStatus.CREATED) ;
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT) ;
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<List<Item>> deleteItem(@RequestParam int itemId) {
        try {
            return new ResponseEntity<>(favouriteService.deleteItem((long) itemId), HttpStatus.CREATED) ;
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT) ;
        }
    }



}
