package com.dvdrental.management.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "film")
public class Film {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private Integer filmId;
    
    @Column(name = "title", nullable = false, length = 255)
    private String title;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "release_year")
    private Integer releaseYear;
    
    @Column(name = "language_id", nullable = false)
    private Short languageId;
    
    @Column(name = "rental_duration", nullable = false)
    private Short rentalDuration = 3;
    
    @Column(name = "rental_rate", nullable = false, precision = 4, scale = 2)
    private BigDecimal rentalRate = new BigDecimal("4.99");
    
    @Column(name = "length")
    private Short length;
    
    @Column(name = "replacement_cost", nullable = false, precision = 5, scale = 2)
    private BigDecimal replacementCost = new BigDecimal("19.99");
    
    @Column(name = "rating", columnDefinition = "mpaa_rating")
    private String rating = "G";
    
    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;
    
    @Column(name = "special_features", columnDefinition = "TEXT[]")
    private String[] specialFeatures;
    
    @Column(name = "fulltext", columnDefinition = "tsvector")
    private String fulltext;
    
    // JPA relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", insertable = false, updatable = false)
    private Language language;
    
    // Constructors
    public Film() {
    }
    
    public Film(String title, Short languageId) {
        this.title = title;
        this.languageId = languageId;
        this.lastUpdate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Integer getFilmId() {
        return filmId;
    }
    
    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Integer getReleaseYear() {
        return releaseYear;
    }
    
    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }
    
    public Short getLanguageId() {
        return languageId;
    }
    
    public void setLanguageId(Short languageId) {
        this.languageId = languageId;
    }
    
    public Short getRentalDuration() {
        return rentalDuration;
    }
    
    public void setRentalDuration(Short rentalDuration) {
        this.rentalDuration = rentalDuration;
    }
    
    public BigDecimal getRentalRate() {
        return rentalRate;
    }
    
    public void setRentalRate(BigDecimal rentalRate) {
        this.rentalRate = rentalRate;
    }
    
    public Short getLength() {
        return length;
    }
    
    public void setLength(Short length) {
        this.length = length;
    }
    
    public BigDecimal getReplacementCost() {
        return replacementCost;
    }
    
    public void setReplacementCost(BigDecimal replacementCost) {
        this.replacementCost = replacementCost;
    }
    
    public String getRating() {
        return rating;
    }
    
    public void setRating(String rating) {
        this.rating = rating;
    }
    
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }
    
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    
    public String[] getSpecialFeatures() {
        return specialFeatures;
    }
    
    public void setSpecialFeatures(String[] specialFeatures) {
        this.specialFeatures = specialFeatures;
    }
    
    public String getFulltext() {
        return fulltext;
    }
    
    public void setFulltext(String fulltext) {
        this.fulltext = fulltext;
    }
    
    public Language getLanguage() {
        return language;
    }
    
    public void setLanguage(Language language) {
        this.language = language;
    }
    
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.lastUpdate = LocalDateTime.now();
    }
}
