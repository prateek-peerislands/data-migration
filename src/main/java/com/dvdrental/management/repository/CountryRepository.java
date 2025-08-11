package com.dvdrental.management.repository;

import com.dvdrental.management.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    List<Country> findByCountryContainingIgnoreCase(String country);
}
