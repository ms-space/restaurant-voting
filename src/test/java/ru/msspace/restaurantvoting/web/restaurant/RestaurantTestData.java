package ru.msspace.restaurantvoting.web.restaurant;

import ru.msspace.restaurantvoting.model.Restaurant;
import ru.msspace.restaurantvoting.web.MatcherFactory;

import java.util.List;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class);

    public static final int RESTAURANT1_ID = 1;
    public static final int RESTAURANT2_ID = RESTAURANT1_ID + 1;
    public static final int RESTAURANT3_ID = RESTAURANT1_ID + 2;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, "Restaurant1", "Address1");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT2_ID, "Restaurant2", "Address2");
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT3_ID, "Restaurant3", "Address3");

    public static final List<Restaurant> restaurants = List.of(restaurant1, restaurant2, restaurant3);


    public static Restaurant getNew() {
        return new Restaurant(null, "Created restaurant", "Created address");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT1_ID, "Updated restaurant", "Updated address");
    }
}
