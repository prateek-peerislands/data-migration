package com.dvdrental.management.controller;

import com.dvdrental.management.dto.FilmActorDTO;
import com.dvdrental.management.entity.FilmActor;
import com.dvdrental.management.entity.FilmActorId;
import com.dvdrental.management.repository.FilmActorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/film-actors")
@CrossOrigin(origins = "*")
public class FilmActorController {

    private final FilmActorRepository filmActorRepository;

    public FilmActorController(FilmActorRepository filmActorRepository) {
        this.filmActorRepository = filmActorRepository;
    }

    @GetMapping
    public ResponseEntity<List<FilmActorDTO>> getAllFilmActors() {
        List<FilmActorDTO> filmActors = filmActorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(filmActors, HttpStatus.OK);
    }

    @GetMapping("/{actorId}/{filmId}")
    public ResponseEntity<FilmActorDTO> getFilmActorById(@PathVariable Short actorId, @PathVariable Short filmId) {
        FilmActorId id = new FilmActorId(actorId, filmId);
        Optional<FilmActorDTO> filmActor = filmActorRepository.findById(id).map(this::convertToDTO);
        return filmActor.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<FilmActorDTO> createFilmActor(@RequestBody FilmActorDTO filmActorDTO) {
        try {
            FilmActor filmActor = convertToEntity(filmActorDTO);
            FilmActor savedFilmActor = filmActorRepository.save(filmActor);
            return new ResponseEntity<>(convertToDTO(savedFilmActor), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/actor/{actorId}")
    public ResponseEntity<List<FilmActorDTO>> getFilmsByActor(@PathVariable Short actorId) {
        List<FilmActorDTO> filmActors = filmActorRepository.findByActorId(actorId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(filmActors, HttpStatus.OK);
    }

    @GetMapping("/film/{filmId}")
    public ResponseEntity<List<FilmActorDTO>> getActorsByFilm(@PathVariable Short filmId) {
        List<FilmActorDTO> filmActors = filmActorRepository.findByFilmId(filmId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(filmActors, HttpStatus.OK);
    }

    private FilmActorDTO convertToDTO(FilmActor filmActor) {
        return new FilmActorDTO(
                filmActor.getActorId(),
                filmActor.getFilmId(),
                filmActor.getLastUpdate()
        );
    }

    private FilmActor convertToEntity(FilmActorDTO filmActorDTO) {
        FilmActor filmActor = new FilmActor();
        filmActor.setActorId(filmActorDTO.actorId());
        filmActor.setFilmId(filmActorDTO.filmId());
        filmActor.setLastUpdate(LocalDateTime.now());
        return filmActor;
    }
}
