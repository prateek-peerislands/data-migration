package com.dvdrental.management.dto;

import java.time.LocalDateTime;

public record AddressDTO(
    Integer addressId,
    String address,
    String address2,
    String district,
    Short cityId,
    String postalCode,
    String phone,
    LocalDateTime lastUpdate
) {}
