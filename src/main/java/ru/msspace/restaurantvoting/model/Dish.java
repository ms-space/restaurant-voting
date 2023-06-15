package ru.msspace.restaurantvoting.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "dish", uniqueConstraints = {@UniqueConstraint(columnNames = {"menu_id", "name"}, name = "dish_unique_menu_name_idx")})
public class Dish extends NamedEntity {

    @Column(name = "price", nullable = false)
    @Positive
    private int price;

    public Dish(Integer id, String name, int price) {
        super(id, name);
        this.price = price;
    }
}