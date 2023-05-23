package com.mpanchuk.app.mapper;

import com.mpanchuk.app.domain.request.ItemToAddRequest;
import com.mpanchuk.app.domain.response.ItemToAddResponse;
import com.mpanchuk.app.model.ItemToAdd;
import org.springframework.stereotype.Component;

@Component
public class ItemToAddMapper {
    public ItemToAdd toEntity(ItemToAddRequest item) {
        return ItemToAdd.builder()
                .price(item.price())
                .name(item.name())
                .cityName(item.city())
                .build();
    }

    public ItemToAddResponse toResponse(ItemToAdd item) {
        return ItemToAddResponse.builder()
                .id(item.getId())
                .city(item.getCityName())
                .price(item.getPrice())
                .name(item.getName())
                .build();
    }
}
