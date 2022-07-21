package ru.yandex.practicum.filmorate.dao;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class InMemoryUserStorage implements UserStorage {

    private Map<Integer, User> users = new HashMap<>();
    private Integer userID = 0;

    //Создаем юзера
    @Override
    public User createUser(User user) {
        user.setId(++userID);
        users.put(user.getId(), user);
        return user;
    }
    //Обновляем юзера
    @Override
    public User updateUser(User user) {
        users.put(user.getId(),user);
        return user;
    }
    //Получаем список всех юзеров
    @Override
    public Set<User> getAllUsers() {
        return new HashSet<User>(users.values());
    }

    //Добавляем друга юзеру
    @Override
    public void addFriend(User user, User friend) {
        user.getFriends().add(friend.getId());
        friend.getFriends().add(user.getId());
    }

    //Удаляем друга у юзера
    @Override
    public void deleteFriend(User user, User friend) {
        user.getFriends().remove(friend.getId());
        friend.getFriends().remove(user.getId());
    }

    //Получаем юзера по ID
    @Override
    public User getUserForID(int userID) {
        return users.get(userID);
    }

    //Получаем друзей юзера по ID юзера
    @Override
    public Set<Integer> getFriendsIdForUserID(User user) {
        return new HashSet<>(users.get(user.getId()).getFriends());
    }

}
