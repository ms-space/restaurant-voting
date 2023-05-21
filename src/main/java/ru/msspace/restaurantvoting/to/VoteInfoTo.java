package ru.msspace.restaurantvoting.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VoteInfoTo extends VoteTo {
    int menuId;
    LocalDate date;

    public VoteInfoTo(Integer id, int menuId, int restaurantId, LocalDate date) {
        super(id, restaurantId);
        this.menuId = menuId;
        this.date = date;
    }
}