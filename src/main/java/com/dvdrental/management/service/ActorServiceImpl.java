package com.dvdrental.management.service;

import com.dvdrental.management.dto.ActorDTO;
import com.dvdrental.management.entity.Actor;
import com.dvdrental.management.repository.ActorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;

    public ActorServiceImpl(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @Override
    public List<ActorDTO> getAllActors() {
        return actorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ActorDTO> getActorById(Integer id) {
        return actorRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public ActorDTO saveActor(ActorDTO actorDTO) {
        Actor actor = convertToEntity(actorDTO);
        Actor savedActor = actorRepository.save(actor);
        return convertToDTO(savedActor);
    }

    @Override
    public ActorDTO updateActor(Integer id, ActorDTO actorDTO) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actor not found with id: " + id));
        
        actor.setFirstName(actorDTO.firstName());
        actor.setLastName(actorDTO.lastName());
        
        Actor updatedActor = actorRepository.save(actor);
        return convertToDTO(updatedActor);
    }

    @Override
    public void deleteActor(Integer id) {
        if (!actorRepository.existsById(id)) {
            throw new RuntimeException("Actor not found with id: " + id);
        }
        actorRepository.deleteById(id);
    }

    @Override
    public List<ActorDTO> searchActorsByName(String name) {
        return actorRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ActorDTO convertToDTO(Actor actor) {
        return new ActorDTO(
                actor.getActorId(),
                actor.getFirstName(),
                actor.getLastName(),
                actor.getLastUpdate()
        );
    }

    private Actor convertToEntity(ActorDTO actorDTO) {
        Actor actor = new Actor();
        actor.setFirstName(actorDTO.firstName());
        actor.setLastName(actorDTO.lastName());
        actor.setLastUpdate(LocalDateTime.now());
        return actor;
    }
}
