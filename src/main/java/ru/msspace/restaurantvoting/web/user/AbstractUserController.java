package ru.msspace.restaurantvoting.web.user;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.msspace.restaurantvoting.model.User;
import ru.msspace.restaurantvoting.repository.UserRepository;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractUserController {
    protected final Logger log = getLogger(getClass());

    @Autowired
    protected UserRepository repository;

    public User get(int id) {
        log.info("get {}", id);
        return repository.getExisted(id);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        repository.deleteExisted(id);
    }
}