package com.dvdrental.management.repository;

import com.dvdrental.management.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {
    List<Staff> findByStoreId(Short storeId);
    List<Staff> findByActive(Boolean active);
    Optional<Staff> findByUsername(String username);
    List<Staff> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
}
