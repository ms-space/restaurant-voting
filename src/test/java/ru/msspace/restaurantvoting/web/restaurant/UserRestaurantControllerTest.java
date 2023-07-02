package ru.msspace.restaurantvoting.web.restaurant;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.security.test.context.support.WithUserDetails;

import static ru.msspace.restaurantvoting.web.user.UserTestData.USER_MAIL;

@WithUserDetails(value = USER_MAIL)
class UserRestaurantControllerTest extends AbstractRestaurantControllerTest {

    @BeforeAll
    public static void init() {
        REST_URL = UserRestaurantController.REST_URL;
        REST_URL_SLASH = UserRestaurantController.REST_URL + '/';
    }
}