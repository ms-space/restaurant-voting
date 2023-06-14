package ru.msspace.restaurantvoting.to;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.msspace.restaurantvoting.model.Dish;

import java.time.LocalDate;
import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
public class MenuTo extends BaseTo {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    int restaurantId;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    String restaurantAddress;

    @NotNull
    LocalDate date;

    @NotNull
    List<Dish> dishes;

    public MenuTo(Integer id, int restaurantId, String restaurantAddress, @NotNull LocalDate date, @NotNull List<Dish> dishes) {
        super(id);
        this.restaurantId = restaurantId;
        this.restaurantAddress = restaurantAddress;
        this.date = date;
        this.dishes = dishes;
    }
}