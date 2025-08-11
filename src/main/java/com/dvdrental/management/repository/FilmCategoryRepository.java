package com.dvdrental.management.repository;

import com.dvdrental.management.entity.FilmCategory;
import com.dvdrental.management.entity.FilmCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmCategoryRepository extends JpaRepository<FilmCategory, FilmCategoryId> {
    List<FilmCategory> findByFilmId(Short filmId);
    List<FilmCategory> findByCategoryId(Short categoryId);
}
