package ru.msspace.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.msspace.restaurantvoting.model.Vote;
import ru.msspace.restaurantvoting.to.VoteTo;

import java.util.List;

@UtilityClass
public class VoteUtil {

    public static VoteTo createVoteTo(Vote vote) {
        return new VoteTo(vote.id(), vote.getMenu().getRestaurant().id(), vote.getMenu().id(), vote.getDate());
    }

    public static List<VoteTo> createVoteTos(List<Vote> votes) {
        return votes.stream().map(VoteUtil::createVoteTo).toList();
    }
}