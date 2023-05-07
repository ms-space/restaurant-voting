package ru.msspace.restaurantvoting.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Restaurant extends NamedEntity {

    @NotBlank
    @Size(min = 2, max = 128)
    private String address;

    public Restaurant(Integer id, String name, String address) {
        super(id, name);
        this.address = address;
    }
}