package com.dvdrental.management.dto;

import java.time.LocalDateTime;

public record InventoryDTO(
    Integer inventoryId,
    Short filmId,
    Short storeId,
    LocalDateTime lastUpdate
) {}
