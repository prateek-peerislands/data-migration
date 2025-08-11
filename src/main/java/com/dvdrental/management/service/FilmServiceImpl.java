package com.dvdrental.management.service;

import com.dvdrental.management.dto.FilmDTO;
import com.dvdrental.management.entity.Film;
import com.dvdrental.management.repository.FilmRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;

    public FilmServiceImpl(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public List<FilmDTO> getAllFilms() {
        return filmRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<FilmDTO> getFilmById(Integer id) {
        return filmRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public FilmDTO saveFilm(FilmDTO filmDTO) {
        Film film = convertToEntity(filmDTO);
        Film savedFilm = filmRepository.save(film);
        return convertToDTO(savedFilm);
    }

    @Override
    public FilmDTO updateFilm(Integer id, FilmDTO filmDTO) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Film not found with id: " + id));
        
        film.setTitle(filmDTO.title());
        film.setDescription(filmDTO.description());
        film.setReleaseYear(filmDTO.releaseYear());
        film.setLanguageId(filmDTO.languageId());
        film.setRentalDuration(filmDTO.rentalDuration());
        film.setRentalRate(filmDTO.rentalRate());
        film.setLength(filmDTO.length());
        film.setReplacementCost(filmDTO.replacementCost());
        film.setRating(filmDTO.rating());
        film.setSpecialFeatures(filmDTO.specialFeatures());
        
        Film updatedFilm = filmRepository.save(film);
        return convertToDTO(updatedFilm);
    }

    @Override
    public void deleteFilm(Integer id) {
        if (!filmRepository.existsById(id)) {
            throw new RuntimeException("Film not found with id: " + id);
        }
        filmRepository.deleteById(id);
    }

    @Override
    public List<FilmDTO> searchFilmsByTitle(String title) {
        return filmRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FilmDTO> getFilmsByYear(Integer year) {
        return filmRepository.findByReleaseYear(year).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FilmDTO> getFilmsByLanguage(Short languageId) {
        return filmRepository.findByLanguageId(languageId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FilmDTO> getFilmsByRating(String rating) {
        return filmRepository.findByRating(rating).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FilmDTO> getFilmsByRentalRateRange(BigDecimal minRate, BigDecimal maxRate) {
        return filmRepository.findByRentalRateBetween(minRate, maxRate).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FilmDTO> searchFilmsByKeyword(String keyword) {
        return filmRepository.searchByKeyword(keyword).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private FilmDTO convertToDTO(Film film) {
        return new FilmDTO(
                film.getFilmId(),
                film.getTitle(),
                film.getDescription(),
                film.getReleaseYear(),
                film.getLanguageId(),
                film.getRentalDuration(),
                film.getRentalRate(),
                film.getLength(),
                film.getReplacementCost(),
                film.getRating(),
                film.getLastUpdate(),
                film.getSpecialFeatures()
        );
    }

    private Film convertToEntity(FilmDTO filmDTO) {
        Film film = new Film();
        film.setTitle(filmDTO.title());
        film.setDescription(filmDTO.description());
        film.setReleaseYear(filmDTO.releaseYear());
        film.setLanguageId(filmDTO.languageId());
        film.setRentalDuration(filmDTO.rentalDuration());
        film.setRentalRate(filmDTO.rentalRate());
        film.setLength(filmDTO.length());
        film.setReplacementCost(filmDTO.replacementCost());
        film.setRating(filmDTO.rating());
        film.setSpecialFeatures(filmDTO.specialFeatures());
        film.setLastUpdate(LocalDateTime.now());
        return film;
    }
}
