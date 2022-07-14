package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@RestController
@Slf4j
public class FilmController {
    private Map<Integer, Film> films = new HashMap<>();
    private Integer filmID = 0;

    private void validationFilm(Film film) throws ValidationException {
       final LocalDate minDate = LocalDate.of(1895, 12, 28);
        if (film.getName() == null || film.getName().isBlank()) {
            log.error("error {} name", film.getName());
            throw new ValidationException("film: error name");
        }
        if (film.getDescription().length() > 200) {
            log.error("error {} description", film.getDescription());
            throw new ValidationException("film: error length description");
        }
        if (film.getReleaseDate().isBefore(minDate)) {
            log.error("error {} releaseDate", film.getReleaseDate());
            throw new ValidationException("film: error release date");
        }
        if (film.getDuration() <= 0) {
            log.error("error {} duration", film.getDuration());
            throw new ValidationException("film: error duration time");
        }

    }

    @PostMapping(value = "/films")
    public Film createFilm(@Valid @RequestBody Film film) throws ValidationException  {
        log.info("create request");
        log.info("validation");
        validationFilm(film);
        log.info("complete validation");
        film.setId(++filmID);
        films.put(film.getId(), film);
        log.info("complete request");
        return film;
    }

    @PutMapping(value = "/films")
    public Film updateFilm(@Valid @RequestBody Film film) throws ValidationException {
        log.info("create request");
        if (films.containsKey(film.getId())) {
            log.info("validation");
            validationFilm(film);
            log.info("complete validation");
            films.put(film.getId(), film);
        } else {
            log.error("film: id {} not found ", film.getId());
            throw new ValidationException("film: id not found");
        }
        log.info("complete request");
        return films.get(film.getId());
    }

    @GetMapping("/films")
    public HashSet<Film> getFilms() {
        return new HashSet<>(films.values());
    }
}