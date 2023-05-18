package com.mpanchuk.app.controller;

import com.mpanchuk.app.domain.ItemResponse;
import com.mpanchuk.app.model.Item;
import com.mpanchuk.app.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public Page<ItemResponse> getAllItems(@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize) {
        return itemService.getAllItems(pageNo, pageSize);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> getItemById(@PathVariable Long id) {
        Item item = itemService.getItemById(id);
        if (item == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ItemResponse itemResponse = new ItemResponse(item.getId(), item.getName(), item.getPrice()) ;

        return new ResponseEntity<>(itemResponse, HttpStatus.OK);
    }

    @GetMapping("/find")
    public Page<ItemResponse> getItemByName(@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize, @RequestParam String regexp) {
        logger.info("Getting item with regexp: " + regexp);
        return itemService.getItemByRegexp(regexp, pageNo, pageSize);
    }
}
