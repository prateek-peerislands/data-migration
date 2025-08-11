package com.dvdrental.management.dto;

import java.time.LocalDateTime;

public record FilmActorDTO(
    Short actorId,
    Short filmId,
    LocalDateTime lastUpdate
) {}
