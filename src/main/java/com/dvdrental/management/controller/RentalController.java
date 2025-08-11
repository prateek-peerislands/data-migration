package com.dvdrental.management.controller;

import com.dvdrental.management.dto.RentalDTO;
import com.dvdrental.management.entity.Rental;
import com.dvdrental.management.repository.RentalRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rentals")
@CrossOrigin(origins = "*")
public class RentalController {

    private final RentalRepository rentalRepository;

    public RentalController(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    @GetMapping
    public ResponseEntity<List<RentalDTO>> getAllRentals() {
        List<RentalDTO> rentals = rentalRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalDTO> getRentalById(@PathVariable Integer id) {
        Optional<RentalDTO> rental = rentalRepository.findById(id).map(this::convertToDTO);
        return rental.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<RentalDTO> createRental(@RequestBody RentalDTO rentalDTO) {
        try {
            Rental rental = convertToEntity(rentalDTO);
            Rental savedRental = rentalRepository.save(rental);
            return new ResponseEntity<>(convertToDTO(savedRental), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentalDTO> updateRental(@PathVariable Integer id, @RequestBody RentalDTO rentalDTO) {
        try {
            Rental rental = rentalRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Rental not found"));
            rental.setRentalDate(rentalDTO.rentalDate());
            rental.setInventoryId(rentalDTO.inventoryId());
            rental.setCustomerId(rentalDTO.customerId());
            rental.setReturnDate(rentalDTO.returnDate());
            rental.setStaffId(rentalDTO.staffId());
            Rental updatedRental = rentalRepository.save(rental);
            return new ResponseEntity<>(convertToDTO(updatedRental), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Integer id) {
        try {
            rentalRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<RentalDTO>> getRentalsByCustomer(@PathVariable Short customerId) {
        List<RentalDTO> rentals = rentalRepository.findByCustomerId(customerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    @GetMapping("/active/{customerId}")
    public ResponseEntity<List<RentalDTO>> getActiveRentalsByCustomer(@PathVariable Short customerId) {
        List<RentalDTO> rentals = rentalRepository.findActiveRentalsByCustomer(customerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    private RentalDTO convertToDTO(Rental rental) {
        return new RentalDTO(
                rental.getRentalId(),
                rental.getRentalDate(),
                rental.getInventoryId(),
                rental.getCustomerId(),
                rental.getReturnDate(),
                rental.getStaffId(),
                rental.getLastUpdate()
        );
    }

    private Rental convertToEntity(RentalDTO rentalDTO) {
        Rental rental = new Rental();
        rental.setRentalDate(rentalDTO.rentalDate());
        rental.setInventoryId(rentalDTO.inventoryId());
        rental.setCustomerId(rentalDTO.customerId());
        rental.setReturnDate(rentalDTO.returnDate());
        rental.setStaffId(rentalDTO.staffId());
        rental.setLastUpdate(LocalDateTime.now());
        return rental;
    }
}
