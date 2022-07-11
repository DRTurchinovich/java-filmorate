package ru.yandex.practicum.filmorate.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@RestController
@Slf4j
public class UserController {
    private Map<Integer, User> users = new HashMap<>();
    private Integer userID = 0;

    protected void validationUser(User user) {
        if (user.getEmail() == null  || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.info("error {} mail", user.getEmail());
            throw new ValidationException("user: Incorrect email");
        }
        if (user.getLogin().isBlank() && user.getLogin().contains(" ")) {
            log.info("error {} login", user.getLogin());
            throw new ValidationException("user: Incorrect login");
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        if(user.getBirthday().isAfter(LocalDate.now())) {
            log.info("error {} birthday", user.getLogin());
            throw new ValidationException("user: Incorrect date");
        }
    }

    @PostMapping(value = "/users")
    public User createUser(@Valid @RequestBody User user) {
        validationUser(user);
        user.setId(++userID);
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping(value = "/users")
    public User updateUser(@Valid @RequestBody User user) {
        if (users.containsKey(user.getId())) {
            validationUser(user);
            users.put(user.getId(), user);
        } else {
            log.error("error {} id", user.getId());
            throw new ValidationException("user: id not found");
        }
        return users.get(user.getId());
    }

    @GetMapping("/users")
    public HashSet<User> getUsers() {
        return new HashSet<>(users.values());
    }

    public void removeUsers() {
        users.clear();
    }
}