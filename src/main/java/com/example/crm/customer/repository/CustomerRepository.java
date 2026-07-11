package com.example.crm.customer.repository;

import com.example.crm.customer.entity.Customer;
import com.example.crm.customer.entity.CustomerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository
        extends JpaRepository<Customer, Long> {

    List<Customer> findByCompanyNameContaining(
            String companyName);

    List<Customer> findByStatus(
            CustomerStatus status);

    Page<Customer> findByStatus(
            CustomerStatus status,
            Pageable pageable);
}