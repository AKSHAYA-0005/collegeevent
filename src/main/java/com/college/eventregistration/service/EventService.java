package com.college.eventregistration.service;

import com.college.eventregistration.dto.EventRequestDTO;
import com.college.eventregistration.dto.EventResponseDTO;
import com.college.eventregistration.dto.RegistrationRequestDTO;
import com.college.eventregistration.dto.RegistrationResponseDTO;

import java.util.List;

/**
 * Service interface defining business operations for events and registrations.
 */
public interface EventService {

    /**
     * Create a new college event
     */
    EventResponseDTO createEvent(EventRequestDTO eventRequestDTO);

    /**
     * Retrieve all available events (with optional search filter)
     */
    List<EventResponseDTO> getAllEvents(String search);

    /**
     * Retrieve single event by its ID
     */
    EventResponseDTO getEventById(Long id);

    /**
     * Update an existing event
     */
    EventResponseDTO updateEvent(Long id, EventRequestDTO eventRequestDTO);

    /**
     * Delete an event by its ID
     */
    void deleteEvent(Long id);

    /**
     * Register a participant for a selected event
     */
    RegistrationResponseDTO registerParticipant(RegistrationRequestDTO requestDTO);

    /**
     * Retrieve all participants registered for a specific event
     */
    List<RegistrationResponseDTO> getParticipantsForEvent(Long eventId);

    /**
     * Get the total registration count for a specific event
     */
    long getRegistrationCountForEvent(Long eventId);

    /**
     * Retrieve all registrations across all events
     */
    List<RegistrationResponseDTO> getAllRegistrations();

    /**
     * Retrieve a specific registration by its ID
     */
    RegistrationResponseDTO getRegistrationById(Long id);

    /**
     * Cancel/delete a registration by ID
     */
    void cancelRegistration(Long registrationId);
}

