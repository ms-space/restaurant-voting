package ru.msspace.restaurantvoting.web.menu;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.msspace.restaurantvoting.service.MenuService;
import ru.msspace.restaurantvoting.to.MenuTo;
import ru.msspace.restaurantvoting.util.MenuUtil;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.msspace.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;
import static ru.msspace.restaurantvoting.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class AdminMenuController extends AbstractMenuController {
    static final String REST_URL = "/api/admin/restaurants";

    private final MenuService service;

    @GetMapping("{restaurantId}/menus/{menuId}")
    public MenuTo get(@PathVariable int restaurantId, @PathVariable int menuId) {
        log.info("get menu id={} from restaurant id={}", menuId, restaurantId);
        return MenuUtil.createTo(service.get(restaurantId, menuId));
    }

    @Override
    @GetMapping(value = "menus/by-date")
    public List<MenuTo> getAllByDate(@Nullable @RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        return super.getAllByDate(date);
    }

    @GetMapping(value = "/{restaurantId}/menus")
    public List<MenuTo> getAll(@PathVariable int restaurantId) {
        log.info("get all menu from restaurant id={}", restaurantId);
        return MenuUtil.createTos(service.getAll(restaurantId));
    }

    @PostMapping(value = "/{restaurantId}/menus", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuTo> create(@Valid @RequestBody MenuTo menuTo, @PathVariable int restaurantId) {
        log.info("create menu on date {} for restaurant id={}", menuTo.getDate(), restaurantId);
        checkNew(menuTo);
        MenuTo created = MenuUtil.createTo(service.save(restaurantId, menuTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{restaurant_id}/menus/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{restaurantId}/menus/{menuId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody MenuTo menuTo,
                       @PathVariable int restaurantId,
                       @PathVariable int menuId) {
        log.info("update menu id={} {} for restaurant id={}", menuId, menuTo, restaurantId);
        assureIdConsistent(menuTo, menuId);
        service.update(restaurantId, menuTo);
    }
}