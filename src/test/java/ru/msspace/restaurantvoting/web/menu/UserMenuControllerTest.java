package ru.msspace.restaurantvoting.web.menu;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.msspace.restaurantvoting.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.msspace.restaurantvoting.util.MenuUtil.createTo;
import static ru.msspace.restaurantvoting.web.menu.MenuTestData.*;
import static ru.msspace.restaurantvoting.web.user.UserTestData.USER_MAIL;

class UserMenuControllerTest extends AbstractControllerTest {
    private static final String REST_URL_SLASH = UserMenuController.REST_URL + '/';

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllForToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "today"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(createTo(menu3), createTo(menu4)));
    }
}