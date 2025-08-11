package com.dvdrental.management.dto;

import java.time.LocalDateTime;

public record ActorDTO(
    Integer actorId,
    String firstName,
    String lastName,
    LocalDateTime lastUpdate
) {}
