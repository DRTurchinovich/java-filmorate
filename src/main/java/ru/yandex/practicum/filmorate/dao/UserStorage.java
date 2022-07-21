package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;


import java.util.Set;

public interface UserStorage {


    User createUser(User user);
    User updateUser(User user);
    Set<User> getAllUsers();
    User getUserForID(int userID);
    void addFriend(User user, User friend);
    void deleteFriend(User user, User friend);
    Set<Integer> getFriendsIdForUserID(User user);

}
