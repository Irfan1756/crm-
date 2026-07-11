package com.example.crm.lead.repository;

import com.example.crm.lead.entity.Lead;
import com.example.crm.lead.entity.LeadStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeadRepository
        extends JpaRepository<Lead, Long> {

    List<Lead> findByStatus(LeadStatus status);

    List<Lead> findByAssignedToId(Long userId);

    List<Lead> findByCustomerId(Long customerId);

    long countByStatus(LeadStatus status);
}