package ru.msspace.restaurantvoting.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.msspace.restaurantvoting.AbstractControllerTest;
import ru.msspace.restaurantvoting.repository.VoteRepository;
import ru.msspace.restaurantvoting.to.VoteTo;
import ru.msspace.restaurantvoting.util.DateTimeUtil;
import ru.msspace.restaurantvoting.util.JsonUtil;
import ru.msspace.restaurantvoting.util.VoteUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.msspace.restaurantvoting.util.VoteUtil.createTo;
import static ru.msspace.restaurantvoting.util.VoteUtil.createTos;
import static ru.msspace.restaurantvoting.web.user.UserTestData.USER2_MAIL;
import static ru.msspace.restaurantvoting.web.user.UserTestData.USER_MAIL;
import static ru.msspace.restaurantvoting.web.vote.UserVoteController.REST_URL;
import static ru.msspace.restaurantvoting.web.vote.VoteTestData.*;

class UserVoteControllerTest extends AbstractControllerTest {
    private static final String REST_URL_SLASH = REST_URL + '/';

    @Autowired
    private VoteRepository repository;

    @Test
    @WithUserDetails(value = USER2_MAIL)
    void create() throws Exception {
        VoteTo newVoteTo = VoteUtil.createTo(getNew());
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL_SLASH + "today")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVoteTo)));

        VoteTo created = VOTE_TO_MATCHER.readFromJson(action);
        int newId = created.id();
        newVoteTo.setId(newId);
        VOTE_TO_MATCHER.assertMatch(created, newVoteTo);
        VOTE_TO_MATCHER.assertMatch(VoteUtil.createTo(repository.getExisted(newId)), newVoteTo);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateBefore() throws Exception {
        LocalTime votingTimeBefore = DateTimeUtil.getEndVoteTime().minusMinutes(5);
        DateTimeUtil.setVotingDateTime(LocalDateTime.of(LocalDate.now(), votingTimeBefore));

        VoteTo updated = VoteUtil.createTo(VoteTestData.getUpdated());
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + "today")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        VOTE_TO_MATCHER.assertMatch(createTo(repository.getExisted(VOTE1_ID + 2)), updated);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateAfter() throws Exception {
        LocalTime votingTimeAfter = DateTimeUtil.getEndVoteTime().plusMinutes(5);
        DateTimeUtil.setVotingDateTime(LocalDateTime.of(LocalDate.now(), votingTimeAfter));

        VoteTo updated = VoteUtil.createTo(VoteTestData.getUpdated());
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + "today")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getForToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "today"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(createTo(vote3)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "by-date")
                .param("date", "2023-06-01"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(createTo(vote1)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(createTos(List.of(vote1, vote3))));
    }
}