package ru.msspace.restaurantvoting.web.vote;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.msspace.restaurantvoting.repository.UserRepository;
import ru.msspace.restaurantvoting.to.VoteTo;
import ru.msspace.restaurantvoting.web.AuthUser;

import java.util.List;

@RestController
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AdminVoteController extends AbstractVoteController {
    static final String REST_URL = "api/admin/users/{id}/votes";

    protected UserRepository userRepository;

    @GetMapping
    public List<VoteTo> getAll(@PathVariable int id) {
        AuthUser authUser = new AuthUser(userRepository.getExisted(id));
        return super.getAllByUser(authUser);
    }
}