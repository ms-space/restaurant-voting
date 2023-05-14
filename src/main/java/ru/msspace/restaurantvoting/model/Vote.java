package ru.msspace.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "vote", uniqueConstraints = {@UniqueConstraint(columnNames = {"vote_date", "user_id"}, name = "menu_unique_vote_date_user_id_idx")})
public class Vote extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    @NotNull
    private User user;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(name = "vote_date", nullable = false)
    private LocalDate date;

    public Vote(Integer id, User user, Menu menu, LocalDate date) {
        super(id);
        this.user = user;
        this.menu = menu;
        this.date = date;
    }
}