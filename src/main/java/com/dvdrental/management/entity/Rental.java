package com.dvdrental.management.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rental")
public class Rental {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    private Integer rentalId;
    
    @Column(name = "rental_date", nullable = false)
    private LocalDateTime rentalDate;
    
    @Column(name = "inventory_id", nullable = false)
    private Integer inventoryId;
    
    @Column(name = "customer_id", nullable = false)
    private Short customerId;
    
    @Column(name = "return_date")
    private LocalDateTime returnDate;
    
    @Column(name = "staff_id", nullable = false)
    private Short staffId;
    
    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;
    
    // JPA relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id", insertable = false, updatable = false)
    private Inventory inventory;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private Customer customer;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id", insertable = false, updatable = false)
    private Staff staff;
    
    // Constructors
    public Rental() {
    }
    
    public Rental(LocalDateTime rentalDate, Integer inventoryId, Short customerId, Short staffId) {
        this.rentalDate = rentalDate;
        this.inventoryId = inventoryId;
        this.customerId = customerId;
        this.staffId = staffId;
        this.lastUpdate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Integer getRentalId() {
        return rentalId;
    }
    
    public void setRentalId(Integer rentalId) {
        this.rentalId = rentalId;
    }
    
    public LocalDateTime getRentalDate() {
        return rentalDate;
    }
    
    public void setRentalDate(LocalDateTime rentalDate) {
        this.rentalDate = rentalDate;
    }
    
    public Integer getInventoryId() {
        return inventoryId;
    }
    
    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }
    
    public Short getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(Short customerId) {
        this.customerId = customerId;
    }
    
    public LocalDateTime getReturnDate() {
        return returnDate;
    }
    
    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }
    
    public Short getStaffId() {
        return staffId;
    }
    
    public void setStaffId(Short staffId) {
        this.staffId = staffId;
    }
    
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }
    
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    
    public Inventory getInventory() {
        return inventory;
    }
    
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public Staff getStaff() {
        return staff;
    }
    
    public void setStaff(Staff staff) {
        this.staff = staff;
    }
    
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.lastUpdate = LocalDateTime.now();
    }
}
