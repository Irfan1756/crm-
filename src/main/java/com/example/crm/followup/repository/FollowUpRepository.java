package com.example.crm.followup.repository;

import com.example.crm.followup.entity.FollowUp;
import com.example.crm.followup.entity.FollowUpStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowUpRepository
        extends JpaRepository<FollowUp, Long> {

    long countByStatus(FollowUpStatus status);
}