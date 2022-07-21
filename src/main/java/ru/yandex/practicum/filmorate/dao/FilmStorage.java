package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Set;

public interface FilmStorage {

    Film createFilm(Film film);
    Film updateFilm(Film film);
    Set<Film> getAllFilms();
    Film getFilmForID(int filmID);
    Integer addLike(Film film, User user);
    Integer deleteLike(Film film, User user);
}
