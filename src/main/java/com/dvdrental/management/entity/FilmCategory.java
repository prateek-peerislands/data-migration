package com.dvdrental.management.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "film_category")
@IdClass(FilmCategoryId.class)
public class FilmCategory {
    
    @Id
    @Column(name = "film_id")
    private Short filmId;
    
    @Id
    @Column(name = "category_id")
    private Short categoryId;
    
    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;
    
    // JPA relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", insertable = false, updatable = false)
    private Film film;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private Category category;
    
    // Constructors
    public FilmCategory() {
    }
    
    public FilmCategory(Short filmId, Short categoryId) {
        this.filmId = filmId;
        this.categoryId = categoryId;
        this.lastUpdate = LocalDateTime.now();
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
    
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }
    
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    
    public Film getFilm() {
        return film;
    }
    
    public void setFilm(Film film) {
        this.film = film;
    }
    
    public Category getCategory() {
        return category;
    }
    
    public void setCategory(Category category) {
        this.category = category;
    }
    
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.lastUpdate = LocalDateTime.now();
    }
}
