package ru.msspace.restaurantvoting.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.msspace.restaurantvoting.model.Menu;
import ru.msspace.restaurantvoting.model.Restaurant;
import ru.msspace.restaurantvoting.repository.MenuRepository;
import ru.msspace.restaurantvoting.repository.RestaurantRepository;
import ru.msspace.restaurantvoting.to.MenuTo;
import ru.msspace.restaurantvoting.util.MenuUtil;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class MenuService {
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository repository;

    @Transactional
    public Menu save(int restaurantId, MenuTo menuTo) {
        Restaurant restaurantExisted = restaurantRepository.getExisted(restaurantId);
        Menu created = MenuUtil.createNewFromTo(menuTo);
        created.setRestaurant(restaurantExisted);
        return repository.save(created);
    }

    @Transactional
    public void update(int restaurantId, MenuTo menuTo) {
        restaurantRepository.getExisted(restaurantId);
        Menu existedOrBelonged = repository.getExistedOrBelonged(restaurantId, menuTo.getId());
        Menu updated = MenuUtil.createNewFromTo(menuTo);
        updated.setRestaurant(existedOrBelonged.getRestaurant());
        repository.save(updated);
    }

    @Transactional
    public Menu get(int restaurantId, int menuId) {
        restaurantRepository.getExisted(restaurantId);
        return repository.getExistedOrBelonged(restaurantId, menuId);
    }

    @Transactional
    public Menu get(int restaurantId, LocalDate date) {
        restaurantRepository.getExisted(restaurantId);
        return repository.getExistedOrBelonged(restaurantId, date);
    }

    @Transactional
    public List<Menu> getAll(int restaurantId) {
        restaurantRepository.getExisted(restaurantId);
        return repository.getAll(restaurantId);
    }
}