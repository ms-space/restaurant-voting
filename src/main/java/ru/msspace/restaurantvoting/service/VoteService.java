package ru.msspace.restaurantvoting.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.msspace.restaurantvoting.model.Menu;
import ru.msspace.restaurantvoting.model.Vote;
import ru.msspace.restaurantvoting.repository.MenuRepository;
import ru.msspace.restaurantvoting.repository.UserRepository;
import ru.msspace.restaurantvoting.repository.VoteRepository;
import ru.msspace.restaurantvoting.to.VoteTo;
import ru.msspace.restaurantvoting.util.DateTimeUtil;
import ru.msspace.restaurantvoting.util.VoteUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static ru.msspace.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;

@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository repository;
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;

    @Transactional
    public VoteTo create(VoteTo voteTo, int userId, LocalDate date) {
        Menu menuExistedOrBelonged = menuRepository.getExistedOrBelonged(voteTo.getRestaurantId(), date);
        Vote create = new Vote(null, menuExistedOrBelonged.getRestaurant(), date);
        create.setUser(userRepository.getExisted(userId));
        return VoteUtil.createTo(repository.save(create));
    }

    @Transactional
    public void update(VoteTo voteTo, int userId, LocalDateTime dateTime) {
        DateTimeUtil.checkTime(dateTime.toLocalTime());
        LocalDate date = dateTime.toLocalDate();
        Vote voteExistedOrBelonged = repository.getExistedOrBelonged(date, userId);
        assureIdConsistent(voteTo, voteExistedOrBelonged.id());
        Menu menuExistedOrBelonged = menuRepository.getExistedOrBelonged(voteTo.getRestaurantId(), date);
        Vote update = new Vote(voteTo.getId(), menuExistedOrBelonged.getRestaurant(), date);
        update.setUser(userRepository.getExisted(userId));
        repository.save(update);
    }

    public Optional<VoteTo> getByDate(LocalDate date, int userId) {
        Optional<Vote> vote = repository.getByUserAndDate(date, userId);
        return vote.map(VoteUtil::createTo);
    }
}