package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;


import javax.validation.Valid;


import java.util.Set;


@RestController
@Slf4j
public class FilmController {
    @Autowired
    FilmService filmService;

    //Создаем фильм
    @PostMapping(value = "/films")
    public Film createFilm(@Valid @RequestBody Film film) {
        log.info("POST request create film");
        return filmService.createFilm(film);
    }

    //Обновляем фильм
    @PutMapping(value = "/films")
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("PUT request update film");
        return filmService.updateFilm(film);
    }

    //Получаем список всех фильмов
    @GetMapping("/films")
    public Set<Film> getAllFilms() {
        log.info("GET request for all films");
        return filmService.getAllFilms();
    }

    //Получаем фильм по ID
    @GetMapping("/films/{filmId}")
    public Film getFilmForID(@PathVariable int filmId) {
        log.info("GET request get film for filmID");
        return filmService.getFilmForID(filmId);
    }

    //Добавляем лайк фильму
    @PutMapping(value = "/films/{id}/like/{userId}")
    public Integer addLike(@PathVariable int id, @PathVariable int userId) {
        log.info("PUT request add like to the film");
        return filmService.addLike(id, userId);
    }

    //Удаляем лайк у фильма
    @DeleteMapping("/films/{id}/like/{userId}")
    public Integer deleteLike(@PathVariable int id, @PathVariable int userId) {
        log.info("DELETE request delete like to the film");
        return filmService.deleteLike(id, userId);
    }

    // Получаем 10 популярных фильмов
    @GetMapping("/films/popular")
    public Set<Film> getTenPopularFilms(@RequestParam(required = false, defaultValue = "10") int count) {
        log.info("GET request for ten popular films");
        return filmService.getTenPopularFilms(count);
    }
}