package ru.msspace.restaurantvoting.to;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
public class VoteTo extends BaseTo {

    int restaurantId;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    LocalDate date;

    public VoteTo(Integer id, int restaurantId, LocalDate date) {
        super(id);
        this.restaurantId = restaurantId;
        this.date = date;
    }
}