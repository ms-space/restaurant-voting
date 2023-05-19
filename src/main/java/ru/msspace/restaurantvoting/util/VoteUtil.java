package ru.msspace.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.msspace.restaurantvoting.model.Vote;
import ru.msspace.restaurantvoting.to.VoteTo;

import java.util.List;

@UtilityClass
public class VoteUtil {

    public static VoteTo createTo(Vote vote) {
        return new VoteTo(vote.getId(), vote.getMenu().id(), vote.getMenu().getRestaurant().id(), vote.getDate());
    }

    public static List<VoteTo> createTos(List<Vote> votes) {
        return votes.stream().map(VoteUtil::createTo).toList();
    }
}