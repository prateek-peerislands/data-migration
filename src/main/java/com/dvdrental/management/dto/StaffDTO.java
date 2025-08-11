package com.dvdrental.management.dto;

import java.time.LocalDateTime;

public record StaffDTO(
    Integer staffId,
    String firstName,
    String lastName,
    Short addressId,
    String email,
    Short storeId,
    Boolean active,
    String username,
    String password,
    LocalDateTime lastUpdate,
    byte[] picture
) {}
