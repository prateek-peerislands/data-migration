package com.dvdrental.management.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer")
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer customerId;
    
    @Column(name = "store_id", nullable = false)
    private Short storeId;
    
    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;
    
    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;
    
    @Column(name = "email", length = 50)
    private String email;
    
    @Column(name = "address_id", nullable = false)
    private Short addressId;
    
    @Column(name = "activebool", nullable = false)
    private Boolean activebool = true;
    
    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;
    
    @Column(name = "last_update")
    private LocalDateTime lastUpdate;
    
    @Column(name = "active")
    private Integer active;
    
    // JPA relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", insertable = false, updatable = false)
    private Address address;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", insertable = false, updatable = false)
    private Store store;
    
    // Constructors
    public Customer() {
    }
    
    public Customer(Short storeId, String firstName, String lastName, Short addressId) {
        this.storeId = storeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressId = addressId;
        this.createDate = LocalDate.now();
        this.lastUpdate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Integer getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
    
    public Short getStoreId() {
        return storeId;
    }
    
    public void setStoreId(Short storeId) {
        this.storeId = storeId;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Short getAddressId() {
        return addressId;
    }
    
    public void setAddressId(Short addressId) {
        this.addressId = addressId;
    }
    
    public Boolean getActivebool() {
        return activebool;
    }
    
    public void setActivebool(Boolean activebool) {
        this.activebool = activebool;
    }
    
    public LocalDate getCreateDate() {
        return createDate;
    }
    
    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }
    
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }
    
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    
    public Integer getActive() {
        return active;
    }
    
    public void setActive(Integer active) {
        this.active = active;
    }
    
    public Address getAddress() {
        return address;
    }
    
    public void setAddress(Address address) {
        this.address = address;
    }
    
    public Store getStore() {
        return store;
    }
    
    public void setStore(Store store) {
        this.store = store;
    }
    
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.lastUpdate = LocalDateTime.now();
    }
}
