package ru.msspace.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.msspace.restaurantvoting.error.VoteException;

import java.time.LocalDateTime;
import java.time.LocalTime;

@UtilityClass
public class DateTimeUtil {
    private static final LocalTime END_VOTE = LocalTime.of(11, 0);
    private static LocalDateTime votingDateTime = LocalDateTime.now();

    public static void checkTime(LocalTime current) {
        if (current.isAfter(END_VOTE)) {
            throw new VoteException("Too late to change the vote");
        }
    }

    public static LocalTime getEndVoteTime() {
        return END_VOTE;
    }

    public static LocalDateTime getVotingDateTime() {
        return votingDateTime;
    }

    public static void setVotingDateTime(LocalDateTime dateTime) {
        votingDateTime = dateTime;
    }
}