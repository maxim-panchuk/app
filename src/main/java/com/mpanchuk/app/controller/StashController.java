package com.mpanchuk.app.controller;

import com.mpanchuk.app.domain.response.StashResponse;
import com.mpanchuk.app.model.Item;
import com.mpanchuk.app.domain.StashPair;
import com.mpanchuk.app.service.StashService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stash")
public class StashController implements SecuredRestController{
    private final StashService stashService ;

    @Autowired
    public StashController(StashService stashService) {
        this.stashService = stashService ;
    }

    @GetMapping("/")
    public ResponseEntity<List<StashResponse>> getStash(@NonNull HttpServletRequest request) {
        var arr = stashService.getStash(getJwtString(request));
        if (arr == null) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(arr, HttpStatus.OK) ;
    }

    @PutMapping("/add")
    public ResponseEntity<List<StashResponse>> addItem(@NonNull HttpServletRequest request, @RequestParam int itemId, @RequestParam(defaultValue = "1") int amount) {
        var arr = stashService.addItem(getJwtString(request), (long) itemId, amount);
        return new ResponseEntity<>(arr, HttpStatus.CREATED) ;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<List<StashPair<Item, Integer>>> deleteItem(@NonNull HttpServletRequest request, @RequestParam int itemId, @RequestParam(defaultValue = "1") int amount) {
        List<StashPair<Item, Integer>> arr = stashService.deleteItem(getJwtString(request), (long) itemId, amount);
        if (arr == null) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(arr, HttpStatus.OK) ;
    }

    private String getJwtString(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
         return authHeader.substring(7);
    }
}
