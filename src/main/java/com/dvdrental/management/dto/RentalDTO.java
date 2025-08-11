package com.dvdrental.management.dto;

import java.time.LocalDateTime;

public record RentalDTO(
    Integer rentalId,
    LocalDateTime rentalDate,
    Integer inventoryId,
    Short customerId,
    LocalDateTime returnDate,
    Short staffId,
    LocalDateTime lastUpdate
) {}
