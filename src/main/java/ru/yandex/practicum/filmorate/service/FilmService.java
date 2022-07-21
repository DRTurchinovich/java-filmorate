package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.dao.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;


import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final InMemoryUserStorage userStorage;
    private final InMemoryFilmStorage filmStorage;
    private static final LocalDate minDate = LocalDate.of(1895, 12, 28);

    @Autowired
    public FilmService(InMemoryUserStorage userStorage, InMemoryFilmStorage filmStorage) {
        this.userStorage = userStorage;
        this.filmStorage = filmStorage;
    }


    //Создаем фильм
    public Film createFilm(Film film) {
        if (film.getReleaseDate().isBefore(minDate)) {
            throw new ValidationException("Incorrect data release");
        }
        return filmStorage.createFilm(film);
    }

    //Обновляем фильм
    public Film updateFilm(Film film) {
        final Film updatedFilm = filmStorage.getFilmForID(film.getId());
        if (updatedFilm == null) {
            throw new NotFoundException("Film with ID = " + film.getId() + " not found.");
        }
        return filmStorage.updateFilm(film);
    }

    //Получаем список всех фильмов
    public Set<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    //Получаем фильм по ID
    public Film getFilmForID(int filmId) {
        final Film film = filmStorage.getFilmForID(filmId);
        if (film == null) {
            throw new NotFoundException("Film with ID = " + filmId + " not found.");
        }
        return filmStorage.getFilmForID(filmId);
    }

    //Добавляем лайк фильму
    public Integer addLike(int filmID, int userID) {
        User user = userStorage.getUserForID(userID);
        Film film = filmStorage.getFilmForID(filmID);
        if (user == null) {
            throw new NotFoundException("User with ID = " + userID + " not found.");
        }
        if (film == null) {
            throw new NotFoundException("Film with ID = " + filmID + " not found.");
        }
        return filmStorage.addLike(film, user);
    }

    //Удаляем лайк у фильма
    public Integer deleteLike(int filmID, int userID) {
        User user = userStorage.getUserForID(userID);
        Film film = filmStorage.getFilmForID(filmID);
        if (user == null) {
            throw new NotFoundException("User with ID = " + userID + " not found.");
        }
        if (film == null) {
            throw new NotFoundException("Film with ID = " + filmID + " not found.");
        }
        return filmStorage.deleteLike(film, user);
    }

    // Получаем 10 популярных фильмов
    public Set<Film> getTenPopularFilms(int count) {
        return filmStorage.getAllFilms().stream().sorted((c0, c1) ->
                        c1.getLikes().size() - (c0.getLikes().size())).
                limit(count).collect(Collectors.toSet());
    }
}
