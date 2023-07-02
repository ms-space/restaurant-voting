package ru.msspace.restaurantvoting.web.restaurant;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.msspace.restaurantvoting.model.Restaurant;
import ru.msspace.restaurantvoting.repository.RestaurantRepository;
import ru.msspace.restaurantvoting.service.RestaurantService;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractRestaurantController {
    protected final Logger log = getLogger(getClass());

    @Autowired
    protected RestaurantRepository repository;

    @Autowired
    protected RestaurantService service;

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        log.info("get restaurant id={}", id);
        return service.get(id);
    }

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return repository.findAll();
    }
}