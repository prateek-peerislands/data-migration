package com.dvdrental.management.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "film_actor")
@IdClass(FilmActorId.class)
public class FilmActor {
    
    @Id
    @Column(name = "actor_id")
    private Short actorId;
    
    @Id
    @Column(name = "film_id")
    private Short filmId;
    
    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;
    
    // JPA relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id", insertable = false, updatable = false)
    private Actor actor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", insertable = false, updatable = false)
    private Film film;
    
    // Constructors
    public FilmActor() {
    }
    
    public FilmActor(Short actorId, Short filmId) {
        this.actorId = actorId;
        this.filmId = filmId;
        this.lastUpdate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Short getActorId() {
        return actorId;
    }
    
    public void setActorId(Short actorId) {
        this.actorId = actorId;
    }
    
    public Short getFilmId() {
        return filmId;
    }
    
    public void setFilmId(Short filmId) {
        this.filmId = filmId;
    }
    
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }
    
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    
    public Actor getActor() {
        return actor;
    }
    
    public void setActor(Actor actor) {
        this.actor = actor;
    }
    
    public Film getFilm() {
        return film;
    }
    
    public void setFilm(Film film) {
        this.film = film;
    }
    
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.lastUpdate = LocalDateTime.now();
    }
}
