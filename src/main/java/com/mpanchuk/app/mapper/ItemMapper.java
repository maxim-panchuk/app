package com.mpanchuk.app.mapper;

import com.mpanchuk.app.domain.request.ItemToAddRequest;
import com.mpanchuk.app.domain.response.ItemToAddResponse;
import com.mpanchuk.app.model.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    public Item toItem(ItemToAddRequest request) {
        Item item = new Item();
        if (request.name() != null)
            item.setName(request.name());
        if (request.price() != null)
            item.setPrice(request.price());
        return item;
    }

    public ItemToAddResponse toResponse(Item item) {
        ItemToAddResponse itemToAddResponse = new ItemToAddResponse();
        if (item.getId() != null)
            itemToAddResponse.setId(item.getId());
        if (item.getPrice() != null)
            itemToAddResponse.setPrice(item.getPrice());
        if (item.getName() != null)
            itemToAddResponse.setName(item.getName());
        return itemToAddResponse;
    }
}
