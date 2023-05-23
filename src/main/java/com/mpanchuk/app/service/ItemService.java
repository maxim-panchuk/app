package com.mpanchuk.app.service;

import com.mpanchuk.app.domain.request.ItemToAddRequest;
import com.mpanchuk.app.domain.response.ItemResponse;
import com.mpanchuk.app.domain.response.ItemToAddResponse;
import com.mpanchuk.app.exception.ItemToAddValidationException;
import com.mpanchuk.app.mapper.ItemToAddMapper;
import com.mpanchuk.app.model.Item;
import com.mpanchuk.app.repository.CityRepository;
import com.mpanchuk.app.repository.ItemRepository;
import com.mpanchuk.app.repository.ItemToAddRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemTestRepository;
    private final ItemToAddRepository itemToAddRepository;
    private final ItemToAddMapper itemToAddMapper;
    private final CityRepository cityRepository;

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

    /**
     * SUPPLIER
     */
    @Transactional
    public void addItemFromSupplier(List<ItemToAddRequest> request) {
        request.forEach(it -> {
            validateItemToAddRequest(it);
            var entity = itemToAddMapper.toEntity(it);
            itemToAddRepository.save(entity);
        });
    }


    /**
     * MANAGER
     */
    public Page<ItemToAddResponse> getItemsToAdd(Integer pageNumber, Integer pageSize) {
        var pageable = PageRequest.of(pageNumber, pageSize);
        var itemsToAdd = itemToAddRepository.findAll(pageable);
        return itemsToAdd.map(itemToAddMapper::toResponse);
    }


    private void validateItemToAddRequest(ItemToAddRequest request) {
        cityRepository.findByName(request.city()).orElseThrow(
                () -> new ItemToAddValidationException(String.format("Города %s не существует!", request.city())));
        if (request.price() < 0)
            throw new ItemToAddValidationException(String.format("Цена не может быть меньше 0! %s", request.price()));
        if (request.name() == null || request.name().equals(""))
            throw new ItemToAddValidationException("Название товара не может быть пустым!");
    }
}
