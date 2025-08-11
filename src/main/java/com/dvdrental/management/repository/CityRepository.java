package com.dvdrental.management.repository;

import com.dvdrental.management.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    List<City> findByCityContainingIgnoreCase(String city);
    List<City> findByCountryId(Short countryId);
}
