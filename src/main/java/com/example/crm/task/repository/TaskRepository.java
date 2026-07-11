package com.example.crm.task.repository;

import com.example.crm.task.entity.Task;
import com.example.crm.task.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository
        extends JpaRepository<Task, Long> {

    List<Task> findByAssignedToId(Long userId);

    List<Task> findByCustomerId(Long customerId);

    List<Task> findByStatus(TaskStatus status);

    long countByStatus(TaskStatus status);
}