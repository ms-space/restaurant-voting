package ru.msspace.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.msspace.restaurantvoting.model.Vote;
import ru.msspace.restaurantvoting.to.VoteInfoTo;
import ru.msspace.restaurantvoting.to.VoteTo;

import java.util.List;

@UtilityClass
public class VoteUtil {

    public static VoteTo createVoteTo(Vote vote) {
        return new VoteTo(vote.getId(), vote.getMenu().getRestaurant().id());
    }

    public static VoteInfoTo createVoteInfoTo(Vote vote) {
        return new VoteInfoTo(vote.getId(), vote.getMenu().id(), vote.getMenu().getRestaurant().id(), vote.getDate());
    }

    public static List<VoteInfoTo> createVoteInfoTos(List<Vote> votes) {
        return votes.stream().map(VoteUtil::createVoteInfoTo).toList();
    }
}