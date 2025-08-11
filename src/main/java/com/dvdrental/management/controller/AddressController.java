package com.dvdrental.management.controller;

import com.dvdrental.management.dto.AddressDTO;
import com.dvdrental.management.entity.Address;
import com.dvdrental.management.repository.AddressRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/addresses")
@CrossOrigin(origins = "*")
public class AddressController {

    private final AddressRepository addressRepository;

    public AddressController(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @GetMapping
    public ResponseEntity<List<AddressDTO>> getAllAddresses() {
        List<AddressDTO> addresses = addressRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Integer id) {
        Optional<AddressDTO> address = addressRepository.findById(id).map(this::convertToDTO);
        return address.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<AddressDTO> createAddress(@RequestBody AddressDTO addressDTO) {
        try {
            Address address = convertToEntity(addressDTO);
            Address savedAddress = addressRepository.save(address);
            return new ResponseEntity<>(convertToDTO(savedAddress), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/city/{cityId}")
    public ResponseEntity<List<AddressDTO>> getAddressesByCity(@PathVariable Short cityId) {
        List<AddressDTO> addresses = addressRepository.findByCityId(cityId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    private AddressDTO convertToDTO(Address address) {
        return new AddressDTO(
                address.getAddressId(),
                address.getAddress(),
                address.getAddress2(),
                address.getDistrict(),
                address.getCityId(),
                address.getPostalCode(),
                address.getPhone(),
                address.getLastUpdate()
        );
    }

    private Address convertToEntity(AddressDTO addressDTO) {
        Address address = new Address();
        address.setAddress(addressDTO.address());
        address.setAddress2(addressDTO.address2());
        address.setDistrict(addressDTO.district());
        address.setCityId(addressDTO.cityId());
        address.setPostalCode(addressDTO.postalCode());
        address.setPhone(addressDTO.phone());
        address.setLastUpdate(LocalDateTime.now());
        return address;
    }
}
