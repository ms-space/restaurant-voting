package ru.msspace.restaurantvoting.web.vote;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.msspace.restaurantvoting.repository.VoteRepository;
import ru.msspace.restaurantvoting.service.VoteService;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractVoteController {
    protected final Logger log = getLogger(getClass());

    @Autowired
    protected VoteService service;

    @Autowired
    protected VoteRepository repository;

}