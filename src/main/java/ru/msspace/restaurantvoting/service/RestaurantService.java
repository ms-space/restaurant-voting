package ru.msspace.restaurantvoting.service;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.msspace.restaurantvoting.model.Restaurant;
import ru.msspace.restaurantvoting.repository.RestaurantRepository;

@Service
@AllArgsConstructor
@CacheConfig(cacheNames = "restaurants")
public class RestaurantService {
    RestaurantRepository repository;

    @Transactional
    @CacheEvict(key = "#id")
    public void update(Restaurant restaurant, int id) {
        repository.getExisted(id);
        repository.save(restaurant);
    }

    @Cacheable
    public Restaurant get(int id) {
        return repository.getExisted(id);
    }

    @CacheEvict
    public void delete(int id) {
        repository.deleteExisted(id);
    }
}