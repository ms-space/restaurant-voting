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

    @Query("SELECT m FROM Menu m JOIN FETCH m.dishes WHERE m.id=:id AND m.restaurant.id=:restaurantId")
    Optional<Menu> getByIdAndRestaurant(int id, int restaurantId);

    @Query("SELECT m FROM Menu m LEFT JOIN FETCH m.dishes WHERE m.id=:id")
    Optional<Menu> findExisted(int id);

    @Query("SELECT m FROM Menu m JOIN FETCH m.dishes WHERE m.restaurant.id=:restaurantId")
    List<Menu> getAllByRestaurant(int restaurantId);

    @Query("SELECT m FROM Menu m JOIN FETCH m.dishes WHERE m.date=:date")
    List<Menu> getAllByDate(LocalDate date);

    @Override
    default Menu getExisted(int id) {
        return findExisted(id).orElseThrow(
                () -> new NotFoundException("Menu with id=" + id + " not found"));
    }

    default Menu getExistedOrBelonged(int restaurantId, LocalDate date) {
        return getByRestaurantAndDate(restaurantId, date).orElseThrow(
                () -> new DataConflictException("Menu with date=" + date + " is not exist or doesn't belong restaurant id=" + restaurantId));
    }

    default Menu getExistedOrBelonged(int id, int restaurantId) {
        return getByIdAndRestaurant(id, restaurantId).orElseThrow(
                () -> new DataConflictException("Menu with id=" + id + " is not exist or doesn't belong restaurant id=" + restaurantId));
    }
}