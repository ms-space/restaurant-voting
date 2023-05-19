package ru.msspace.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.msspace.restaurantvoting.error.VoteException;

import java.time.LocalTime;

@UtilityClass
public class DateTimeUtil {
    private static final LocalTime END_VOTE = LocalTime.of(11, 0);

    public static void checkTime(LocalTime current) {
        if (current.isAfter(END_VOTE)) {
            throw new VoteException("Too late to change the vote");
        }
    }
}