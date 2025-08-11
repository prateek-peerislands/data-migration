package com.dvdrental.management.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
public class Inventory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Integer inventoryId;
    
    @Column(name = "film_id", nullable = false)
    private Short filmId;
    
    @Column(name = "store_id", nullable = false)
    private Short storeId;
    
    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;
    
    // JPA relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", insertable = false, updatable = false)
    private Film film;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", insertable = false, updatable = false)
    private Store store;
    
    // Constructors
    public Inventory() {
    }
    
    public Inventory(Short filmId, Short storeId) {
        this.filmId = filmId;
        this.storeId = storeId;
        this.lastUpdate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Integer getInventoryId() {
        return inventoryId;
    }
    
    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }
    
    public Short getFilmId() {
        return filmId;
    }
    
    public void setFilmId(Short filmId) {
        this.filmId = filmId;
    }
    
    public Short getStoreId() {
        return storeId;
    }
    
    public void setStoreId(Short storeId) {
        this.storeId = storeId;
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
    
    public Store getStore() {
        return store;
    }
    
    public void setStore(Store store) {
        this.store = store;
    }
    
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.lastUpdate = LocalDateTime.now();
    }
}
