package com.college.eventregistration.controller;

import com.college.eventregistration.dto.RegistrationRequestDTO;
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
 * REST Controller for Registration endpoints.
 * Base URL: /api/registrations
 */
@RestController
@RequestMapping("/api/registrations")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
public class RegistrationController {

    private final EventService eventService;

    @Autowired
    public RegistrationController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Requirement 3: Register a participant using name and email for a selected event
     * POST /api/registrations
     */
    @PostMapping
    public ResponseEntity<RegistrationResponseDTO> registerParticipant(
            @Valid @RequestBody RegistrationRequestDTO requestDTO) {
        RegistrationResponseDTO response = eventService.registerParticipant(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * GET /api/registrations - Retrieve all registrations across events
     */
    @GetMapping
    public ResponseEntity<List<RegistrationResponseDTO>> getAllRegistrations() {
        List<RegistrationResponseDTO> list = eventService.getAllRegistrations();
        return ResponseEntity.ok(list);
    }

    /**
     * GET /api/registrations/{id} - Retrieve single registration by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<RegistrationResponseDTO> getRegistrationById(@PathVariable Long id) {
        RegistrationResponseDTO response = eventService.getRegistrationById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /api/registrations/{id} - Cancel or remove a registration
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> cancelRegistration(@PathVariable Long id) {
        eventService.cancelRegistration(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Registration successfully cancelled with ID: " + id);
        return ResponseEntity.ok(response);
    }
}
