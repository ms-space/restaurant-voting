package ru.msspace.restaurantvoting.web.vote;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
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

    @PostMapping(value = "/today", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteTo> create(@AuthenticationPrincipal AuthUser authUser,
                                         @RequestBody @Valid VoteTo voteTo) {
        log.info("create vote {} from user {}", voteTo, authUser);
        checkNew(voteTo);
        VoteTo created = service.create(voteTo, authUser, LocalDate.now());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/today").build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/today", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser,
                       @RequestBody @Valid VoteTo voteTo) {
        log.info("update vote {} from user {}", voteTo, authUser);
        LocalDateTime dateTime = LocalDateTime.now();
        service.update(voteTo, authUser, dateTime);
    }

    @GetMapping("/today")
    public VoteTo getForToday(@AuthenticationPrincipal AuthUser authUser) {
        LocalDate date = LocalDate.now();
        log.info("get vote for today {} for user {}", date, authUser);
        return VoteUtil.createVoteTo(repository.findExistedOrBelonged(date, authUser.getUser()));
    }

    @GetMapping("/by-date")
    public VoteTo getByDate(@AuthenticationPrincipal AuthUser authUser,
                            @RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get vote for user {} on date {}", authUser, date);
        return VoteUtil.createVoteTo(repository.findExistedOrBelonged(date, authUser.getUser()));

    }

    @GetMapping
    public List<VoteTo> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get all votes for user {}", authUser);
        return VoteUtil.createVoteTos(repository.getAllByUser(authUser.getUser()));
    }
}