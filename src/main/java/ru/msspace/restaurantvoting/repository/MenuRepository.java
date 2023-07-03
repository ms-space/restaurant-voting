package ru.msspace.restaurantvoting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.msspace.restaurantvoting.error.DataConflictException;
import ru.msspace.restaurantvoting.error.NotFoundException;
import ru.msspace.restaurantvoting.model.Menu;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {

    @Query("SELECT m FROM Menu m JOIN FETCH m.dishes WHERE m.restaurant.id=:restaurantId AND m.date=:date")
    Optional<Menu> getByRestaurantAndDate(int restaurantId, LocalDate date);

    @Query("SELECT m FROM Menu m JOIN FETCH m.dishes WHERE m.id=:id")
    Optional<Menu> getWithDishes(int id);

    @Query("SELECT m FROM Menu m JOIN FETCH m.dishes WHERE m.restaurant.id=:restaurantId")
    List<Menu> getAllByRestaurant(int restaurantId);

    @Query("SELECT m FROM Menu m JOIN FETCH m.dishes WHERE m.date=:date")
    List<Menu> getAllByDate(LocalDate date);

    default Menu getExistedWithDishes(int id) {
        return getWithDishes(id).orElseThrow(
                () -> new NotFoundException("Menu with id=" + id + " not found"));
    }

    default Menu getExistedOrBelonged(int restaurantId, LocalDate date) {
        return getByRestaurantAndDate(restaurantId, date).orElseThrow(
                () -> new DataConflictException("Menu with date=" + date + " is not exist or doesn't belong restaurant id=" + restaurantId));
    }
}