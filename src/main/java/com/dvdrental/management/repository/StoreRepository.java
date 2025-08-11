package com.dvdrental.management.repository;

import com.dvdrental.management.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Integer> {
    List<Store> findByManagerStaffId(Short managerStaffId);
    List<Store> findByAddressId(Short addressId);
}
