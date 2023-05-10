package ru.msspace.restaurantvoting.web.restaurant;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.msspace.restaurantvoting.model.Restaurant;
import ru.msspace.restaurantvoting.repository.RestaurantRepository;
import ru.msspace.restaurantvoting.service.RestaurantService;
import ru.msspace.restaurantvoting.web.AuthUser;

import java.net.URI;
import java.util.List;

import static ru.msspace.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;
import static ru.msspace.restaurantvoting.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class RestaurantController {
    static final String REST_URL = "/api/admin/restaurants";

    private final RestaurantRepository repository;
    private final RestaurantService service;

    @GetMapping("/{id}")
    public Restaurant get(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("get restaurant {} by user {}", id, authUser.id());
        return repository.getExisted(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("delete restaurant {} by user {}", id, authUser.id());
        repository.deleteExisted(id);
    }

    @GetMapping
    public List<Restaurant> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("getAll restaurants by user {}", authUser.id());
        return repository.findAll();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        log.info("update restaurant {} by user {}", restaurant, authUser.id());
        assureIdConsistent(restaurant, id);
        service.update(restaurant, id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody Restaurant restaurant) {
        log.info("create restaurant {} by user {}", restaurant, authUser.id());
        checkNew(restaurant);
        Restaurant created = repository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}