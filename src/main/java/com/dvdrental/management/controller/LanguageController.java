package com.dvdrental.management.controller;

import com.dvdrental.management.dto.LanguageDTO;
import com.dvdrental.management.entity.Language;
import com.dvdrental.management.repository.LanguageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/languages")
@CrossOrigin(origins = "*")
public class LanguageController {

    private final LanguageRepository languageRepository;

    public LanguageController(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @GetMapping
    public ResponseEntity<List<LanguageDTO>> getAllLanguages() {
        List<LanguageDTO> languages = languageRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(languages, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LanguageDTO> getLanguageById(@PathVariable Integer id) {
        Optional<LanguageDTO> language = languageRepository.findById(id).map(this::convertToDTO);
        return language.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<LanguageDTO> createLanguage(@RequestBody LanguageDTO languageDTO) {
        try {
            Language language = convertToEntity(languageDTO);
            Language savedLanguage = languageRepository.save(language);
            return new ResponseEntity<>(convertToDTO(savedLanguage), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private LanguageDTO convertToDTO(Language language) {
        return new LanguageDTO(language.getLanguageId(), language.getName(), language.getLastUpdate());
    }

    private Language convertToEntity(LanguageDTO languageDTO) {
        Language language = new Language();
        language.setName(languageDTO.name());
        language.setLastUpdate(LocalDateTime.now());
        return language;
    }
}
