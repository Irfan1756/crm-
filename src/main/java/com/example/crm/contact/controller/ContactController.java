package com.example.crm.contact.controller;

import com.example.crm.contact.dto.ContactRequest;
import com.example.crm.contact.dto.ContactResponse;
import com.example.crm.contact.entity.Contact;
import com.example.crm.contact.service.ContactService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@CrossOrigin(origins = "*")
public class ContactController {

    private final ContactService contactService;

    public ContactController(
            ContactService contactService) {

        this.contactService = contactService;
    }

    @PostMapping
    public ContactResponse createContact(
            @RequestBody ContactRequest request) {

        return contactService
                .createContact(request);
    }

    @GetMapping
    public List<ContactResponse> getAllContacts() {

        return contactService
                .getAllContacts();
    }

    @GetMapping("/customer/{customerId}")
    public List<ContactResponse> getContactsByCustomer(
            @PathVariable Long customerId) {

        return contactService
                .getContactsByCustomer(
                        customerId);
    }
}