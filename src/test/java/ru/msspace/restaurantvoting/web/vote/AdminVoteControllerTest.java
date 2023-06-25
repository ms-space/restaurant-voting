package ru.msspace.restaurantvoting.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.msspace.restaurantvoting.AbstractControllerTest;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.msspace.restaurantvoting.util.VoteUtil.createTo;
import static ru.msspace.restaurantvoting.util.VoteUtil.createTos;
import static ru.msspace.restaurantvoting.web.user.UserTestData.ADMIN_MAIL;
import static ru.msspace.restaurantvoting.web.vote.VoteTestData.*;

class AdminVoteControllerTest extends AbstractControllerTest {
    private static final String REST_URL_SLASH = AdminVoteController.REST_URL + '/';

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "by-date")
                .param("date", "2023-06-01"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(createTo(vote1), createTo(vote2)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "today"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(createTos(List.of(vote3))));
    }
}