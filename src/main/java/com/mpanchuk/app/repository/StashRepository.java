package com.mpanchuk.app.repository;

import com.mpanchuk.app.model.Item;
import com.mpanchuk.app.domain.StashPair;
import com.mpanchuk.app.service.JwtService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class StashRepository {

    // username - list<stash_pair>>
    Map<String, List<StashPair<Item, Integer>>> storage = new HashMap<>();

    @Autowired
    JwtService jwtService;

    public List<StashPair<Item, Integer>> addItem(String jwt, Item item, int amount) {
        String username = getUsername(jwt);
        List<StashPair<Item, Integer>> userStash = storage.getOrDefault(username, null) ;
        if (userStash == null) {
            userStash = new ArrayList<StashPair<Item, Integer>>();
        }

        Optional<StashPair<Item, Integer>> optionalStashPair = userStash.stream()
                .filter(pair -> pair.getFirst().getId().equals(item.getId()))
                .findFirst();

        if (optionalStashPair.isEmpty()) {
            userStash.add(new StashPair<>(item, amount));
        }

        else {
            StashPair<Item, Integer> pair = optionalStashPair.get();
            pair.setSecond(pair.getSecond() + amount);
        }

        storage.put(username, userStash);

        return storage.getOrDefault(username, null);
    }
    public List<StashPair<Item, Integer>> deleteItem(String jwt, Item item, int amount) {
        String username = getUsername(jwt);
        List<StashPair<Item, Integer>> stashPairList = storage.getOrDefault(username, null);

        if (stashPairList == null) {
            return null;
        }

        Optional<StashPair<Item, Integer>> optionalPair = stashPairList.stream()
                .filter(pair -> pair.getFirst().getId().equals(item.getId()))
                .findFirst();

        if (optionalPair.isEmpty()) {
            return stashPairList;
        }

        StashPair<Item, Integer> pair = optionalPair.get();

        int newAmount = Math.max(pair.getSecond() - amount, 0);

        if (newAmount == 0) {
            stashPairList.remove(pair);
        }

        else {
            pair.setSecond(newAmount);
        }

        storage.put(username, stashPairList);

        return stashPairList;
    }

    public List<StashPair<Item, Integer>> getStorage(String jwt) {
        String username = getUsername(jwt);
        return storage.getOrDefault(username, null);
    }

    public int calcPrice(String jwt) {
        String username = getUsername(jwt);
        List<StashPair<Item, Integer>> stashPairList = storage.getOrDefault(username, null);
        if (stashPairList == null) {
            return 0;
        }

        return stashPairList.stream()
                .mapToInt(pair -> pair.getFirst().getPrice() * pair.getSecond())
                .sum();

    }

    private String getUsername(String jwt) {
        return jwtService.extractUsername(jwt);
    }
}
