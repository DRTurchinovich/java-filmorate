package ru.yandex.practicum.filmorate.dao;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private Map<Integer, Film> films = new HashMap<>();
    private Integer filmID = 0;


    @Override
    public Film createFilm(Film film) {
        film.setId(++filmID);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        } else {
            throw new NotFoundException("film: id not found");
        }
        return films.get(film.getId());
    }

    @Override
    public Set<Film> getAllFilms() {
        return new HashSet<>(films.values());
    }

    //Получаем фильм по ID
    @Override
    public Film getFilmForID(int filmID) {
        return films.get(filmID);
    }

    //Добавляем лайк фильму
    public Integer addLike(Film film, User user) {
        getFilmForID(film.getId()).getLikes().add(user.getId());
        return getFilmForID(film.getId()).getLikes().size();
    }

    //Удаляем лайк у фильма
    public Integer deleteLike(Film film, User user) {
        getFilmForID(film.getId()).getLikes().remove(user.getId());
        return getFilmForID(film.getId()).getLikes().size();
    }
}
