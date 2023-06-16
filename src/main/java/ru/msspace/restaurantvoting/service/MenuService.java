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
@Transactional(readOnly = true)
public class MenuService {
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository repository;

    @Transactional
    public Menu save(int restaurantId, Menu menu) {
        Restaurant restaurantExisted = restaurantRepository.getExisted(restaurantId);
        menu.setRestaurant(restaurantExisted);
        return repository.save(menu);
    }

    @Transactional
    public void update(int restaurantId, Menu menu) {
        restaurantRepository.getExisted(restaurantId);
        Menu existedOrBelonged = repository.getExistedOrBelonged(restaurantId, menu.id());
        menu.setRestaurant(existedOrBelonged.getRestaurant());
        repository.save(menu);
    }

    @Transactional
    public void delete(int restaurantId, int menuId) {
        restaurantRepository.getExisted(restaurantId);
        repository.getExistedOrBelonged(restaurantId, menuId);
        repository.deleteExisted(menuId);
    }

    public Menu get(int restaurantId, int menuId) {
        restaurantRepository.getExisted(restaurantId);
        return repository.getExistedOrBelonged(restaurantId, menuId);
    }

    public Menu get(int restaurantId, LocalDate date) {
        restaurantRepository.getExisted(restaurantId);
        return repository.getExistedOrBelonged(restaurantId, date);
    }

    public List<MenuTo> getAllByDate(LocalDate date) {
        return MenuUtil.createTos(repository.getAllByDate(date));
    }

    public List<Menu> getAll(int restaurantId) {
        restaurantRepository.getExisted(restaurantId);
        return repository.getAll(restaurantId);
    }
}