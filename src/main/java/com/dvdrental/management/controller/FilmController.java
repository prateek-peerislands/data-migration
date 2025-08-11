package com.dvdrental.management.controller;

import com.dvdrental.management.dto.FilmDTO;
import com.dvdrental.management.service.FilmService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/films")
@CrossOrigin(origins = "*")
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public ResponseEntity<List<FilmDTO>> getAllFilms() {
        List<FilmDTO> films = filmService.getAllFilms();
        return new ResponseEntity<>(films, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmDTO> getFilmById(@PathVariable Integer id) {
        Optional<FilmDTO> film = filmService.getFilmById(id);
        return film.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<FilmDTO> createFilm(@RequestBody FilmDTO filmDTO) {
        try {
            FilmDTO savedFilm = filmService.saveFilm(filmDTO);
            return new ResponseEntity<>(savedFilm, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<FilmDTO> updateFilm(@PathVariable Integer id, @RequestBody FilmDTO filmDTO) {
        try {
            FilmDTO updatedFilm = filmService.updateFilm(id, filmDTO);
            return new ResponseEntity<>(updatedFilm, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable Integer id) {
        try {
            filmService.deleteFilm(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<FilmDTO>> searchFilmsByTitle(@RequestParam String title) {
        List<FilmDTO> films = filmService.searchFilmsByTitle(title);
        return new ResponseEntity<>(films, HttpStatus.OK);
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<List<FilmDTO>> getFilmsByYear(@PathVariable Integer year) {
        List<FilmDTO> films = filmService.getFilmsByYear(year);
        return new ResponseEntity<>(films, HttpStatus.OK);
    }

    @GetMapping("/language/{languageId}")
    public ResponseEntity<List<FilmDTO>> getFilmsByLanguage(@PathVariable Short languageId) {
        List<FilmDTO> films = filmService.getFilmsByLanguage(languageId);
        return new ResponseEntity<>(films, HttpStatus.OK);
    }

    @GetMapping("/rating/{rating}")
    public ResponseEntity<List<FilmDTO>> getFilmsByRating(@PathVariable String rating) {
        List<FilmDTO> films = filmService.getFilmsByRating(rating);
        return new ResponseEntity<>(films, HttpStatus.OK);
    }

    @GetMapping("/rental-rate")
    public ResponseEntity<List<FilmDTO>> getFilmsByRentalRateRange(
            @RequestParam BigDecimal minRate,
            @RequestParam BigDecimal maxRate) {
        List<FilmDTO> films = filmService.getFilmsByRentalRateRange(minRate, maxRate);
        return new ResponseEntity<>(films, HttpStatus.OK);
    }

    @GetMapping("/keyword")
    public ResponseEntity<List<FilmDTO>> searchFilmsByKeyword(@RequestParam String keyword) {
        List<FilmDTO> films = filmService.searchFilmsByKeyword(keyword);
        return new ResponseEntity<>(films, HttpStatus.OK);
    }
}
