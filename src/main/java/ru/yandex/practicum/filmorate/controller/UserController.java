package ru.yandex.practicum.filmorate.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;

import java.util.Set;

@RestController
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    //Создаем юзера
    @PostMapping(value = "/users")
    public User create(@Valid @RequestBody User user) {
        log.info("POST request create user");
        return userService.createUser(user);
    }

    //Обновляем юзера
    @PutMapping(value = "/users")
    public User update(@Valid @RequestBody User user){
        log.info("PUT request update user");
        return userService.updateUser(user);
    }

    //Получаем список всех юзеров
    @GetMapping("/users")
    public Set<User> getAllUsers() {
        log.info("GET request for all users");
        return userService.getAllUsers();
    }

    //Добавляем друга юзеру
    @PutMapping(value = "/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("PUT request add friend to the user");
        userService.addFriend(id, friendId);
    }

    //Удаляем друга у юзера
    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("DELETE request delete friend to the user");
        userService.deleteFriend(id, friendId);
    }

    //Получаем юзера по ID
    @GetMapping("/users/{userId}")
    public User getUserForID(@PathVariable int userId) {
        log.info("GET request get user for userID");
        return userService.getUserForID(userId);
    }

    //Получаем друзей юзера по ID юзера
    @GetMapping("/users/{id}/friends")
    public Set<User> getFriendsListForUserID(@PathVariable int id) {
        log.info("GET request friends for userID");
        return userService.getFriendsListForUserID(id);
    }

    //Получаем общих друзей по ID юзеров
    @GetMapping("/users/{id}/friends/common/{otherId}")
    public Set<User> getCommonsFriendsForUsersID(@PathVariable int id, @PathVariable int otherId) {
        log.info("GET request common friends");
        return userService.getCommonsFriendsForUsersID(id,otherId);
    }

}