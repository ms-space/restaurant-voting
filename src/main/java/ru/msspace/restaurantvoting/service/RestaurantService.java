package ru.msspace.restaurantvoting.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.msspace.restaurantvoting.model.Restaurant;
import ru.msspace.restaurantvoting.repository.RestaurantRepository;

@Service
@AllArgsConstructor
public class RestaurantService {
    RestaurantRepository repository;

    @Transactional
    public void update(Restaurant restaurant, int id) {
        repository.getExisted(id);
        repository.save(restaurant);
    }
}