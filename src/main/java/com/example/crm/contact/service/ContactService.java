package com.example.crm.contact.service;

import com.example.crm.common.exception.ResourceNotFoundException;
import com.example.crm.contact.dto.ContactRequest;
import com.example.crm.contact.dto.ContactResponse;
import com.example.crm.contact.entity.Contact;
import com.example.crm.contact.repository.ContactRepository;
import com.example.crm.customer.entity.Customer;
import com.example.crm.customer.repository.CustomerRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final CustomerRepository customerRepository;

    public ContactService(
            ContactRepository contactRepository,
            CustomerRepository customerRepository) {

        this.contactRepository = contactRepository;
        this.customerRepository = customerRepository;
    }

    public ContactResponse createContact(
            ContactRequest request) {

        Customer customer =
                customerRepository.findById(
                                request.getCustomerId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Customer not found"));

        Contact contact = new Contact();

        contact.setCustomer(customer);
        contact.setName(request.getName());
        contact.setEmail(request.getEmail());
        contact.setPhone(request.getPhone());
        contact.setDesignation(
                request.getDesignation());
        contact.setPrimaryContact(
                request.getPrimaryContact());

        Contact savedContact =
                contactRepository.save(contact);

        return mapToResponse(savedContact);
    }

    public List<ContactResponse> getAllContacts() {

        return contactRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<ContactResponse> getContactsByCustomer(
            Long customerId) {

        return contactRepository
                .findByCustomerId(customerId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ContactResponse mapToResponse(Contact contact){

        ContactResponse response =
                new ContactResponse();

        response.setId(contact.getId());
        response.setName(contact.getName());
        response.setEmail(contact.getEmail());
        response.setPhone(contact.getPhone());
        response.setDesignation(contact.getDesignation());
        response.setPrimaryContact(contact.getPrimaryContact());

        if(contact.getCustomer()!=null){
            response.setCustomerName(
                    contact.getCustomer()
                            .getCompanyName());
        }

        return response;
    }
}