package com.dvdrental.management.repository;

import com.dvdrental.management.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    List<Address> findByCityId(Short cityId);
    List<Address> findByDistrictContainingIgnoreCase(String district);
    List<Address> findByPostalCode(String postalCode);
}
