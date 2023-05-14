package ru.msspace.restaurantvoting.web.vote;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.msspace.restaurantvoting.service.VoteService;
import ru.msspace.restaurantvoting.to.VoteTo;
import ru.msspace.restaurantvoting.web.AuthUser;

import java.net.URI;

import static ru.msspace.restaurantvoting.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = UserVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class UserVoteController {
    static final String REST_URL = "api/user/votes";

    private final VoteService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteTo> create(@AuthenticationPrincipal AuthUser authUser,
                                         @RequestBody VoteTo voteTo) {
        log.info("create vote {} from user with id={}", voteTo, authUser.id());
        checkNew(voteTo);
        VoteTo created = service.save(voteTo, authUser);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}