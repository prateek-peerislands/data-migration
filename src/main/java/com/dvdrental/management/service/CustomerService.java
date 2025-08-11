package com.dvdrental.management.service;

import com.dvdrental.management.dto.CustomerDTO;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<CustomerDTO> getAllCustomers();
    Optional<CustomerDTO> getCustomerById(Integer id);
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CustomerDTO updateCustomer(Integer id, CustomerDTO customerDTO);
    void deleteCustomer(Integer id);
    List<CustomerDTO> searchCustomersByName(String name);
    List<CustomerDTO> getCustomersByStore(Short storeId);
    Optional<CustomerDTO> getCustomerByEmail(String email);
}
