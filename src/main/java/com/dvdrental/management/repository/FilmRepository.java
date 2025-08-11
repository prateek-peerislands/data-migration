package com.dvdrental.management.repository;

import com.dvdrental.management.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer> {
    List<Film> findByTitleContainingIgnoreCase(String title);
    List<Film> findByReleaseYear(Integer releaseYear);
    List<Film> findByLanguageId(Short languageId);
    List<Film> findByRating(String rating);
    List<Film> findByRentalRateBetween(BigDecimal minRate, BigDecimal maxRate);
    List<Film> findByLengthBetween(Short minLength, Short maxLength);
    
    @Query("SELECT f FROM Film f WHERE f.title LIKE %:keyword% OR f.description LIKE %:keyword%")
    List<Film> searchByKeyword(@Param("keyword") String keyword);
}
