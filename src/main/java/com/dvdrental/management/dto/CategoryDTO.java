package com.dvdrental.management.dto;

import java.time.LocalDateTime;

public record CategoryDTO(
    Integer categoryId,
    String name,
    LocalDateTime lastUpdate
) {}
