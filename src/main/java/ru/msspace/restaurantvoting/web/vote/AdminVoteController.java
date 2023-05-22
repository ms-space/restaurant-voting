package ru.msspace.restaurantvoting.web.vote;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.msspace.restaurantvoting.to.VoteTo;
import ru.msspace.restaurantvoting.util.VoteUtil;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AdminVoteController extends AbstractVoteController {
    static final String REST_URL = "api/admin/votes";

    @GetMapping("/{id}")
    public VoteTo get(@PathVariable int id) {
        log.info("get vote id={}", id);
        return VoteUtil.createVoteTo(repository.getExisted(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete vote id={}", id);
        repository.deleteExisted(id);
    }

    @GetMapping
    public List<VoteTo> getAllByDate(
            @Nullable @RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        log.info("get all votes on date {}", date);
        return VoteUtil.createVoteTos(repository.getAllByDate(date));
    }
}