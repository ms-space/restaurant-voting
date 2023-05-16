package ru.msspace.restaurantvoting.web.vote;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.msspace.restaurantvoting.service.VoteService;
import ru.msspace.restaurantvoting.to.VoteTo;
import ru.msspace.restaurantvoting.web.AuthUser;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractVoteController {
    protected final Logger log = getLogger(getClass());

    @Autowired
    protected VoteService service;

    public List<VoteTo> getAllByUser(AuthUser authUser) {
        log.info("get all votes for user {}", authUser);
        return service.getAllByUser(authUser);

    }
}