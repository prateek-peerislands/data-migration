package com.dvdrental.management.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "address")
public class Address {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer addressId;
    
    @Column(name = "address", nullable = false, length = 50)
    private String address;
    
    @Column(name = "address2", length = 50)
    private String address2;
    
    @Column(name = "district", nullable = false, length = 20)
    private String district;
    
    @Column(name = "city_id", nullable = false)
    private Short cityId;
    
    @Column(name = "postal_code", length = 10)
    private String postalCode;
    
    @Column(name = "phone", nullable = false, length = 20)
    private String phone;
    
    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;
    
    // JPA relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", insertable = false, updatable = false)
    private City city;
    
    // Constructors
    public Address() {
    }
    
    public Address(String address, String district, Short cityId, String phone) {
        this.address = address;
        this.district = district;
        this.cityId = cityId;
        this.phone = phone;
        this.lastUpdate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Integer getAddressId() {
        return addressId;
    }
    
    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getAddress2() {
        return address2;
    }
    
    public void setAddress2(String address2) {
        this.address2 = address2;
    }
    
    public String getDistrict() {
        return district;
    }
    
    public void setDistrict(String district) {
        this.district = district;
    }
    
    public Short getCityId() {
        return cityId;
    }
    
    public void setCityId(Short cityId) {
        this.cityId = cityId;
    }
    
    public String getPostalCode() {
        return postalCode;
    }
    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }
    
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    
    public City getCity() {
        return city;
    }
    
    public void setCity(City city) {
        this.city = city;
    }
    
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.lastUpdate = LocalDateTime.now();
    }
}
