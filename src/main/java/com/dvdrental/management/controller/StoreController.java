package com.dvdrental.management.controller;

import com.dvdrental.management.dto.StoreDTO;
import com.dvdrental.management.entity.Store;
import com.dvdrental.management.repository.StoreRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stores")
@CrossOrigin(origins = "*")
public class StoreController {

    private final StoreRepository storeRepository;

    public StoreController(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @GetMapping
    public ResponseEntity<List<StoreDTO>> getAllStores() {
        List<StoreDTO> stores = storeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(stores, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreDTO> getStoreById(@PathVariable Integer id) {
        Optional<StoreDTO> store = storeRepository.findById(id).map(this::convertToDTO);
        return store.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<StoreDTO> createStore(@RequestBody StoreDTO storeDTO) {
        try {
            Store store = convertToEntity(storeDTO);
            Store savedStore = storeRepository.save(store);
            return new ResponseEntity<>(convertToDTO(savedStore), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private StoreDTO convertToDTO(Store store) {
        return new StoreDTO(
                store.getStoreId(),
                store.getManagerStaffId(),
                store.getAddressId(),
                store.getLastUpdate()
        );
    }

    private Store convertToEntity(StoreDTO storeDTO) {
        Store store = new Store();
        store.setManagerStaffId(storeDTO.managerStaffId());
        store.setAddressId(storeDTO.addressId());
        store.setLastUpdate(LocalDateTime.now());
        return store;
    }
}
