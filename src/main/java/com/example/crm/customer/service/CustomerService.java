package com.example.crm.customer.service;

import com.example.crm.common.exception.ResourceNotFoundException;
import com.example.crm.customer.dto.CustomerRequest;
import com.example.crm.customer.dto.CustomerResponse;
import com.example.crm.customer.entity.Customer;
import com.example.crm.customer.entity.CustomerStatus;
import com.example.crm.customer.repository.CustomerRepository;
import com.example.crm.user.entity.User;
import com.example.crm.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public CustomerService(
            CustomerRepository customerRepository,
            UserRepository userRepository) {

        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }

    public CustomerResponse createCustomer(CustomerRequest request) {

        User user = userRepository.findById(
                        request.getAssignedUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        Customer customer = new Customer();

        customer.setCompanyName(
                request.getCompanyName());

        customer.setIndustry(
                request.getIndustry());

        customer.setWebsite(
                request.getWebsite());

        customer.setAddress(
                request.getAddress());

        customer.setStatus(
                CustomerStatus.valueOf(
                        request.getStatus()));

        customer.setAssignedUser(user);

        Customer savedCustomer =
                customerRepository.save(customer);

        return mapToResponse(savedCustomer);
    }

    public List<CustomerResponse> getAllCustomers() {

        return customerRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public CustomerResponse getCustomerById(Long id) {

        Customer customer =
                customerRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Customer not found with id " + id));

        return mapToResponse(customer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public CustomerResponse updateCustomer(
            Long id,
            CustomerRequest request) {

        Customer customer = customerRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with id " + id));

        User user = userRepository.findById(
                        request.getAssignedUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        customer.setCompanyName(
                request.getCompanyName());

        customer.setIndustry(
                request.getIndustry());

        customer.setWebsite(
                request.getWebsite());

        customer.setAddress(
                request.getAddress());

        customer.setStatus(
                CustomerStatus.valueOf(
                        request.getStatus()));

        customer.setAssignedUser(user);

        Customer updatedCustomer =
                customerRepository.save(customer);

        return mapToResponse(updatedCustomer);
    }

    public List<CustomerResponse> searchByCompanyName(
            String companyName) {

        return customerRepository
                .findByCompanyNameContaining(companyName)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public Page<CustomerResponse> getCustomersByStatus(
            CustomerStatus status,
            Pageable pageable) {

        return customerRepository
                .findByStatus(status, pageable)
                .map(this::mapToResponse);
    }

    public Page<CustomerResponse> getCustomers(
            Pageable pageable){

        return customerRepository
                .findAll(pageable)
                .map(this::mapToResponse);
    }

    private CustomerResponse mapToResponse(Customer customer){

        CustomerResponse response =
                new CustomerResponse();

        response.setId(customer.getId());

        response.setCompanyName(
                customer.getCompanyName());

        response.setIndustry(
                customer.getIndustry());

        response.setWebsite(
                customer.getWebsite());

        response.setAddress(
                customer.getAddress());

        if (customer.getStatus() != null) {
            response.setStatus(
                    customer.getStatus().name());
        }

        if(customer.getAssignedUser()!=null){

            response.setAssignedUser(
                    customer.getAssignedUser()
                            .getName());
        }

        return response;
    }
}