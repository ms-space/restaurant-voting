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

    @Query("SELECT m FROM Menu m JOIN FETCH m.dishes WHERE m.id=:menuId AND m.restaurant.id=:restaurantId")
    Optional<Menu> getByIdAndRestaurant(int menuId, int restaurantId);

    @Query("SELECT m FROM Menu m JOIN FETCH m.dishes WHERE m.restaurant.id=:restaurantId")
    List<Menu> getAllByRestaurant(int restaurantId);

    @Query("SELECT m FROM Menu m JOIN FETCH m.dishes WHERE m.date=:date")
    List<Menu> getAllByDate(LocalDate date);

    default Menu findExistedOrBelonged(int menuId, int restaurantId) {
        return getByIdAndRestaurant(menuId, restaurantId).orElseThrow(
                () -> new NotFoundException("Menu with id=" + menuId + " and with restaurant id= " + restaurantId + " not found"));
    }

    default Menu getExistedOrBelonged(int restaurantId, LocalDate date) {
        return getByRestaurantAndDate(restaurantId, date).orElseThrow(
                () -> new DataConflictException("Menu with date=" + date + " is not exist or doesn't belong restaurant id=" + restaurantId));
    }

    default Menu getExistedOrBelonged(int menuId, int restaurantId) {
        return getByIdAndRestaurant(menuId, restaurantId).orElseThrow(
                () -> new DataConflictException("Menu with id=" + menuId + " is not exist or doesn't belong restaurant id=" + restaurantId));
    }
}