package com.dvdrental.management.repository;

import com.dvdrental.management.entity.FilmActor;
import com.dvdrental.management.entity.FilmActorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmActorRepository extends JpaRepository<FilmActor, FilmActorId> {
    List<FilmActor> findByActorId(Short actorId);
    List<FilmActor> findByFilmId(Short filmId);
}
