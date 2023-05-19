package com.mpanchuk.app.service;


import com.mpanchuk.app.domain.CityDistancePair;
import com.mpanchuk.app.domain.OrderResponse;
import com.mpanchuk.app.domain.StashPair;
import com.mpanchuk.app.exception.PriceException;
import com.mpanchuk.app.model.City;
import com.mpanchuk.app.model.Coupon;
import com.mpanchuk.app.model.Item;
import com.mpanchuk.app.repository.CityRepository;
import com.mpanchuk.app.repository.CouponRepository;
import com.mpanchuk.app.repository.ItemRepository;
import com.mpanchuk.app.repository.StashRepository;
import com.mpanchuk.app.util.graph.RouteFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OrderService {

    private final StashRepository stashRepository;
    private final CityRepository cityRepository;
    private final ItemRepository itemRepository;
    private final CouponRepository couponRepository;

    private final RouteFinder routeFinder;

    @Autowired
    public OrderService(StashRepository stashRepository,
                        CityRepository cityRepository,
                        ItemRepository itemRepository,
                        RouteFinder routeFinder,
                        CouponRepository couponRepository) {
        this.stashRepository = stashRepository;
        this.cityRepository = cityRepository;
        this.itemRepository = itemRepository;
        this.routeFinder = routeFinder;
        this.couponRepository = couponRepository;
    }

    public OrderResponse makeOrder(String destination, String coupon) throws NoSuchElementException, PriceException {
        if (!checkOrderSum()) {
            throw new PriceException();
        }

        Optional<City> optionalCity = cityRepository.findByName(destination);
        if (optionalCity.isEmpty()) {
            throw new NoSuchElementException("city");
        }
        Optional<Coupon> cp = couponRepository.findByName(coupon);
        City destinationCity = cityRepository.findByName(destination).orElseThrow();
        List<StashPair<Item, Integer>> storage = stashRepository.getStorage();
        HashMap<String, CityDistancePair<String, Integer>> itemToCity = new HashMap<>();

        for (StashPair<Item, Integer> pair : storage) {
            List<City> cities = itemRepository.findById(pair.getFirst().getId()).orElseThrow().getCities();
            int[] result = routeFinder.findRoute(destinationCity.getId(), cities);
            String cityNameFrom = cityRepository.findById((long) result[0]).orElseThrow().getName();
            if (result[1] > 1200) {
                itemToCity.put(pair.getFirst().getName(), new CityDistancePair<>("Невозможно доставить, дистанция больше 1200", result[1]));
            } else
                itemToCity.put(pair.getFirst().getName(), new CityDistancePair<>(cityNameFrom, result[1]));
        }


        int price = stashRepository.calcPrice();
        if (cp.isEmpty()) {
            return new OrderResponse(itemToCity, destination, price, 0, price, "Без купона");
        }
        int discount = (int) (price * cp.get().getDiscount());
        return new OrderResponse(itemToCity, destination, price, discount, price - discount, "Купон применен");
    }


    private boolean checkOrderSum() {
        return stashRepository.calcPrice() > 1000;
    }
}
