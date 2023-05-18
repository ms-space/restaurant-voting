package ru.msspace.restaurantvoting.web.restaurant;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.msspace.restaurantvoting.model.Restaurant;
import ru.msspace.restaurantvoting.service.RestaurantService;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractRestaurantController {
    protected final Logger log = getLogger(getClass());

    @Autowired
    protected RestaurantService service;

    public Restaurant get(int id) {
        log.info("get restaurant id={}", id);
        return service.getExisted(id);
    }
}