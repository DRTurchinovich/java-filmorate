package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    InMemoryUserStorage userStorage;

    //Создаем юзера
    public User createUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return userStorage.createUser(user);
    }

    //Обновляем юзера
    public User updateUser(User user) {
        final User updatedUser = userStorage.getUserForID(user.getId());
        if (updatedUser == null) {
            throw new NotFoundException("User with ID = " + user.getId() + " not found.");
        } else {
            return userStorage.updateUser(user);
        }
    }

    //Получаем список всех юзеров
    public Set<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    //Добавляем друга юзеру
    public void addFriend(int userID, int friendID) {
        User user = userStorage.getUserForID(userID);
        User friend = userStorage.getUserForID(friendID);
        if (user == null) {
            throw new NotFoundException("User with ID = " + userID + " not found.");
        }
        if (friend == null) {
            throw new NotFoundException("Friend with ID = " + friendID + " not found.");
        }
        userStorage.addFriend(user, friend);
    }

    //Удаляем друга у юзера
    public void deleteFriend(int userID, int friendID) {
        User user = userStorage.getUserForID(userID);
        User friend = userStorage.getUserForID(friendID);
        if (user == null) {
            throw new NotFoundException("User with ID = " + userID + " not found.");
        }
        if (friend == null) {
            throw new NotFoundException("Friend with ID = " + friendID + " not found.");
        }
        if (!userStorage.getAllUsers().contains(user) || !userStorage.getAllUsers().contains(friend)) {
            throw new NotFoundException("User or friend with ID not found.");
        }
        userStorage.deleteFriend(user, friend);
    }

    //Получаем юзера по ID
    public User getUserForID(int userID) {
        final User user = userStorage.getUserForID(userID);
        if (user == null) {
            throw new NotFoundException("User with ID = " + userID + " not found.");
        }
        return userStorage.getUserForID(userID);
    }
    //Получаем список друзей по ID юзера
    public Set<User> getFriendsListForUserID(int userID) {
        final User user = userStorage.getUserForID(userID);
        if (user == null) {
            throw new NotFoundException("User with ID = " + userID + " not found.");
        }
       return getFriendsIdForUserID(userID).stream()
                .map(userStorage::getUserForID)
                .collect(Collectors.toSet());
    }

    //Получаем ID друзей по ID юзера
    public Set<Integer> getFriendsIdForUserID(int userID) {
        final User user = userStorage.getUserForID(userID);
        if (user == null) {
            throw new NotFoundException("User with ID = " + userID + " not found.");
        }
        return userStorage.getFriendsIdForUserID(user);
    }

    //Получаем общих друзей по ID юзеров
    public Set<User> getCommonsFriendsForUsersID(int userID, int otherID) {
        final User user = userStorage.getUserForID(userID);
        final User otherUser = userStorage.getUserForID(otherID);
        if (user == null) {
            throw new NotFoundException("User with ID = " + userID + " not found.");
        }
        if (otherUser == null) {
            throw new NotFoundException("User with ID = " + otherID + " not found.");
        }
        return getFriendsIdForUserID(userID).stream()
                .filter(getFriendsIdForUserID(otherID)::contains)
                .map(userStorage::getUserForID)
                .collect(Collectors.toSet());
    }
}
