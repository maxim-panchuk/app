package com.mpanchuk.app.service;

import com.mpanchuk.app.model.Item;
import com.mpanchuk.app.repository.FavouriteRepository;
import com.mpanchuk.app.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FavouriteService {
    private final ItemRepository itemRepository;
    private final FavouriteRepository favouriteRepository;

    @Autowired
    public FavouriteService(ItemRepository itemRepository, FavouriteRepository favouriteRepository) {
        this.itemRepository = itemRepository;
        this.favouriteRepository = favouriteRepository;
    }

    public List<Item> getAll() {
        return this.favouriteRepository.getAll();
    }

    public List<Item> addItem(Long itemId) throws NoSuchElementException {
        Item item = itemRepository.findById(itemId).orElseThrow();
        return favouriteRepository.addItem(item);
    }

    public List<Item> deleteItem(Long itemId) throws NoSuchElementException {
        Item item = itemRepository.findById(itemId).orElseThrow();
        return favouriteRepository.deleteItem(item);
    }
}
