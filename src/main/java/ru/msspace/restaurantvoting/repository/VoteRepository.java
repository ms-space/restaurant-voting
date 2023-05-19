package ru.msspace.restaurantvoting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.msspace.restaurantvoting.error.DataConflictException;
import ru.msspace.restaurantvoting.model.User;
import ru.msspace.restaurantvoting.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT v FROM Vote v WHERE  v.date=:date AND v.user=:user")
    Optional<Vote> getByUserAndDate(LocalDate date, User user);

    @Query("SELECT v FROM Vote v WHERE  v.date=:date")
    List<Vote> getAllByDate(LocalDate date);

    default Vote getExistedOrBelonged(LocalDate date, User user) {
        return getByUserAndDate(date, user).orElseThrow(
                () -> new DataConflictException("Vote with date=" + date + " is not exist or doesn't belong user id=" + user.id()));
    }
}