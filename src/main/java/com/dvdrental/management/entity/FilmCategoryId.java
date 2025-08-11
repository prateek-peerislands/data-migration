package com.dvdrental.management.entity;

import java.io.Serializable;
import java.util.Objects;

public class FilmCategoryId implements Serializable {
    
    private Short filmId;
    private Short categoryId;
    
    // Constructors
    public FilmCategoryId() {
    }
    
    public FilmCategoryId(Short filmId, Short categoryId) {
        this.filmId = filmId;
        this.categoryId = categoryId;
    }
    
    // Getters and Setters
    public Short getFilmId() {
        return filmId;
    }
    
    public void setFilmId(Short filmId) {
        this.filmId = filmId;
    }
    
    public Short getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(Short categoryId) {
        this.categoryId = categoryId;
    }
    
    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmCategoryId that = (FilmCategoryId) o;
        return Objects.equals(filmId, that.filmId) && Objects.equals(categoryId, that.categoryId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(filmId, categoryId);
    }
}
