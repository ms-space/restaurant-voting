package ru.msspace.restaurantvoting.web.menu;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.msspace.restaurantvoting.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.msspace.restaurantvoting.util.MenuUtil.createTo;
import static ru.msspace.restaurantvoting.web.menu.MenuTestData.*;

public abstract class AbstractMenuControllerTest extends AbstractControllerTest {
    protected static String REST_URL;
    protected static String REST_URL_SLASH;

    @Test
    void getAllByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "menus/by-date")
                .param("date", "2023-06-01"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(createTo(menu1), createTo(menu2)));
    }

    @Test
    void getAllByDateNow() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "menus/by-date"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(createTo(menu3), createTo(menu4)));
    }
}