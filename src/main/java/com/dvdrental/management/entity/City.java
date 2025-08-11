package com.dvdrental.management.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "city")
public class City {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Integer cityId;
    
    @Column(name = "city", nullable = false, length = 50)
    private String city;
    
    @Column(name = "country_id", nullable = false)
    private Short countryId;
    
    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;
    
    // JPA relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", insertable = false, updatable = false)
    private Country country;
    
    // Constructors
    public City() {
    }
    
    public City(String city, Short countryId) {
        this.city = city;
        this.countryId = countryId;
        this.lastUpdate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Integer getCityId() {
        return cityId;
    }
    
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public Short getCountryId() {
        return countryId;
    }
    
    public void setCountryId(Short countryId) {
        this.countryId = countryId;
    }
    
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }
    
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    
    public Country getCountry() {
        return country;
    }
    
    public void setCountry(Country country) {
        this.country = country;
    }
    
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.lastUpdate = LocalDateTime.now();
    }
}
