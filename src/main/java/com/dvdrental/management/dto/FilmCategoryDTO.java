package com.dvdrental.management.dto;

import java.time.LocalDateTime;

public record FilmCategoryDTO(
    Short filmId,
    Short categoryId,
    LocalDateTime lastUpdate
) {}
