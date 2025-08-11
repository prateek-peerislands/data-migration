package com.dvdrental.management.dto;

import java.time.LocalDateTime;

public record StoreDTO(
    Integer storeId,
    Short managerStaffId,
    Short addressId,
    LocalDateTime lastUpdate
) {}
