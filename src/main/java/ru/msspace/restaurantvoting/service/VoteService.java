package ru.msspace.restaurantvoting.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.msspace.restaurantvoting.model.Menu;
import ru.msspace.restaurantvoting.model.Vote;
import ru.msspace.restaurantvoting.repository.VoteRepository;
import ru.msspace.restaurantvoting.to.VoteTo;
import ru.msspace.restaurantvoting.web.AuthUser;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository repository;
    private final MenuService menuService;

    @Transactional
    public VoteTo save(VoteTo voteTo, AuthUser authUser) {
        LocalDate date = LocalDate.now();
        Menu menu = menuService.get(voteTo.getRestaurantId(), date);
        Vote vote = new Vote(null, authUser.getUser(), menu, date);
        Vote save = repository.save(vote);
        return new VoteTo(save.getId(), save.getMenu().id(), save.getMenu().getRestaurant().id(), save.getDate());
    }
}