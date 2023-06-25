package ru.msspace.restaurantvoting.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.msspace.restaurantvoting.model.Menu;
import ru.msspace.restaurantvoting.model.Restaurant;
import ru.msspace.restaurantvoting.repository.MenuRepository;
import ru.msspace.restaurantvoting.to.MenuTo;
import ru.msspace.restaurantvoting.util.MenuUtil;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class MenuService {
    private final MenuRepository repository;
    private final RestaurantService restaurantService;

    @Transactional
    public Menu save(int restaurantId, Menu menu) {
        Restaurant restaurantExisted = restaurantService.get(restaurantId);
        menu.setRestaurant(restaurantExisted);
        return repository.save(menu);
    }

    @Transactional
    public void update(Menu menu, int id) {
        Menu existed = repository.getExisted(id);
        menu.setRestaurant(existed.getRestaurant());
        repository.save(menu);
    }

    @Transactional
    public void delete(int restaurantId, int id) {
        repository.getExistedOrBelonged(id, restaurantId);
        repository.deleteExisted(id);
    }

    public MenuTo get(int id) {
        return MenuUtil.createTo(repository.getExisted(id));
    }

    public List<MenuTo> getAllByDate(LocalDate date) {
        return MenuUtil.createTos(repository.getAllByDate(date));
    }

    public List<MenuTo> getAllByRestaurant(int restaurantId) {
        return MenuUtil.createTos(repository.getAllByRestaurant(restaurantId));
    }
}