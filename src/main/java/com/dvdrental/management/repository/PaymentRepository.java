package com.dvdrental.management.repository;

import com.dvdrental.management.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByCustomerId(Short customerId);
    List<Payment> findByStaffId(Short staffId);
    List<Payment> findByRentalId(Integer rentalId);
    List<Payment> findByPaymentDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Payment> findByAmountBetween(BigDecimal minAmount, BigDecimal maxAmount);
    
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.customerId = :customerId")
    BigDecimal getTotalPaymentsByCustomer(@Param("customerId") Short customerId);
}
