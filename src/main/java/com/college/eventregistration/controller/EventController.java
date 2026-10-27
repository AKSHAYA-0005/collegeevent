package com.college.eventregistration.controller;

import com.college.eventregistration.dto.EventRequestDTO;
import com.college.eventregistration.dto.EventResponseDTO;
import com.college.eventregistration.dto.RegistrationResponseDTO;
import com.college.eventregistration.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for Event endpoints.
 * Base URL: /api/events
 */
@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Requirement 2: Display available events
     * GET /api/events or /api/events?search=keyword
     */
    @GetMapping
    public ResponseEntity<List<EventResponseDTO>> getAllEvents(
            @RequestParam(required = false) String search) {
        List<EventResponseDTO> events = eventService.getAllEvents(search);
        return ResponseEntity.ok(events);
    }

    /**
     * GET /api/events/{id} - Get single event
     */
    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getEventById(@PathVariable Long id) {
        EventResponseDTO event = eventService.getEventById(id);
        return ResponseEntity.ok(event);
    }

    /**
     * Requirement 1: Add events with name, date, venue, and short description
     * POST /api/events
     */
    @PostMapping
    public ResponseEntity<EventResponseDTO> createEvent(@Valid @RequestBody EventRequestDTO eventRequestDTO) {
        EventResponseDTO createdEvent = eventService.createEvent(eventRequestDTO);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    /**
     * PUT /api/events/{id} - Update an existing event
     */
    @PutMapping("/{id}")
    public ResponseEntity<EventResponseDTO> updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody EventRequestDTO eventRequestDTO) {
        EventResponseDTO updatedEvent = eventService.updateEvent(id, eventRequestDTO);
        return ResponseEntity.ok(updatedEvent);
    }

    /**
     * DELETE /api/events/{id} - Delete event and its registrations
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Event successfully deleted with ID: " + id);
        return ResponseEntity.ok(response);
    }

    /**
     * Requirement 4: Display participants registered for each event
     * GET /api/events/{id}/participants
     */
    @GetMapping("/{id}/participants")
    public ResponseEntity<List<RegistrationResponseDTO>> getParticipantsForEvent(@PathVariable Long id) {
        List<RegistrationResponseDTO> participants = eventService.getParticipantsForEvent(id);
        return ResponseEntity.ok(participants);
    }

    /**
     * Requirement 5: Show the total registrations for each event
     * GET /api/events/{id}/count
     */
    @GetMapping("/{id}/count")
    public ResponseEntity<Map<String, Object>> getRegistrationCount(@PathVariable Long id) {
        long count = eventService.getRegistrationCountForEvent(id);
        Map<String, Object> response = new HashMap<>();
        response.put("eventId", id);
        response.put("registrationCount", count);
        return ResponseEntity.ok(response);
    }
}
