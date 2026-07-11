package com.example.crm.dashboard.service;

import com.example.crm.customer.repository.CustomerRepository;
import com.example.crm.dashboard.dto.DashboardResponse;
import com.example.crm.lead.entity.LeadStatus;
import com.example.crm.lead.repository.LeadRepository;
import com.example.crm.note.repository.NoteRepository;
import com.example.crm.task.entity.TaskStatus;
import com.example.crm.task.repository.TaskRepository;

import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final CustomerRepository customerRepository;
    private final LeadRepository leadRepository;
    private final TaskRepository taskRepository;
    private final NoteRepository noteRepository;

    public DashboardService(
            CustomerRepository customerRepository,
            LeadRepository leadRepository,
            TaskRepository taskRepository,
            NoteRepository noteRepository) {

        this.customerRepository = customerRepository;
        this.leadRepository = leadRepository;
        this.taskRepository = taskRepository;
        this.noteRepository = noteRepository;
    }

    public DashboardResponse getDashboardSummary() {

        DashboardResponse response = new DashboardResponse();

        response.setTotalCustomers(
                customerRepository.count());

        response.setTotalLeads(
                leadRepository.count());

        response.setConvertedLeads(
                leadRepository.countByStatus(
                        LeadStatus.CONVERTED));

        response.setPendingTasks(
                taskRepository.countByStatus(
                        TaskStatus.PENDING));

        response.setTotalNotes(
                noteRepository.count());

        return response;
    }
}