package com.example.crm.lead.controller;

import com.example.crm.lead.dto.LeadRequest;
import com.example.crm.lead.entity.Lead;
import com.example.crm.lead.entity.LeadStatus;
import com.example.crm.lead.service.LeadService;
import com.example.crm.lead.dto.LeadResponse;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leads")
@CrossOrigin(origins = "*")
public class LeadController {

    private final LeadService leadService;

    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    @PostMapping
    public LeadResponse createLead(@RequestBody LeadRequest request) {
        return leadService.createLead(request);
    }

    @GetMapping
    public List<LeadResponse> getAllLeads() {
        return leadService.getAllLeads();
    }

    @GetMapping("/{id}")
    public LeadResponse getLeadById(@PathVariable Long id) {
        return leadService.getLeadById(id);
    }

    @PutMapping("/{id}")
    public LeadResponse updateLeadStatus(
            @PathVariable Long id,
            @RequestBody LeadRequest request) {

        return leadService.updateLeadStatus(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteLead(@PathVariable Long id) {
        leadService.deleteLead(id);
        return "Lead deleted successfully";
    }

    @GetMapping("/status")
    public List<Lead> getByStatus(
            @RequestParam String status){

        return leadService.getByStatus(
                LeadStatus.valueOf(status));
    }

    @GetMapping("/user/{id}")
    public List<Lead> getByUser(
            @PathVariable Long id){

        return leadService.getByUser(id);
    }

    @GetMapping("/customer/{id}")
    public List<Lead> getByCustomer(
            @PathVariable Long id){

        return leadService.getByCustomer(id);
    }
}