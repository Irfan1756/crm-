package com.example.crm.note.repository;

import com.example.crm.note.entity.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository
        extends JpaRepository<Note,Long> {

    List<Note> findByCustomerId(Long customerId);

    List<Note> findByLeadId(Long leadId);

    Page<Note> findAll(Pageable pageable);
}