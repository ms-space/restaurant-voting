package ru.msspace.restaurantvoting.to;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.hibernate.validator.constraints.Range;

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
    List<String> dishes;

    @Range(min = 0, max = 1000000)
    int price;

    public MenuTo(Integer id, int restaurantId, String restaurantAddress, @NotNull LocalDate date, @NotNull List<String> dishes, int price) {
        super(id);
        this.restaurantId = restaurantId;
        this.restaurantAddress = restaurantAddress;
        this.date = date;
        this.dishes = dishes;
        this.price = price;
    }
}