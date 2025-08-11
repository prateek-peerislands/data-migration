package com.dvdrental.management.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "country")
public class Country {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id")
    private Integer countryId;
    
    @Column(name = "country", nullable = false, length = 50)
    private String country;
    
    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;
    
    // Constructors
    public Country() {
    }
    
    public Country(String country) {
        this.country = country;
        this.lastUpdate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Integer getCountryId() {
        return countryId;
    }
    
    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }
    
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.lastUpdate = LocalDateTime.now();
    }
}
