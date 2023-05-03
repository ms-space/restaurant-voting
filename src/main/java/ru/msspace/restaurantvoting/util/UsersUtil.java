package ru.msspace.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.msspace.restaurantvoting.model.User;
import ru.msspace.restaurantvoting.to.UserTo;


@UtilityClass
public class UsersUtil {

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        return user;
    }
}