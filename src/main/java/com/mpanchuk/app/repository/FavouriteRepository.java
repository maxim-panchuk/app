package com.mpanchuk.app.repository;

import com.mpanchuk.app.model.Item;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FavouriteRepository {
    List<Item> storage = new ArrayList<>() ;

    public List<Item> addItem(Item item) {
        Optional<Item> itm = storage.stream()
                .filter(i -> i.getId().equals(item.getId()))
                .findFirst();

        if (itm.isEmpty()) {
            storage.add(item);
        }

        return storage ;
    }

    public List<Item> deleteItem(Item item) {
        Optional<Item> itm = storage.stream()
                .filter(i -> i.getId().equals(item.getId()))
                .findFirst();

        if (itm.isPresent()) {
            storage.remove(item) ;
        }

        return storage ;
    }

    public List<Item> getAll() {
        return storage ;
    }
}
