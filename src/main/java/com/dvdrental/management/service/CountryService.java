package com.dvdrental.management.service;

import com.dvdrental.management.dto.CountryDTO;

import java.util.List;
import java.util.Optional;

public interface CountryService {
    List<CountryDTO> getAllCountries();
    Optional<CountryDTO> getCountryById(Integer id);
    CountryDTO saveCountry(CountryDTO countryDTO);
    CountryDTO updateCountry(Integer id, CountryDTO countryDTO);
    void deleteCountry(Integer id);
    List<CountryDTO> searchCountriesByName(String name);
}
