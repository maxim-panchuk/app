package com.mpanchuk.app.service;

import com.mpanchuk.app.domain.request.ItemRequest;
import com.mpanchuk.app.domain.response.ItemResponse;
import com.mpanchuk.app.exception.NoSuchItemException;
import com.mpanchuk.app.mapper.ItemMapper;
import com.mpanchuk.app.model.Item;
import com.mpanchuk.app.repository.ItemRepository;
import com.mpanchuk.app.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemTestRepository;
    private final SupplierRepository supplierRepository;
    private final ItemMapper mapper;

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

    public void addItemFromSupplier(ItemRequest request) {
        supplierRepository.addItemFromSupplier(mapper.toItem(request));
    }

    public ItemResponse getItem() {
        return mapper.toResponse(supplierRepository
                .getItemFromManager()
                .orElseThrow(() -> new NoSuchItemException("Товары закончились!")));
    }
}
