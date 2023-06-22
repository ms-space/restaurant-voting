package ru.msspace.restaurantvoting.web.menu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.msspace.restaurantvoting.AbstractControllerTest;
import ru.msspace.restaurantvoting.repository.MenuRepository;
import ru.msspace.restaurantvoting.to.MenuTo;
import ru.msspace.restaurantvoting.util.JsonUtil;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.msspace.restaurantvoting.util.MenuUtil.createTo;
import static ru.msspace.restaurantvoting.web.menu.MenuTestData.*;
import static ru.msspace.restaurantvoting.web.restaurant.RestaurantTestData.RESTAURANT1_ID;
import static ru.msspace.restaurantvoting.web.user.UserTestData.ADMIN_MAIL;

class AdminMenuControllerTest extends AbstractControllerTest {
    private static final String REST_URL_SLASH = AdminMenuController.REST_URL + '/';

    @Autowired
    private MenuRepository menuRepository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "menus/" + MENU1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(createTo(menu1)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllForToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "menus/today"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(createTo(menu3), createTo(menu4)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "/menus/by-date")
                .param("date", "2023-06-01"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(createTo(menu1), createTo(menu2)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "restaurants/" + RESTAURANT1_ID + "/menus"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(createTo(menu1), createTo(menu3)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        MenuTo newMenuTo = createTo(MenuTestData.getNew());
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL_SLASH + "restaurants/" + RESTAURANT1_ID + "/menus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenuTo)));

        MenuTo created = MENU_TO_MATCHER.readFromJson(action);
        int newId = created.id();
        newMenuTo.setId(newId);
        newMenuTo.getDishes().get(0).setId(created.getDishes().get(0).getId());
        newMenuTo.getDishes().get(1).setId(created.getDishes().get(1).getId());
        MENU_TO_MATCHER.assertMatch(created, newMenuTo);
        MENU_TO_MATCHER.assertMatch(createTo(menuRepository.getExisted(newId)), newMenuTo);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        MenuTo updated = createTo(MenuTestData.getUpdated());
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + "menus/" + MENU1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        MENU_TO_MATCHER_IGNORE_DISH_ID.assertMatch(createTo(menuRepository.getExisted(MENU1_ID)), updated);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + "menus/" + MENU1_ID))
                .andExpect(status().isNoContent());
        assertFalse(menuRepository.findById(MENU1_ID).isPresent());
    }
}