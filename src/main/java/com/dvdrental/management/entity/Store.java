package com.dvdrental.management.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "store")
public class Store {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Integer storeId;
    
    @Column(name = "manager_staff_id", nullable = false)
    private Short managerStaffId;
    
    @Column(name = "address_id", nullable = false)
    private Short addressId;
    
    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;
    
    // JPA relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", insertable = false, updatable = false)
    private Address address;
    
    // Constructors
    public Store() {
    }
    
    public Store(Short managerStaffId, Short addressId) {
        this.managerStaffId = managerStaffId;
        this.addressId = addressId;
        this.lastUpdate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Integer getStoreId() {
        return storeId;
    }
    
    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }
    
    public Short getManagerStaffId() {
        return managerStaffId;
    }
    
    public void setManagerStaffId(Short managerStaffId) {
        this.managerStaffId = managerStaffId;
    }
    
    public Short getAddressId() {
        return addressId;
    }
    
    public void setAddressId(Short addressId) {
        this.addressId = addressId;
    }
    
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }
    
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    
    public Address getAddress() {
        return address;
    }
    
    public void setAddress(Address address) {
        this.address = address;
    }
    
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.lastUpdate = LocalDateTime.now();
    }
}
