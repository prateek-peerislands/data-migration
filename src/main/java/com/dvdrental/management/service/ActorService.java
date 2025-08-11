package com.dvdrental.management.service;

import com.dvdrental.management.dto.ActorDTO;

import java.util.List;
import java.util.Optional;

public interface ActorService {
    List<ActorDTO> getAllActors();
    Optional<ActorDTO> getActorById(Integer id);
    ActorDTO saveActor(ActorDTO actorDTO);
    ActorDTO updateActor(Integer id, ActorDTO actorDTO);
    void deleteActor(Integer id);
    
    List<ActorDTO> searchActorsByName(String name);
}
