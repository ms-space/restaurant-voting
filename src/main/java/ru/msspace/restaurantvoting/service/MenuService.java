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
    public MenuTo save(int restaurantId, MenuTo menuTo) {
        Restaurant restaurantExisted = restaurantRepository.getExisted(restaurantId);
        Menu menu = MenuUtil.createNewFromTo(menuTo);
        menu.setRestaurant(restaurantExisted);
        return MenuUtil.createTo(repository.save(menu));
    }

    @Transactional
    public void update(int restaurantId, MenuTo menuTo) {
        restaurantRepository.getExisted(restaurantId);
        Menu existedOrBelonged = repository.getExistedOrBelonged(restaurantId, menuTo.getId());
        Menu menu = MenuUtil.createNewFromTo(menuTo);
        menu.setRestaurant(existedOrBelonged.getRestaurant());
        repository.save(menu);
    }

    @Transactional
    public void delete(int restaurantId, int menuId) {
        restaurantRepository.getExisted(restaurantId);
        repository.getExistedOrBelonged(restaurantId, menuId);
        repository.deleteExisted(menuId);
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