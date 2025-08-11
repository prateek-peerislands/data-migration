package com.dvdrental.management.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "staff")
public class Staff {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    private Integer staffId;
    
    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;
    
    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;
    
    @Column(name = "address_id", nullable = false)
    private Short addressId;
    
    @Column(name = "email", length = 50)
    private String email;
    
    @Column(name = "store_id", nullable = false)
    private Short storeId;
    
    @Column(name = "active", nullable = false)
    private Boolean active = true;
    
    @Column(name = "username", nullable = false, length = 16)
    private String username;
    
    @Column(name = "password", length = 40)
    private String password;
    
    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;
    
    @Column(name = "picture", columnDefinition = "bytea")
    private byte[] picture;
    
    // Constructors
    public Staff() {
    }
    
    public Staff(String firstName, String lastName, Short addressId, Short storeId, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressId = addressId;
        this.storeId = storeId;
        this.username = username;
        this.lastUpdate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Integer getStaffId() {
        return staffId;
    }
    
    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
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
    
    public Short getAddressId() {
        return addressId;
    }
    
    public void setAddressId(Short addressId) {
        this.addressId = addressId;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Short getStoreId() {
        return storeId;
    }
    
    public void setStoreId(Short storeId) {
        this.storeId = storeId;
    }
    
    public Boolean getActive() {
        return active;
    }
    
    public void setActive(Boolean active) {
        this.active = active;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }
    
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    
    public byte[] getPicture() {
        return picture;
    }
    
    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
    
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.lastUpdate = LocalDateTime.now();
    }
}
