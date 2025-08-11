package com.dvdrental.management.service;

import com.dvdrental.management.dto.CountryDTO;
import com.dvdrental.management.entity.Country;
import com.dvdrental.management.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<CountryDTO> getAllCountries() {
        return countryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CountryDTO> getCountryById(Integer id) {
        return countryRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public CountryDTO saveCountry(CountryDTO countryDTO) {
        Country country = convertToEntity(countryDTO);
        Country savedCountry = countryRepository.save(country);
        return convertToDTO(savedCountry);
    }

    @Override
    public CountryDTO updateCountry(Integer id, CountryDTO countryDTO) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Country not found with id: " + id));
        
        country.setCountry(countryDTO.country());
        
        Country updatedCountry = countryRepository.save(country);
        return convertToDTO(updatedCountry);
    }

    @Override
    public void deleteCountry(Integer id) {
        if (!countryRepository.existsById(id)) {
            throw new RuntimeException("Country not found with id: " + id);
        }
        countryRepository.deleteById(id);
    }

    @Override
    public List<CountryDTO> searchCountriesByName(String name) {
        return countryRepository.findByCountryContainingIgnoreCase(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CountryDTO convertToDTO(Country country) {
        return new CountryDTO(
                country.getCountryId(),
                country.getCountry(),
                country.getLastUpdate()
        );
    }

    private Country convertToEntity(CountryDTO countryDTO) {
        Country country = new Country();
        country.setCountry(countryDTO.country());
        country.setLastUpdate(LocalDateTime.now());
        return country;
    }
}
