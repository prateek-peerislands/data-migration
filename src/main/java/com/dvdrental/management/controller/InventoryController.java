package com.dvdrental.management.controller;

import com.dvdrental.management.dto.InventoryDTO;
import com.dvdrental.management.entity.Inventory;
import com.dvdrental.management.repository.InventoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "*")
public class InventoryController {

    private final InventoryRepository inventoryRepository;

    public InventoryController(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @GetMapping
    public ResponseEntity<List<InventoryDTO>> getAllInventory() {
        List<InventoryDTO> inventory = inventoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryDTO> getInventoryById(@PathVariable Integer id) {
        Optional<InventoryDTO> inventory = inventoryRepository.findById(id).map(this::convertToDTO);
        return inventory.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<InventoryDTO> createInventory(@RequestBody InventoryDTO inventoryDTO) {
        try {
            Inventory inventory = convertToEntity(inventoryDTO);
            Inventory savedInventory = inventoryRepository.save(inventory);
            return new ResponseEntity<>(convertToDTO(savedInventory), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/film/{filmId}")
    public ResponseEntity<List<InventoryDTO>> getInventoryByFilm(@PathVariable Short filmId) {
        List<InventoryDTO> inventory = inventoryRepository.findByFilmId(filmId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<InventoryDTO>> getInventoryByStore(@PathVariable Short storeId) {
        List<InventoryDTO> inventory = inventoryRepository.findByStoreId(storeId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }

    private InventoryDTO convertToDTO(Inventory inventory) {
        return new InventoryDTO(
                inventory.getInventoryId(),
                inventory.getFilmId(),
                inventory.getStoreId(),
                inventory.getLastUpdate()
        );
    }

    private Inventory convertToEntity(InventoryDTO inventoryDTO) {
        Inventory inventory = new Inventory();
        inventory.setFilmId(inventoryDTO.filmId());
        inventory.setStoreId(inventoryDTO.storeId());
        inventory.setLastUpdate(LocalDateTime.now());
        return inventory;
    }
}
