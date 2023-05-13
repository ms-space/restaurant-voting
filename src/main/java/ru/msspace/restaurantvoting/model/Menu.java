package ru.msspace.restaurantvoting.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "menu", uniqueConstraints = {@UniqueConstraint(columnNames = {"menu_date", "restaurant_id",}, name = "menu_unique_menu_date_restaurant_idx")})
public class Menu extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    @Column(name = "menu_date", nullable = false)
    @NotNull
    private LocalDate date;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "name", nullable = false)
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    @CollectionTable(name = "dish", joinColumns = @JoinColumn(name = "menu_id"))
    @NotNull
    private List<String> dishes;

    @Column(name = "price", nullable = false)
    @Range(min = 0, max = 1000000)
    private int price;

    public Menu(Integer id, Restaurant restaurant, LocalDate date, List<String> dishes, Integer price) {
        super(id);
        this.restaurant = restaurant;
        this.date = date;
        this.dishes = dishes;
        this.price = price;
    }
}