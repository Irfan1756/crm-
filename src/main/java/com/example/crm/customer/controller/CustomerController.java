package com.example.crm.customer.controller;

import com.example.crm.customer.dto.CustomerRequest;
import com.example.crm.customer.dto.CustomerResponse;
import com.example.crm.customer.entity.Customer;
import com.example.crm.customer.entity.CustomerStatus;
import com.example.crm.customer.service.CustomerService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public CustomerResponse createCustomer(
            @Valid @RequestBody CustomerRequest request) {

        return customerService.createCustomer(request);
    }

    @GetMapping
    public Page<CustomerResponse> getCustomers(
            Pageable pageable) {

        return customerService.getCustomers(pageable);
    }

    @GetMapping("/{id}")
    public CustomerResponse getCustomerById(
            @PathVariable Long id) {

        return customerService.getCustomerById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(
            @PathVariable Long id) {

        customerService.deleteCustomer(id);

        return "Customer deleted successfully";
    }

    @GetMapping("/search/company")
    public List<CustomerResponse> searchByCompanyName(
            @RequestParam String companyName) {

        return customerService
                .searchByCompanyName(companyName);
    }

    @PutMapping("/{id}")
    public CustomerResponse updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerRequest request) {

        return customerService.updateCustomer(id, request);
    }

    @GetMapping("/status")
    public Page<CustomerResponse> getByStatus(
            @RequestParam CustomerStatus status,
            Pageable pageable) {

        return customerService
                .getCustomersByStatus(
                        status,
                        pageable);
    }
}