package ru.msspace.restaurantvoting.web.menu;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.msspace.restaurantvoting.to.MenuTo;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = UserMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class UserMenuController extends AbstractMenuController {
    static final String REST_URL = "/api/user/menus";

    @GetMapping
    public List<MenuTo> getForToday() {
        LocalDate date = LocalDate.now();
        log.info("get for today menu, date={}", date);
        return super.getAllByDate(date);
    }
}