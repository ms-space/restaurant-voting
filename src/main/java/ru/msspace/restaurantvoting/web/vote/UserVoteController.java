package ru.msspace.restaurantvoting.web.vote;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.msspace.restaurantvoting.to.VoteInfoTo;
import ru.msspace.restaurantvoting.to.VoteTo;
import ru.msspace.restaurantvoting.util.DateTimeUtil;
import ru.msspace.restaurantvoting.util.VoteUtil;
import ru.msspace.restaurantvoting.web.AuthUser;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static ru.msspace.restaurantvoting.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = UserVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserVoteController extends AbstractVoteController {
    static final String REST_URL = "api/user/votes";

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteTo> create(@AuthenticationPrincipal AuthUser authUser,
                                             @RequestBody @Valid VoteTo voteTo) {
        log.info("create vote {} from user {}", voteTo, authUser);
        checkNew(voteTo);
        VoteTo created = service.create(voteTo, authUser, LocalDate.now());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser,
                       @RequestBody @Valid VoteTo voteTo) {
        log.info("update vote {} from user {}", voteTo, authUser);
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeUtil.checkTime(dateTime.toLocalTime());
        service.update(voteTo, authUser, dateTime.toLocalDate());
    }

    @GetMapping
    public VoteInfoTo getByUserAndDate(@AuthenticationPrincipal AuthUser authUser,
                                       @Nullable @RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        log.info("get vote for user {} on date {}", authUser, date);
        return VoteUtil.createVoteInfoTo(repository.getExistedOrBelonged(date, authUser.getUser()));
    }
}