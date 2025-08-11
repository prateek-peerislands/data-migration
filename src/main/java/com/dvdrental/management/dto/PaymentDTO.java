package com.dvdrental.management.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentDTO(
    Integer paymentId,
    Short customerId,
    Short staffId,
    Integer rentalId,
    BigDecimal amount,
    LocalDateTime paymentDate
) {}
