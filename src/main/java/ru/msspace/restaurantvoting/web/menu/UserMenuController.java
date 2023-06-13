package ru.msspace.restaurantvoting.web.menu;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.msspace.restaurantvoting.to.MenuTo;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = UserMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserMenuController extends AbstractMenuController {
    static final String REST_URL = "/api/user/menus/today";

    @GetMapping
    public List<MenuTo> getAllToday() {
        LocalDate date = LocalDate.now();
        return super.getAllByDate(date);
    }
}