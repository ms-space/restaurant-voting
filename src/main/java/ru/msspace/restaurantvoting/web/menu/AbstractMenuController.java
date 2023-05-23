package ru.msspace.restaurantvoting.web.menu;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.msspace.restaurantvoting.repository.MenuRepository;
import ru.msspace.restaurantvoting.to.MenuTo;
import ru.msspace.restaurantvoting.util.DateTimeUtil;
import ru.msspace.restaurantvoting.util.MenuUtil;

import java.time.LocalDate;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractMenuController {
    protected final Logger log = getLogger(getClass());

    @Autowired
    protected MenuRepository repository;

    public List<MenuTo> getAllByDate(LocalDate date) {
        date = DateTimeUtil.checkAndSetDate(date);
        log.info("get all menus by date {}", date);
        return MenuUtil.createTos(repository.getAllByDate(date));
    }
}