package ru.msspace.restaurantvoting.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.msspace.restaurantvoting.error.DataConflictException;
import ru.msspace.restaurantvoting.model.Menu;
import ru.msspace.restaurantvoting.model.Restaurant;
import ru.msspace.restaurantvoting.repository.MenuRepository;
import ru.msspace.restaurantvoting.repository.VoteRepository;
import ru.msspace.restaurantvoting.to.MenuTo;
import ru.msspace.restaurantvoting.util.MenuUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class MenuService {
    private final MenuRepository repository;
    private final VoteRepository voteRepository;
    private final RestaurantService restaurantService;

    @Transactional
    public Menu save(int restaurantId, Menu menu) {
        Restaurant restaurantExisted = getExistedRestaurant(restaurantId);
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
    public void delete(int id) {
        Menu existed = repository.getExisted(id);
        participatesInVoting(existed);
        repository.delete(existed);
    }

    public MenuTo get(int id) {
        return MenuUtil.createTo(repository.getExistedWithDishes(id));
    }

    public List<MenuTo> getAllByDate(LocalDate date) {
        List<Menu> menus = repository.getAllByDate(date);
        return !(menus.isEmpty()) ? MenuUtil.createTos(repository.getAllByDate(date)) : null;
    }

    public Optional<MenuTo> getByRestaurantAndDate(Integer restaurantId, LocalDate date) {
        getExistedRestaurant(restaurantId);
        Optional<Menu> menu = repository.getByRestaurantAndDate(restaurantId, date);
        return menu.map(MenuUtil::createTo);
    }

    public List<MenuTo> getAllByRestaurant(int restaurantId) {
        getExistedRestaurant(restaurantId);
        return MenuUtil.createTos(repository.getAllByRestaurant(restaurantId));
    }

    private void participatesInVoting(Menu menu) {
        if (voteRepository.getByRestaurantAndDate(menu.getRestaurant().id(), menu.getDate()).isPresent()) {
            throw new DataConflictException("Unable to delete menu due to voting");
        }
    }

    private Restaurant getExistedRestaurant(Integer restaurantId) {
        return restaurantService.get(restaurantId);
    }
}