package ru.msspace.restaurantvoting.web.restaurant;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.msspace.restaurantvoting.model.Restaurant;
import ru.msspace.restaurantvoting.repository.RestaurantRepository;
import ru.msspace.restaurantvoting.util.JsonUtil;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.msspace.restaurantvoting.web.restaurant.RestaurantTestData.*;
import static ru.msspace.restaurantvoting.web.user.UserTestData.ADMIN_MAIL;

@WithUserDetails(value = ADMIN_MAIL)
class AdminRestaurantControllerTest extends AbstractRestaurantControllerTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @BeforeAll
    public static void init() {
        REST_URL = AdminRestaurantController.REST_URL;
        REST_URL_SLASH = AdminRestaurantController.REST_URL + '/';
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + RESTAURANT3_ID))
                .andExpect(status().isNoContent());
        assertFalse(restaurantRepository.findById(RESTAURANT3_ID).isPresent());
    }

    @Test
    void update() throws Exception {
        Restaurant updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + RESTAURANT1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        RESTAURANT_MATCHER.assertMatch(restaurantRepository.getExisted(RESTAURANT1_ID), updated);
    }

    @Test
    void create() throws Exception {
        Restaurant newRestaurant = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)));

        Restaurant created = RESTAURANT_MATCHER.readFromJson(action);
        int newId = created.id();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(restaurantRepository.getExisted(newId), newRestaurant);
    }
}