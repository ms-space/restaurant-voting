package ru.msspace.restaurantvoting.web.vote;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.msspace.restaurantvoting.to.VoteTo;
import ru.msspace.restaurantvoting.util.VoteUtil;
import ru.msspace.restaurantvoting.web.AuthUser;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.msspace.restaurantvoting.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = UserVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserVoteController extends AbstractVoteController {
    static final String REST_URL = "api/user/votes";

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteTo> create(@AuthenticationPrincipal AuthUser authUser,
                                         @RequestBody VoteTo voteTo) {
        log.info("create vote {} from user {}", voteTo, authUser);
        checkNew(voteTo);
        LocalDate date = LocalDate.now();
        VoteTo created = service.create(voteTo, authUser, date);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser,
                       @Valid @RequestBody VoteTo voteTo) {
        log.info("update vote {} from user {}", voteTo, authUser);
        LocalDateTime dateTime = LocalDateTime.now();
        VoteUtil.checkTime(dateTime.toLocalTime());
        service.update(voteTo, authUser, dateTime.toLocalDate());
    }

    @GetMapping
    public List<VoteTo> getAll(@AuthenticationPrincipal AuthUser authUser) {
        return super.getAllByUser(authUser);
    }
}