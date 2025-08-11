package com.dvdrental.management.dto;

import java.time.LocalDateTime;

public record CountryDTO(
    Integer countryId,
    String country,
    LocalDateTime lastUpdate
) {}
