package com.mpanchuk.app.service;

import com.mpanchuk.app.domain.ItemResponse;
import com.mpanchuk.app.model.Item;
import com.mpanchuk.app.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ItemService {

    private final ItemRepository itemTestRepository;

    @Autowired
    public ItemService(ItemRepository itemTestRepository) {
        this.itemTestRepository = itemTestRepository;
    }

    public Page<ItemResponse> getAllItems(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Item> entityItems = itemTestRepository.findAll(pageable);
        return entityItems.map(item -> new ItemResponse(item.getId(), item.getName(), item.getPrice()));
    }

    public Page<ItemResponse> getItemByRegexp(String regexp, int pageNo, int pageSize) {
        Page<Item> entityItems = itemTestRepository.findByNameLike(regexp, PageRequest.of(pageNo, pageSize)).orElseThrow();
        return entityItems.map(item -> new ItemResponse(item.getId(), item.getName(), item.getPrice()));
    }

    public Item getItemById(Long id) throws NoSuchElementException {
        return itemTestRepository.findById(id).orElseThrow();
    }
}
