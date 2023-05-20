package com.mpanchuk.app.mapper;

import com.mpanchuk.app.domain.request.ItemRequest;
import com.mpanchuk.app.domain.response.ItemResponse;
import com.mpanchuk.app.model.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    public Item toItem(ItemRequest request) {
        Item item = new Item();
        if (request.name() != null)
            item.setName(request.name());
        if (request.price() != null)
            item.setPrice(request.price());
        return item;
    }

    public ItemResponse toResponse(Item item) {
        ItemResponse itemResponse = new ItemResponse();
        if (item.getId() != null)
            itemResponse.setId(item.getId());
        if (item.getPrice() != null)
            itemResponse.setPrice(item.getPrice());
        if (item.getName() != null)
            itemResponse.setName(item.getName());
        return itemResponse;
    }
}
