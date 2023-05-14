package ru.msspace.restaurantvoting.to;

import jakarta.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VoteTo extends BaseTo {
    int menuId;

    @Positive
    int restaurantId;

    LocalDate date;

    public VoteTo(Integer id, int menuId, int restaurantId, LocalDate date) {
        super(id);
        this.menuId = menuId;
        this.restaurantId = restaurantId;
        this.date = date;
    }
}