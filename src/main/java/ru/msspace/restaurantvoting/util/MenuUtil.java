package ru.msspace.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.msspace.restaurantvoting.model.Menu;
import ru.msspace.restaurantvoting.to.MenuTo;

import java.util.List;

@UtilityClass
public class MenuUtil {

    @SuppressWarnings("all")
    public static MenuTo createTo(Menu menu) {
        return new MenuTo(menu.getId(), menu.getRestaurant().id(), menu.getDate(), menu.getDishes());
    }

    public static List<MenuTo> createTos(List<Menu> menus) {
        return menus.stream().map(MenuUtil::createTo).toList();
    }

    public static Menu createNewFromTo(MenuTo menuTo) {
        return new Menu(menuTo.getId(), null, menuTo.getDate(), menuTo.getDishes());
    }
}
