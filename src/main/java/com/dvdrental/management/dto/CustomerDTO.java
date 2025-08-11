package com.dvdrental.management.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CustomerDTO(
    Integer customerId,
    Short storeId,
    String firstName,
    String lastName,
    String email,
    Short addressId,
    Boolean activebool,
    LocalDate createDate,
    LocalDateTime lastUpdate,
    Integer active
) {}
