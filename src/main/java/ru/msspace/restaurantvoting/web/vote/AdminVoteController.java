package ru.msspace.restaurantvoting.web.vote;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.msspace.restaurantvoting.to.VoteTo;
import ru.msspace.restaurantvoting.util.VoteUtil;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AdminVoteController extends AbstractVoteController {
    static final String REST_URL = "api/admin/votes";

    @GetMapping("/by-date")
    public List<VoteTo> getAllByDate(
            @RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get all votes on date {}", date);
        return VoteUtil.createVoteTos(repository.getAllByDate(date));
    }

    @GetMapping("/today")
    public List<VoteTo> getAllToday() {
        LocalDate date = LocalDate.now();
        log.info("get all votes on date {}", date);
        return VoteUtil.createVoteTos(repository.getAllByDate(date));
    }
}