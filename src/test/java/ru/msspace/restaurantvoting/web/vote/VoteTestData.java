package ru.msspace.restaurantvoting.web.vote;

import ru.msspace.restaurantvoting.model.Vote;
import ru.msspace.restaurantvoting.to.VoteTo;
import ru.msspace.restaurantvoting.web.MatcherFactory;

import java.time.LocalDate;

import static ru.msspace.restaurantvoting.web.restaurant.RestaurantTestData.restaurant1;
import static ru.msspace.restaurantvoting.web.restaurant.RestaurantTestData.restaurant2;

public class VoteTestData {
    public static final MatcherFactory.Matcher<VoteTo> VOTE_TO_MATCHER = MatcherFactory.usingEqualsComparator(VoteTo.class);

    public static final int VOTE1_ID = 1;

    public static final Vote vote1 = new Vote(VOTE1_ID, restaurant1, LocalDate.of(2023, 6, 1));
    public static final Vote vote2 = new Vote(VOTE1_ID + 1, restaurant2, LocalDate.of(2023, 6, 1));
    public static final Vote vote3 = new Vote(VOTE1_ID + 2, restaurant1, LocalDate.now());

    public static Vote getNew() {
        return new Vote(null, restaurant1, LocalDate.now());
    }

    public static Vote getUpdated() {
        return new Vote(3, restaurant2, LocalDate.now());
    }
}