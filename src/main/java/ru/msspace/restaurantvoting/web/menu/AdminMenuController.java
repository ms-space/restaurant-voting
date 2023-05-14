package ru.msspace.restaurantvoting.web.menu;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.msspace.restaurantvoting.service.MenuService;
import ru.msspace.restaurantvoting.to.MenuTo;
import ru.msspace.restaurantvoting.util.MenuUtil;
import ru.msspace.restaurantvoting.web.AuthUser;

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
    public MenuTo get(@AuthenticationPrincipal AuthUser authUser,
                      @PathVariable int restaurantId,
                      @PathVariable int menuId) {
        log.info("get menu id={} from restaurant id={} by user {}", menuId, restaurantId, authUser.id());
        return MenuUtil.createTo(service.get(restaurantId, menuId));
    }

    @GetMapping(value = "menus/by-date")
    public List<MenuTo> getAllByDate(@AuthenticationPrincipal AuthUser authUser,
                                     @Nullable @RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("getAllByDate date={} by user {}", date, authUser.id());
        if (date == null) {
            date = LocalDate.now();
        }
        return super.getAllByDate(date);
    }

    @GetMapping(value = "/{restaurantId}/menus")
    public List<MenuTo> getAll(@AuthenticationPrincipal AuthUser authUser,
                               @PathVariable int restaurantId) {
        log.info("get all menu from restaurant id={} by user {}", restaurantId, authUser.id());
        return MenuUtil.createTos(service.getAll(restaurantId));
    }

    @PostMapping(value = "/{restaurantId}/menus", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuTo> create(@AuthenticationPrincipal AuthUser authUser,
                                         @Valid @RequestBody MenuTo menuTo,
                                         @PathVariable int restaurantId) {
        log.info("create menu on date {} for restaurant {} by user {}", menuTo.getDate(), restaurantId, authUser.id());
        checkNew(menuTo);
        MenuTo created = MenuUtil.createTo(service.save(restaurantId, menuTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{restaurant_id}/menus/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{restaurantId}/menus/{menuId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser,
                       @Valid @RequestBody MenuTo menuTo,
                       @PathVariable int restaurantId,
                       @PathVariable int menuId) {
        log.info("update menu id={} {} for restaurant id={} by user {}", menuId, menuTo, restaurantId, authUser.id());
        assureIdConsistent(menuTo, menuId);
        service.update(restaurantId, menuTo);
    }
}