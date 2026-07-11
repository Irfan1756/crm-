package com.example.crm.lead.service;

import com.example.crm.common.exception.ResourceNotFoundException;
import com.example.crm.customer.entity.Customer;
import com.example.crm.customer.repository.CustomerRepository;
import com.example.crm.lead.dto.LeadRequest;
import com.example.crm.lead.entity.Lead;
import com.example.crm.lead.entity.LeadSource;
import com.example.crm.lead.entity.LeadStatus;
import com.example.crm.lead.repository.LeadRepository;
import com.example.crm.user.entity.User;
import com.example.crm.user.repository.UserRepository;
import com.example.crm.lead.dto.LeadResponse;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeadService {

    private final LeadRepository leadRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public LeadService(
            LeadRepository leadRepository,
            CustomerRepository customerRepository,
            UserRepository userRepository) {

        this.leadRepository = leadRepository;
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }

    public LeadResponse createLead(LeadRequest request) {

        Customer customer = customerRepository.findById(
                        request.getCustomerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found"));

        User user = userRepository.findById(
                        request.getAssignedUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Lead lead = new Lead();

        lead.setTitle(request.getTitle());
        lead.setDescription(request.getDescription());
        lead.setSource(LeadSource.valueOf(request.getSource()));
        lead.setStatus(LeadStatus.valueOf(request.getStatus()));
        lead.setExpectedValue(request.getExpectedValue());
        lead.setCustomer(customer);
        lead.setAssignedTo(user);

        Lead savedLead = leadRepository.save(lead);

        return mapToResponse(savedLead);
    }

    public List<LeadResponse> getAllLeads() {

        return leadRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public LeadResponse getLeadById(Long id) {

        Lead lead = leadRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Lead not found"));

        return mapToResponse(lead);
    }

    public LeadResponse updateLeadStatus(Long id, LeadRequest request) {

        Lead lead = leadRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Lead not found"));

        lead.setStatus(
                LeadStatus.valueOf(request.getStatus()));

        Lead updatedLead = leadRepository.save(lead);

        return mapToResponse(updatedLead);
    }

    public void deleteLead(Long id) {
        leadRepository.deleteById(id);
    }

    public List<Lead> getByStatus(
            LeadStatus status) {

        return leadRepository.findByStatus(status);
    }

    public List<Lead> getByUser(
            Long userId) {

        return leadRepository.findByAssignedToId(userId);
    }

    public List<Lead> getByCustomer(
            Long customerId) {

        return leadRepository.findByCustomerId(customerId);
    }

    private LeadResponse mapToResponse(Lead lead) {

        LeadResponse response = new LeadResponse();

        response.setId(lead.getId());
        response.setTitle(lead.getTitle());
        response.setDescription(lead.getDescription());
        response.setSource(lead.getSource().name());
        response.setStatus(lead.getStatus().name());
        response.setExpectedValue(lead.getExpectedValue());

        if (lead.getCustomer() != null) {
            response.setCustomerName(
                    lead.getCustomer().getCompanyName());
        }

        if (lead.getAssignedTo() != null) {
            response.setAssignedUser(
                    lead.getAssignedTo().getName());
        }

        return response;
    }
}