package com.example.crm.task.service;

import com.example.crm.common.exception.ResourceNotFoundException;
import com.example.crm.customer.entity.Customer;
import com.example.crm.customer.repository.CustomerRepository;
import com.example.crm.task.dto.TaskRequest;
import com.example.crm.task.entity.Task;
import com.example.crm.task.entity.TaskPriority;
import com.example.crm.task.entity.TaskStatus;
import com.example.crm.task.repository.TaskRepository;
import com.example.crm.user.entity.User;
import com.example.crm.user.repository.UserRepository;
import com.example.crm.task.dto.TaskResponse;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;

    public TaskService(
            TaskRepository taskRepository,
            UserRepository userRepository,
            CustomerRepository customerRepository) {

        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
    }

    public TaskResponse createTask(TaskRequest request) {

        User user = userRepository.findById(
                        request.getAssignedUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Customer customer = customerRepository.findById(
                        request.getCustomerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found"));

        Task task = new Task();

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(TaskPriority.valueOf(request.getPriority()));
        task.setStatus(TaskStatus.valueOf(request.getStatus()));
        task.setDueDate(request.getDueDate());
        task.setAssignedTo(user);
        task.setCustomer(customer);

        Task savedTask =
                taskRepository.save(task);

        return mapToResponse(savedTask);
    }

    public List<TaskResponse> getAllTasks(){

        return taskRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<TaskResponse> getPendingTasks() {
        return taskRepository
                .findByStatus(TaskStatus.PENDING)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public TaskResponse updateStatus(Long id, TaskStatus status) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found"));

        task.setStatus(status);

        Task updatedTask =
                taskRepository.save(task);

        return mapToResponse(updatedTask);
    }

    private TaskResponse mapToResponse(Task task){

        TaskResponse response =
                new TaskResponse();

        response.setId(task.getId());

        response.setTitle(task.getTitle());

        response.setDescription(task.getDescription());

        response.setPriority(
                task.getPriority().name());

        response.setStatus(
                task.getStatus().name());

        response.setDueDate(
                task.getDueDate());

        if(task.getCustomer()!=null){

            response.setCustomerName(
                    task.getCustomer()
                            .getCompanyName());
        }

        if(task.getAssignedTo()!=null){

            response.setAssignedUser(
                    task.getAssignedTo()
                            .getName());
        }

        return response;
    }
}