package ru.msspace.restaurantvoting.web.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msspace.restaurantvoting.service.MenuService;
import ru.msspace.restaurantvoting.to.MenuTo;

import java.time.LocalDate;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractMenuController {
    protected final Logger log = getLogger(getClass());

    @Autowired
    protected MenuService service;

    @GetMapping("/menus/by-date")
    public List<MenuTo> getAllByDate(
            @Schema(description = "Current date if empty", format = "YYYY-MM-DD")
            @Nullable @RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        log.info("get all menus by date {}", date);
        return service.getAllByDate(date);
    }
}