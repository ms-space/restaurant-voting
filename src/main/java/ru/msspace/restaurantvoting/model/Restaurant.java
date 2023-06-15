package ru.msspace.restaurantvoting.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "restaurant", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurant_unique_name_idx")})
public class Restaurant extends NamedEntity {

    @NotBlank
    @Size(min = 2, max = 128)
    private String address;

    public Restaurant(Integer id, String name, String address) {
        super(id, name);
        this.address = address;
    }
}