package com.mpanchuk.app.service;

import com.mpanchuk.app.model.Item;
import com.mpanchuk.app.domain.StashPair;
import com.mpanchuk.app.repository.ItemRepository;
import com.mpanchuk.app.repository.StashRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class StashService {
    ItemRepository itemRepository ;
    StashRepository stashRepository ;

    @Autowired
    public StashService(ItemRepository itemRepository, StashRepository stashRepository) {
        this.itemRepository = itemRepository ;
        this.stashRepository = stashRepository ;
    }

    public List<StashPair<Item, Integer>> addItem(Long itemId, int amount) throws NoSuchElementException {
        Item item = itemRepository.findById(itemId).orElseThrow();
        return stashRepository.addItem(item, amount) ;
    }

    public List<StashPair<Item, Integer>> deleteItem(Long itemId, int amount) throws NoSuchElementException {
        Item item = itemRepository.findById(itemId).orElseThrow();
        return stashRepository.deleteItem(item, amount);
    }

    public List<StashPair<Item, Integer>> getStash() {
        return stashRepository.getStorage() ;
    }
}
