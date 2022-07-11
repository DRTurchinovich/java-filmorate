package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;

@RestController
@Slf4j
public class FilmController {
    private HashMap<Integer, Film> films = new HashMap<>();
    private Integer filmID = 0;

    private void validationFilm(Film film) {
        final LocalDate minDate = LocalDate.of(1895, 12, 28);
        if (film.getName() == null || film.getName().isBlank()) {
            throw new ValidationException("film: error name");
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException("film: error length description");
        }
        if (film.getReleaseDate().isBefore(minDate)) {
            throw new ValidationException("film: error release date");
        }
        if (film.getDuration() <= 0) {
            throw new ValidationException("film: error duration time");
        }
    }

    @PostMapping(value = "/films")
    public Film createFilm(@RequestBody Film film) {
        validationFilm(film);
        film.setId(++filmID);
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping(value = "/films")
    public Film updateFilm(@RequestBody Film film) {
        if (films.containsKey(film.getId())) {
            validationFilm(film);
            films.put(film.getId(), film);
        } else {
            log.info("error {} id", film.getId());
            throw new ValidationException("film: id not found");
        }
        return films.get(film.getId());
    }

    @GetMapping("/films")
    public HashSet<Film> getFilms() {
        return new HashSet<>(films.values());
    }
}