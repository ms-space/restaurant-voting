package ru.msspace.restaurantvoting.web.menu;

import ru.msspace.restaurantvoting.model.Dish;
import ru.msspace.restaurantvoting.model.Menu;
import ru.msspace.restaurantvoting.to.MenuTo;
import ru.msspace.restaurantvoting.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;

import static java.time.LocalDate.of;
import static ru.msspace.restaurantvoting.web.restaurant.RestaurantTestData.restaurant1;
import static ru.msspace.restaurantvoting.web.restaurant.RestaurantTestData.restaurant2;

public class MenuTestData {
    public static final MatcherFactory.Matcher<MenuTo> MENU_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(MenuTo.class);
    public static final MatcherFactory.Matcher<MenuTo> MENU_TO_MATCHER_IGNORE_DISH_ID =
            MatcherFactory.usingIgnoringFieldsComparator(MenuTo.class, "dishes.id");

    public static final int MENU1_ID = 1;
    public static final int MENU4_ID = MENU1_ID + 3;
    public static final int DISH1_ID = 1;

    public static final Dish dish1 = new Dish(DISH1_ID, "Dish1", 200);
    public static final Dish dish2 = new Dish(DISH1_ID + 1, "Dish2", 220);
    public static final Dish dish3 = new Dish(DISH1_ID + 2, "Dish3", 300);
    public static final Dish dish4 = new Dish(DISH1_ID + 3, "Dish4", 250);
    public static final Dish dish5 = new Dish(DISH1_ID + 4, "Dish5", 200);
    public static final Dish dish6 = new Dish(DISH1_ID + 5, "Dish6", 120);
    public static final Dish dish7 = new Dish(DISH1_ID + 6, "Dish7", 400);
    public static final Dish dish8 = new Dish(DISH1_ID + 7, "Dish8", 450);
    public static final Dish dish9 = new Dish(DISH1_ID + 8, "Dish9", 700);
    public static final Dish dish10 = new Dish(DISH1_ID + 9, "Dish10", 500);
    public static final Dish updateDish1 = new Dish(null, "UpdateDish1", 111);
    public static final Dish updateDish2 = new Dish(null, "UpdateDish2", 222);
    public static final Dish newDish1 = new Dish(null, "NewDish1", 444);
    public static final Dish newDish2 = new Dish(null, "NewDish2", 555);


    public static final List<Dish> dishesForMenu1 = List.of(dish1, dish2, dish3);
    public static final List<Dish> dishesForMenu2 = List.of(dish4, dish5);
    public static final List<Dish> dishesForMenu3 = List.of(dish6, dish7, dish8);
    public static final List<Dish> dishesForMenu4 = List.of(dish9, dish10);

    public static final Menu menu1 = new Menu(MENU1_ID, restaurant1, of(2023, 6, 1), dishesForMenu1);
    public static final Menu menu2 = new Menu(MENU1_ID + 1, restaurant2, of(2023, 6, 1), dishesForMenu2);
    public static final Menu menu3 = new Menu(MENU1_ID + 2, restaurant1, LocalDate.now(), dishesForMenu3);
    public static final Menu menu4 = new Menu(MENU1_ID + 3, restaurant2, LocalDate.now(), dishesForMenu4);

    public static Menu getNew() {
        return new Menu(
                null,
                restaurant1,
                LocalDate.of(2023, 6, 10),
                List.of(newDish1, newDish2));
    }

    public static Menu getUpdated() {
        return new Menu(MENU1_ID, restaurant1, LocalDate.of(2023, 6, 1), List.of(updateDish1, updateDish2));
    }
}