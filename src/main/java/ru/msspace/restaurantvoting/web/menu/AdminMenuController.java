package ru.msspace.restaurantvoting.web.menu;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.msspace.restaurantvoting.model.Menu;
import ru.msspace.restaurantvoting.repository.MenuRepository;
import ru.msspace.restaurantvoting.to.MenuTo;
import ru.msspace.restaurantvoting.util.MenuUtil;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.msspace.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;
import static ru.msspace.restaurantvoting.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AdminMenuController extends AbstractMenuController {
    static final String REST_URL = "/api/admin";

    private final MenuRepository repository;

    @GetMapping("/menus/{id}")
    public MenuTo get(@PathVariable int id) {
        log.info("get menu with id={}", id);
        return service.get(id);
    }

    @GetMapping("/menus/today")
    public List<MenuTo> getAllByToday() {
        LocalDate date = LocalDate.now();
        return super.getAllByDate(date);
    }

    @Override
    @GetMapping("/menus/by-date")
    public List<MenuTo> getAllByDate(@RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return super.getAllByDate(date);
    }

    @GetMapping("restaurants/{restaurantId}/menus")
    public List<MenuTo> getAll(@PathVariable int restaurantId) {
        log.info("get all menu for restaurant with id={}", restaurantId);
        return service.getAllByRestaurant(restaurantId);
    }

    @PostMapping(value = "/restaurants/{restaurantId}/menus", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuTo> create(@Valid @RequestBody MenuTo menuTo, @PathVariable int restaurantId) {
        log.info("create {} for restaurant with id={}", menuTo, restaurantId);
        checkNew(menuTo);
        Menu menu = MenuUtil.createNewFromTo(menuTo);
        MenuTo created = MenuUtil.createTo(service.save(restaurantId, menu));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/menus/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/menus/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody MenuTo menuTo,
                       @PathVariable int id) {
        log.info("update {} with id={}", menuTo, id);
        assureIdConsistent(menuTo, id);
        Menu menu = MenuUtil.createNewFromTo(menuTo);
        service.update(menu, id);
    }

    @DeleteMapping("/menus/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete menu with id={}", id);
        repository.deleteExisted(id);
    }
}