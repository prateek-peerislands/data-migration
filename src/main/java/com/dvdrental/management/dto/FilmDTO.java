package com.dvdrental.management.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FilmDTO(
    Integer filmId,
    String title,
    String description,
    Integer releaseYear,
    Short languageId,
    Short rentalDuration,
    BigDecimal rentalRate,
    Short length,
    BigDecimal replacementCost,
    String rating,
    LocalDateTime lastUpdate,
    String[] specialFeatures
) {}
