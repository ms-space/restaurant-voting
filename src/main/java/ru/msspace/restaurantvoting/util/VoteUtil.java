package ru.msspace.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.msspace.restaurantvoting.error.VoteException;
import ru.msspace.restaurantvoting.model.Vote;
import ru.msspace.restaurantvoting.to.VoteTo;

import java.time.LocalTime;
import java.util.List;

@UtilityClass
public class VoteUtil {
    private static final LocalTime END_VOTE = LocalTime.of(11, 0);

    public static void checkTime(LocalTime current) {
        if (current.isAfter(END_VOTE)) {
            throw new VoteException("Too late to change the vote");
        }
    }

    public static VoteTo createTo(Vote vote) {
        return new VoteTo(vote.getId(), vote.getMenu().id(), vote.getMenu().getRestaurant().id(), vote.getDate());
    }

    public static List<VoteTo> createTos(List<Vote> votes) {
        return votes.stream().map(VoteUtil::createTo).toList();
    }
}