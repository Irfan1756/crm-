package com.example.crm.followup.controller;

import com.example.crm.followup.dto.FollowUpRequest;
import com.example.crm.followup.entity.FollowUp;
import com.example.crm.followup.service.FollowUpService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/followups")
@CrossOrigin(origins = "*")
public class FollowUpController {

    private final FollowUpService followUpService;

    public FollowUpController(
            FollowUpService followUpService) {

        this.followUpService = followUpService;
    }

    @PostMapping
    public FollowUp createFollowUp(
            @RequestBody FollowUpRequest request) {

        return followUpService.createFollowUp(request);
    }

    @GetMapping
    public List<FollowUp> getAllFollowUps() {
        return followUpService.getAllFollowUps();
    }
}