package com.example.crm.followup.dto;

import com.example.crm.followup.entity.FollowUpStatus;

import java.time.LocalDate;

public class FollowUpRequest {

    private String notes;

    private LocalDate followUpDate;

    private FollowUpStatus status;

    private Long leadId;

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getFollowUpDate() {
        return followUpDate;
    }

    public void setFollowUpDate(LocalDate followUpDate) {
        this.followUpDate = followUpDate;
    }

    public FollowUpStatus getStatus() {
        return status;
    }

    public void setStatus(FollowUpStatus status) {
        this.status = status;
    }

    public Long getLeadId() {
        return leadId;
    }

    public void setLeadId(Long leadId) {
        this.leadId = leadId;
    }
}