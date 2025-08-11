package com.dvdrental.management.repository;

import com.dvdrental.management.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {
    Optional<Language> findByNameIgnoreCase(String name);
    List<Language> findByNameContainingIgnoreCase(String name);
}
