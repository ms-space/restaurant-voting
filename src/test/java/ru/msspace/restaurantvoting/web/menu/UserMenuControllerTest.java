package ru.msspace.restaurantvoting.web.menu;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.security.test.context.support.WithUserDetails;

import static ru.msspace.restaurantvoting.web.user.UserTestData.USER_MAIL;

@WithUserDetails(value = USER_MAIL)
class UserMenuControllerTest extends AbstractMenuControllerTest {

    @BeforeAll
    public static void init() {
        REST_URL = UserMenuController.REST_URL;
        REST_URL_SLASH = UserMenuController.REST_URL + '/';
    }
}