package com.dvdrental.management.repository;

import com.dvdrental.management.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {
    List<Rental> findByCustomerId(Short customerId);
    List<Rental> findByStaffId(Short staffId);
    List<Rental> findByInventoryId(Integer inventoryId);
    List<Rental> findByReturnDateIsNull();
    List<Rental> findByRentalDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT r FROM Rental r WHERE r.customerId = :customerId AND r.returnDate IS NULL")
    List<Rental> findActiveRentalsByCustomer(@Param("customerId") Short customerId);
}
