package ru.msspace.restaurantvoting.web.menu;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
    @GetMapping("/menus")
    public List<MenuTo> getAllByDate(@Nullable @RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return super.getAllByDate(date);
    }

    @GetMapping("/{restaurantId}/menus")
    public List<MenuTo> getAll(@PathVariable int restaurantId) {
        log.info("get all menu from restaurant id={}", restaurantId);
        return MenuUtil.createTos(service.getAll(restaurantId));
    }

    @PostMapping(value = "/{restaurantId}/menus", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuTo> create(@Valid @RequestBody MenuTo menuTo, @PathVariable int restaurantId) {
        log.info("create menu {} for restaurant id={}", menuTo, restaurantId);
        checkNew(menuTo);
        MenuTo created = service.save(restaurantId, menuTo);
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
        log.info("update menu {} for restaurant id={}", menuTo, restaurantId);
        assureIdConsistent(menuTo, menuId);
        service.update(restaurantId, menuTo);
    }

    @DeleteMapping("/{restaurantId}/menus/{menuId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable int menuId) {
        log.info("delete  menu id={} for restaurant id={}", menuId, restaurantId);
        service.delete(restaurantId, menuId);
    }
}