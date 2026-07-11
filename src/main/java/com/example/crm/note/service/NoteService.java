package com.example.crm.note.service;

import com.example.crm.common.exception.ResourceNotFoundException;
import com.example.crm.customer.entity.Customer;
import com.example.crm.customer.repository.CustomerRepository;
import com.example.crm.lead.entity.Lead;
import com.example.crm.lead.repository.LeadRepository;
import com.example.crm.note.dto.NoteRequest;
import com.example.crm.note.entity.Note;
import com.example.crm.note.repository.NoteRepository;
import com.example.crm.user.entity.User;
import com.example.crm.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.crm.note.dto.NoteResponse;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final CustomerRepository customerRepository;
    private final LeadRepository leadRepository;
    private final UserRepository userRepository;

    public NoteService(
            NoteRepository noteRepository,
            CustomerRepository customerRepository,
            LeadRepository leadRepository,
            UserRepository userRepository) {

        this.noteRepository = noteRepository;
        this.customerRepository = customerRepository;
        this.leadRepository = leadRepository;
        this.userRepository = userRepository;
    }

    public NoteResponse createNote(NoteRequest request) {

        Customer customer = customerRepository.findById(
                        request.getCustomerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found"));

        Lead lead = leadRepository.findById(
                        request.getLeadId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Lead not found"));

        User user = userRepository.findById(
                        request.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Note note = new Note();

        note.setNoteText(request.getNoteText());
        note.setCustomer(customer);
        note.setLead(lead);
        note.setCreatedBy(user);

        Note savedNote = noteRepository.save(note);

        return mapToResponse(savedNote);
    }

    public List<NoteResponse> getCustomerNotes(Long customerId) {
        return noteRepository.findByCustomerId(customerId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<NoteResponse> getLeadNotes(Long leadId) {
        return noteRepository.findByLeadId(leadId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public Page<Note> getNotes(Pageable pageable) {
        return noteRepository.findAll(pageable);
    }

    private NoteResponse mapToResponse(Note note) {

        NoteResponse response = new NoteResponse();

        response.setId(note.getId());
        response.setNoteText(note.getNoteText());
        response.setCreatedAt(note.getCreatedAt());

        if (note.getCustomer() != null) {
            response.setCustomerName(
                    note.getCustomer().getCompanyName());
        }

        if (note.getLead() != null) {
            response.setLeadTitle(
                    note.getLead().getTitle());
        }

        if (note.getCreatedBy() != null) {
            response.setCreatedBy(
                    note.getCreatedBy().getName());
        }

        return response;
    }
}