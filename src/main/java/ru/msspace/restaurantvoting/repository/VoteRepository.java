package ru.msspace.restaurantvoting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.msspace.restaurantvoting.error.DataConflictException;
import ru.msspace.restaurantvoting.error.NotFoundException;
import ru.msspace.restaurantvoting.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT v FROM Vote v WHERE  v.date=:date AND v.user.id=:userId")
    Optional<Vote> getByUserAndDate(LocalDate date, int userId);

    @Query("SELECT v FROM Vote v WHERE  v.date=:date")
    List<Vote> getAllByDate(LocalDate date);

    @Query("SELECT v FROM Vote v WHERE  v.user.id=:userId")
    List<Vote> getAllByUser(int userId);

    @Query("SELECT v FROM Vote v WHERE v.restaurant.id=:restaurantId AND v.date=:date")
    Optional<Vote> getByRestaurantAndDate(int restaurantId, LocalDate date);

    default Vote getExistedOrBelonged(LocalDate date, int userId) {
        return getByUserAndDate(date, userId).orElseThrow(
                () -> new DataConflictException("Vote with date=" + date + " is not exist or doesn't belong user id=" + userId));
    }

    default Vote findExistedOrBelonged(LocalDate date, int userId) {
        return getByUserAndDate(date, userId).orElseThrow(
                () -> new NotFoundException("Vote with date=" + date + " and with user " + userId + " not found"));
    }
}