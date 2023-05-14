package ru.msspace.restaurantvoting.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.msspace.restaurantvoting.model.Vote;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

}