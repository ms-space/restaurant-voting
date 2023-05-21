package ru.msspace.restaurantvoting.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.msspace.restaurantvoting.model.Menu;
import ru.msspace.restaurantvoting.model.Vote;
import ru.msspace.restaurantvoting.repository.VoteRepository;
import ru.msspace.restaurantvoting.to.VoteTo;
import ru.msspace.restaurantvoting.util.VoteUtil;
import ru.msspace.restaurantvoting.web.AuthUser;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository repository;
    private final MenuService menuService;

    @Transactional
    public VoteTo create(VoteTo voteTo, AuthUser authUser, LocalDate date) {
        Menu menuExisted = menuService.get(voteTo.getRestaurantId(), date);
        Vote create = new Vote(null, authUser.getUser(), menuExisted, date);
        return VoteUtil.createVoteTo(repository.save(create));
    }

    @Transactional
    public void update(VoteTo voteTo, AuthUser authUser, LocalDate date) {
        Vote voteExisted = repository.getExistedOrBelonged(date, authUser.getUser());
        Menu menuExisted = menuService.get(voteTo.getRestaurantId(), date);
        Vote update = new Vote(voteExisted.getId(), authUser.getUser(), menuExisted, date);
        repository.save(update);
    }
}