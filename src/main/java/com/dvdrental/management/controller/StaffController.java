package com.dvdrental.management.controller;

import com.dvdrental.management.dto.StaffDTO;
import com.dvdrental.management.entity.Staff;
import com.dvdrental.management.repository.StaffRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/staff")
@CrossOrigin(origins = "*")
public class StaffController {

    private final StaffRepository staffRepository;

    public StaffController(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @GetMapping
    public ResponseEntity<List<StaffDTO>> getAllStaff() {
        List<StaffDTO> staff = staffRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(staff, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StaffDTO> getStaffById(@PathVariable Integer id) {
        Optional<StaffDTO> staff = staffRepository.findById(id).map(this::convertToDTO);
        return staff.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<StaffDTO> createStaff(@RequestBody StaffDTO staffDTO) {
        try {
            Staff staff = convertToEntity(staffDTO);
            Staff savedStaff = staffRepository.save(staff);
            return new ResponseEntity<>(convertToDTO(savedStaff), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<StaffDTO> updateStaff(@PathVariable Integer id, @RequestBody StaffDTO staffDTO) {
        try {
            Staff staff = staffRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Staff not found"));
            staff.setFirstName(staffDTO.firstName());
            staff.setLastName(staffDTO.lastName());
            staff.setAddressId(staffDTO.addressId());
            staff.setEmail(staffDTO.email());
            staff.setStoreId(staffDTO.storeId());
            staff.setActive(staffDTO.active());
            staff.setUsername(staffDTO.username());
            Staff updatedStaff = staffRepository.save(staff);
            return new ResponseEntity<>(convertToDTO(updatedStaff), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<StaffDTO>> getStaffByStore(@PathVariable Short storeId) {
        List<StaffDTO> staff = staffRepository.findByStoreId(storeId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(staff, HttpStatus.OK);
    }

    private StaffDTO convertToDTO(Staff staff) {
        return new StaffDTO(
                staff.getStaffId(),
                staff.getFirstName(),
                staff.getLastName(),
                staff.getAddressId(),
                staff.getEmail(),
                staff.getStoreId(),
                staff.getActive(),
                staff.getUsername(),
                null, // Don't expose password
                staff.getLastUpdate(),
                staff.getPicture()
        );
    }

    private Staff convertToEntity(StaffDTO staffDTO) {
        Staff staff = new Staff();
        staff.setFirstName(staffDTO.firstName());
        staff.setLastName(staffDTO.lastName());
        staff.setAddressId(staffDTO.addressId());
        staff.setEmail(staffDTO.email());
        staff.setStoreId(staffDTO.storeId());
        staff.setActive(staffDTO.active());
        staff.setUsername(staffDTO.username());
        staff.setPassword(staffDTO.password());
        staff.setPicture(staffDTO.picture());
        staff.setLastUpdate(LocalDateTime.now());
        return staff;
    }
}
