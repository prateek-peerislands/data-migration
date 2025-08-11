package com.dvdrental.management.entity;

import java.io.Serializable;
import java.util.Objects;

public class FilmActorId implements Serializable {
    
    private Short actorId;
    private Short filmId;
    
    // Constructors
    public FilmActorId() {
    }
    
    public FilmActorId(Short actorId, Short filmId) {
        this.actorId = actorId;
        this.filmId = filmId;
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
    
    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmActorId that = (FilmActorId) o;
        return Objects.equals(actorId, that.actorId) && Objects.equals(filmId, that.filmId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(actorId, filmId);
    }
}
