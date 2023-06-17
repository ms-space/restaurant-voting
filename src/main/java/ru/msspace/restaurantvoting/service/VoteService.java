package ru.msspace.restaurantvoting.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.msspace.restaurantvoting.model.Menu;
import ru.msspace.restaurantvoting.model.Vote;
import ru.msspace.restaurantvoting.repository.MenuRepository;
import ru.msspace.restaurantvoting.repository.VoteRepository;
import ru.msspace.restaurantvoting.to.VoteTo;
import ru.msspace.restaurantvoting.util.DateTimeUtil;
import ru.msspace.restaurantvoting.util.VoteUtil;
import ru.msspace.restaurantvoting.web.AuthUser;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository repository;
    private final MenuRepository menuRepository;

    @Transactional
    public VoteTo create(VoteTo voteTo, AuthUser authUser, LocalDate date) {
        Menu menuExisted = menuRepository.getExistedOrBelonged(voteTo.getRestaurantId(), date);
        Vote create = new Vote(null, authUser.getUser(), menuExisted.getRestaurant(), date);
        return VoteUtil.createVoteTo(repository.save(create));
    }

    @Transactional
    public void update(VoteTo voteTo, AuthUser authUser, LocalDateTime dateTime) {
        DateTimeUtil.checkTime(dateTime.toLocalTime());
        LocalDate date = dateTime.toLocalDate();
        Vote voteExisted = repository.getExistedOrBelonged(date, authUser.getUser());
        Menu menuExisted = menuRepository.getExistedOrBelonged(voteTo.getRestaurantId(), date);
        Vote update = new Vote(voteExisted.getId(), authUser.getUser(), menuExisted.getRestaurant(), date);
        repository.save(update);
    }
}