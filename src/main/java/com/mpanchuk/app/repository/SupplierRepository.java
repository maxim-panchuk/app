package com.mpanchuk.app.repository;

import com.mpanchuk.app.exception.NoSuchItemException;
import com.mpanchuk.app.model.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SupplierRepository {
    private final RedisTemplate<String, Item> itemRedisTemplate;

    public void addItemFromSupplier(Item item) {
        itemRedisTemplate.opsForList().leftPush("item", item);
    }

    public Optional<Item> getItemFromManager() {
        return Optional.ofNullable(itemRedisTemplate.opsForList().rightPop("item"));
    }
}
