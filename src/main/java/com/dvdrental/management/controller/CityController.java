package com.dvdrental.management.controller;

import com.dvdrental.management.dto.CityDTO;
import com.dvdrental.management.entity.City;
import com.dvdrental.management.repository.CityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cities")
@CrossOrigin(origins = "*")
public class CityController {

    private final CityRepository cityRepository;

    public CityController(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @GetMapping
    public ResponseEntity<List<CityDTO>> getAllCities() {
        List<CityDTO> cities = cityRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityDTO> getCityById(@PathVariable Integer id) {
        Optional<CityDTO> city = cityRepository.findById(id).map(this::convertToDTO);
        return city.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<CityDTO> createCity(@RequestBody CityDTO cityDTO) {
        try {
            City city = convertToEntity(cityDTO);
            City savedCity = cityRepository.save(city);
            return new ResponseEntity<>(convertToDTO(savedCity), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityDTO> updateCity(@PathVariable Integer id, @RequestBody CityDTO cityDTO) {
        try {
            City city = cityRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("City not found"));
            city.setCity(cityDTO.city());
            city.setCountryId(cityDTO.countryId());
            City updatedCity = cityRepository.save(city);
            return new ResponseEntity<>(convertToDTO(updatedCity), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Integer id) {
        try {
            cityRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/country/{countryId}")
    public ResponseEntity<List<CityDTO>> getCitiesByCountry(@PathVariable Short countryId) {
        List<CityDTO> cities = cityRepository.findByCountryId(countryId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    private CityDTO convertToDTO(City city) {
        return new CityDTO(city.getCityId(), city.getCity(), city.getCountryId(), city.getLastUpdate());
    }

    private City convertToEntity(CityDTO cityDTO) {
        City city = new City();
        city.setCity(cityDTO.city());
        city.setCountryId(cityDTO.countryId());
        city.setLastUpdate(LocalDateTime.now());
        return city;
    }
}
