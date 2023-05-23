package com.mpanchuk.app.service;

import com.mpanchuk.app.domain.StashPair;
import com.mpanchuk.app.model.Item;
import com.mpanchuk.app.repository.ItemRepository;
import com.mpanchuk.app.repository.StashRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class StashService {
    private final ItemRepository itemRepository;
    private final StashRepository stashRepository;

    @Autowired
    public StashService(ItemRepository itemRepository, StashRepository stashRepository) {
        this.itemRepository = itemRepository;
        this.stashRepository = stashRepository;
    }

    public List<StashPair<Item, Integer>> addItem(String jwt, Long itemId, int amount) throws NoSuchElementException {
        Item item = itemRepository.findById(itemId).orElseThrow();
        return stashRepository.addItem(jwt, item, amount);
    }

    public List<StashPair<Item, Integer>> deleteItem(String jwt, Long itemId, int amount) throws NoSuchElementException {
        Item item = itemRepository.findById(itemId).orElseThrow();
        return stashRepository.deleteItem(jwt, item, amount);
    }

    public List<StashPair<Item, Integer>> getStash(String jwt) {
        return stashRepository.getStorage(jwt);
    }
}
