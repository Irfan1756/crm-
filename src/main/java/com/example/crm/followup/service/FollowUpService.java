package com.example.crm.followup.service;

import com.example.crm.common.exception.ResourceNotFoundException;
import com.example.crm.followup.dto.FollowUpRequest;
import com.example.crm.followup.entity.FollowUp;
import com.example.crm.followup.entity.FollowUpStatus;
import com.example.crm.followup.repository.FollowUpRepository;
import com.example.crm.lead.entity.Lead;
import com.example.crm.lead.repository.LeadRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowUpService {

    private final FollowUpRepository followUpRepository;
    private final LeadRepository leadRepository;

    public FollowUpService(
            FollowUpRepository followUpRepository,
            LeadRepository leadRepository) {

        this.followUpRepository = followUpRepository;
        this.leadRepository = leadRepository;
    }

    public FollowUp createFollowUp(
            FollowUpRequest request) {

        Lead lead = leadRepository.findById(
                        request.getLeadId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Lead not found"));

        FollowUp followUp = new FollowUp();

        followUp.setNotes(request.getNotes());
        followUp.setFollowUpDate(
                request.getFollowUpDate());
        followUp.setStatus(request.getStatus());
        followUp.setLead(lead);

        return followUpRepository.save(followUp);
    }

    public List<FollowUp> getAllFollowUps() {
        return followUpRepository.findAll();
    }

    public FollowUp completeFollowUp(Long id) {

        FollowUp followUp =
                followUpRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Follow-up not found"));

        followUp.setStatus(
                FollowUpStatus.COMPLETED);

        return followUpRepository.save(followUp);
    }
}