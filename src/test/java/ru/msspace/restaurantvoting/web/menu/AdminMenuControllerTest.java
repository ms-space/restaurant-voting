package ru.msspace.restaurantvoting.web.menu;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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

@WithUserDetails(value = ADMIN_MAIL)
class AdminMenuControllerTest extends AbstractMenuControllerTest {

    @Autowired
    private MenuRepository menuRepository;

    @BeforeAll
    public static void init() {
        REST_URL = AdminMenuController.REST_URL;
        REST_URL_SLASH = AdminMenuController.REST_URL + '/';
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "menus/" + MENU1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(createTo(menu1)));
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "restaurants/" + RESTAURANT1_ID + "/menus"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(createTo(menu1), createTo(menu3)));
    }

    @Test
    void create() throws Exception {
        MenuTo newMenuTo = createTo(getNew());
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
    void update() throws Exception {
        MenuTo updated = createTo(MenuTestData.getUpdated());
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + "menus/" + MENU1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        MENU_TO_MATCHER_IGNORE_DISH_ID.assertMatch(createTo(menuRepository.getExisted(MENU1_ID)), updated);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + "menus/" + MENU4_ID))
                .andExpect(status().isNoContent());
        assertFalse(menuRepository.findById(MENU4_ID).isPresent());
    }
}