package com.example.crm.note.controller;

import com.example.crm.note.dto.NoteRequest;
import com.example.crm.note.entity.Note;
import com.example.crm.note.service.NoteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.crm.note.dto.NoteResponse;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "*")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public NoteResponse createNote(
            @RequestBody NoteRequest request) {

        return noteService.createNote(request);
    }

    @GetMapping("/customer/{id}")
    public List<NoteResponse> getCustomerNotes(
            @PathVariable Long id) {

        return noteService.getCustomerNotes(id);
    }

    @GetMapping("/lead/{id}")
    public List<NoteResponse> getLeadNotes(
            @PathVariable Long id) {

        return noteService.getLeadNotes(id);
    }

    @GetMapping
    public Page<Note> getNotes(Pageable pageable) {
        return noteService.getNotes(pageable);
    }
}