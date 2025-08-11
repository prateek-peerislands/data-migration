package com.dvdrental.management.service;

import com.dvdrental.management.dto.FilmDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface FilmService {
    List<FilmDTO> getAllFilms();
    Optional<FilmDTO> getFilmById(Integer id);
    FilmDTO saveFilm(FilmDTO filmDTO);
    FilmDTO updateFilm(Integer id, FilmDTO filmDTO);
    void deleteFilm(Integer id);
    
    List<FilmDTO> searchFilmsByTitle(String title);
    List<FilmDTO> getFilmsByYear(Integer year);
    List<FilmDTO> getFilmsByLanguage(Short languageId);
    List<FilmDTO> getFilmsByRating(String rating);
    List<FilmDTO> getFilmsByRentalRateRange(BigDecimal minRate, BigDecimal maxRate);
    List<FilmDTO> searchFilmsByKeyword(String keyword);
}
