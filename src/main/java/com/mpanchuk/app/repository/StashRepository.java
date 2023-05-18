package com.mpanchuk.app.repository;

import com.mpanchuk.app.model.Item;
import com.mpanchuk.app.domain.StashPair;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class StashRepository {
    // item - amount
    List<StashPair<Item, Integer>> storage = new ArrayList<>() ;
    public List<StashPair<Item, Integer>> addItem(Item item, int amount) {
        Optional<StashPair<Item, Integer>> optionalPair = storage.stream()
                .filter(pair -> pair.getFirst().getId().equals(item.getId()))
                .findFirst();

        if (optionalPair.isEmpty()) {
            storage.add(new StashPair<>(item, amount));
        }

        else {
            StashPair<Item, Integer> pair = optionalPair.get();
            pair.setSecond(pair.getSecond() + amount);
        }

        return storage;
    }

    public List<StashPair<Item, Integer>> deleteItem(Item item, int amount) {
        Optional<StashPair<Item, Integer>> optionalPair = storage.stream()
                .filter(pair -> pair.getFirst().getId().equals(item.getId()))
                .findFirst();

        if (optionalPair.isEmpty()) {
            return storage;
        }

        StashPair<Item, Integer> pair = optionalPair.get();

        int newAmount = Math.max(pair.getSecond() - amount, 0);

        if (newAmount == 0) {
            storage.remove(pair);
        }

        else {
            pair.setSecond(newAmount);
        }

        return storage;
    }

    public List<StashPair<Item, Integer>> getStorage() {
        return storage ;
    }

    public int calcPrice() {
        return storage.stream()
                .mapToInt(pair -> pair.getFirst().getPrice() * pair.getSecond())
                .sum();
    }
}
