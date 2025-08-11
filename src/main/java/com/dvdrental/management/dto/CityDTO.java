package com.dvdrental.management.dto;

import java.time.LocalDateTime;

public record CityDTO(
    Integer cityId,
    String city,
    Short countryId,
    LocalDateTime lastUpdate
) {}
