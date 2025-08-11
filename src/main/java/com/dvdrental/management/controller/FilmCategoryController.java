package com.dvdrental.management.controller;

import com.dvdrental.management.dto.FilmCategoryDTO;
import com.dvdrental.management.entity.FilmCategory;
import com.dvdrental.management.entity.FilmCategoryId;
import com.dvdrental.management.repository.FilmCategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/film-categories")
@CrossOrigin(origins = "*")
public class FilmCategoryController {

    private final FilmCategoryRepository filmCategoryRepository;

    public FilmCategoryController(FilmCategoryRepository filmCategoryRepository) {
        this.filmCategoryRepository = filmCategoryRepository;
    }

    @GetMapping
    public ResponseEntity<List<FilmCategoryDTO>> getAllFilmCategories() {
        List<FilmCategoryDTO> filmCategories = filmCategoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(filmCategories, HttpStatus.OK);
    }

    @GetMapping("/{filmId}/{categoryId}")
    public ResponseEntity<FilmCategoryDTO> getFilmCategoryById(@PathVariable Short filmId, @PathVariable Short categoryId) {
        FilmCategoryId id = new FilmCategoryId(filmId, categoryId);
        Optional<FilmCategoryDTO> filmCategory = filmCategoryRepository.findById(id).map(this::convertToDTO);
        return filmCategory.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<FilmCategoryDTO> createFilmCategory(@RequestBody FilmCategoryDTO filmCategoryDTO) {
        try {
            FilmCategory filmCategory = convertToEntity(filmCategoryDTO);
            FilmCategory savedFilmCategory = filmCategoryRepository.save(filmCategory);
            return new ResponseEntity<>(convertToDTO(savedFilmCategory), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/film/{filmId}")
    public ResponseEntity<List<FilmCategoryDTO>> getCategoriesByFilm(@PathVariable Short filmId) {
        List<FilmCategoryDTO> filmCategories = filmCategoryRepository.findByFilmId(filmId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(filmCategories, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<FilmCategoryDTO>> getFilmsByCategory(@PathVariable Short categoryId) {
        List<FilmCategoryDTO> filmCategories = filmCategoryRepository.findByCategoryId(categoryId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(filmCategories, HttpStatus.OK);
    }

    private FilmCategoryDTO convertToDTO(FilmCategory filmCategory) {
        return new FilmCategoryDTO(
                filmCategory.getFilmId(),
                filmCategory.getCategoryId(),
                filmCategory.getLastUpdate()
        );
    }

    private FilmCategory convertToEntity(FilmCategoryDTO filmCategoryDTO) {
        FilmCategory filmCategory = new FilmCategory();
        filmCategory.setFilmId(filmCategoryDTO.filmId());
        filmCategory.setCategoryId(filmCategoryDTO.categoryId());
        filmCategory.setLastUpdate(LocalDateTime.now());
        return filmCategory;
    }
}
