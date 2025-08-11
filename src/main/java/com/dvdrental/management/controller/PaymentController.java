package com.dvdrental.management.controller;

import com.dvdrental.management.dto.PaymentDTO;
import com.dvdrental.management.entity.Payment;
import com.dvdrental.management.repository.PaymentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PaymentRepository paymentRepository;

    public PaymentController(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @GetMapping
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        List<PaymentDTO> payments = paymentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Integer id) {
        Optional<PaymentDTO> payment = paymentRepository.findById(id).map(this::convertToDTO);
        return payment.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody PaymentDTO paymentDTO) {
        try {
            Payment payment = convertToEntity(paymentDTO);
            Payment savedPayment = paymentRepository.save(payment);
            return new ResponseEntity<>(convertToDTO(savedPayment), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByCustomer(@PathVariable Short customerId) {
        List<PaymentDTO> payments = paymentRepository.findByCustomerId(customerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}/total")
    public ResponseEntity<BigDecimal> getTotalPaymentsByCustomer(@PathVariable Short customerId) {
        BigDecimal total = paymentRepository.getTotalPaymentsByCustomer(customerId);
        return new ResponseEntity<>(total != null ? total : BigDecimal.ZERO, HttpStatus.OK);
    }

    private PaymentDTO convertToDTO(Payment payment) {
        return new PaymentDTO(
                payment.getPaymentId(),
                payment.getCustomerId(),
                payment.getStaffId(),
                payment.getRentalId(),
                payment.getAmount(),
                payment.getPaymentDate()
        );
    }

    private Payment convertToEntity(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setCustomerId(paymentDTO.customerId());
        payment.setStaffId(paymentDTO.staffId());
        payment.setRentalId(paymentDTO.rentalId());
        payment.setAmount(paymentDTO.amount());
        payment.setPaymentDate(paymentDTO.paymentDate());
        return payment;
    }
}
