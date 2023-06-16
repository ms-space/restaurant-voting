package ru.msspace.restaurantvoting.web.menu;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.msspace.restaurantvoting.service.MenuService;
import ru.msspace.restaurantvoting.to.MenuTo;

import java.time.LocalDate;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractMenuController {
    protected final Logger log = getLogger(getClass());

    @Autowired
    protected MenuService service;

    public List<MenuTo> getAllByDate(LocalDate date) {
        log.info("get all menus by date {}", date);
        return service.getAllByDate(date);
    }
}