package ru.msspace.restaurantvoting.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.msspace.restaurantvoting.model.Restaurant;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

}