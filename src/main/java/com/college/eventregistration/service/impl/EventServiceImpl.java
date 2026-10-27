package com.college.eventregistration.service.impl;

import com.college.eventregistration.dto.EventRequestDTO;
import com.college.eventregistration.dto.EventResponseDTO;
import com.college.eventregistration.dto.RegistrationRequestDTO;
import com.college.eventregistration.dto.RegistrationResponseDTO;
import com.college.eventregistration.entity.Event;
import com.college.eventregistration.entity.EventRegistration;
import com.college.eventregistration.exception.DuplicateRegistrationException;
import com.college.eventregistration.exception.ResourceNotFoundException;
import com.college.eventregistration.repository.EventRegistrationRepository;
import com.college.eventregistration.repository.EventRepository;
import com.college.eventregistration.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for Event and Registration operations.
 */
@Service
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventRegistrationRepository registrationRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, EventRegistrationRepository registrationRepository) {
        this.eventRepository = eventRepository;
        this.registrationRepository = registrationRepository;
    }

    @Override
    public EventResponseDTO createEvent(EventRequestDTO eventRequestDTO) {
        if (eventRequestDTO == null) {
            throw new IllegalArgumentException("Event request payload cannot be null");
        }
        if (eventRequestDTO.getName() == null || eventRequestDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Event name is mandatory");
        }
        if (eventRequestDTO.getEventDate() == null) {
            throw new IllegalArgumentException("Event date is mandatory");
        }
        if (eventRequestDTO.getVenue() == null || eventRequestDTO.getVenue().trim().isEmpty()) {
            throw new IllegalArgumentException("Event venue is mandatory");
        }
        if (eventRequestDTO.getDescription() == null || eventRequestDTO.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Event description is mandatory");
        }

        Event event = new Event();
        event.setName(eventRequestDTO.getName().trim());
        event.setEventDate(eventRequestDTO.getEventDate());
        event.setVenue(eventRequestDTO.getVenue().trim());
        event.setDescription(eventRequestDTO.getDescription().trim());

        Event savedEvent = eventRepository.save(event);
        return mapToEventResponseDTO(savedEvent);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventResponseDTO> getAllEvents(String search) {
        List<Event> events;
        if (search != null && !search.trim().isEmpty()) {
            events = eventRepository.searchEvents(search.trim());
        } else {
            events = eventRepository.findAllByOrderByEventDateAsc();
        }

        return events.stream()
                .map(this::mapToEventResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EventResponseDTO getEventById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Event ID cannot be null");
        }
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + id));
        return mapToEventResponseDTO(event);
    }

    @Override
    public EventResponseDTO updateEvent(Long id, EventRequestDTO eventRequestDTO) {
        if (id == null) {
            throw new IllegalArgumentException("Event ID cannot be null");
        }
        if (eventRequestDTO == null) {
            throw new IllegalArgumentException("Event update payload cannot be null");
        }

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + id));

        if (eventRequestDTO.getName() != null && !eventRequestDTO.getName().trim().isEmpty()) {
            event.setName(eventRequestDTO.getName().trim());
        }
        if (eventRequestDTO.getEventDate() != null) {
            event.setEventDate(eventRequestDTO.getEventDate());
        }
        if (eventRequestDTO.getVenue() != null && !eventRequestDTO.getVenue().trim().isEmpty()) {
            event.setVenue(eventRequestDTO.getVenue().trim());
        }
        if (eventRequestDTO.getDescription() != null && !eventRequestDTO.getDescription().trim().isEmpty()) {
            event.setDescription(eventRequestDTO.getDescription().trim());
        }

        Event updatedEvent = eventRepository.save(event);
        return mapToEventResponseDTO(updatedEvent);
    }

    @Override
    public void deleteEvent(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Event ID cannot be null");
        }
        if (!eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cannot delete: Event not found with ID: " + id);
        }
        eventRepository.deleteById(id);
    }

    @Override
    public RegistrationResponseDTO registerParticipant(RegistrationRequestDTO requestDTO) {
        if (requestDTO == null) {
            throw new IllegalArgumentException("Registration request payload cannot be null");
        }
        if (requestDTO.getEventId() == null) {
            throw new IllegalArgumentException("Event ID is mandatory for registration");
        }
        if (requestDTO.getParticipantName() == null || requestDTO.getParticipantName().trim().isEmpty()) {
            throw new IllegalArgumentException("Participant name is mandatory");
        }
        if (requestDTO.getParticipantEmail() == null || requestDTO.getParticipantEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Participant email is mandatory");
        }

        // 1. Verify that the selected event exists
        Event event = eventRepository.findById(requestDTO.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Registration failed: Selected event with ID " + requestDTO.getEventId() + " does not exist"));

        String normalizedEmail = requestDTO.getParticipantEmail().trim().toLowerCase();

        // 2. Check for duplicate registration for the same event
        boolean alreadyRegistered = registrationRepository
                .existsByEventIdAndParticipantEmailIgnoreCase(event.getId(), normalizedEmail);
        if (alreadyRegistered) {
            throw new DuplicateRegistrationException(
                    "Participant with email '" + normalizedEmail + "' is already registered for '" + event.getName() + "'.");
        }

        // 3. Create and persist registration
        EventRegistration registration = new EventRegistration();
        registration.setParticipantName(requestDTO.getParticipantName().trim());
        registration.setParticipantEmail(normalizedEmail);
        registration.setEvent(event);

        EventRegistration savedRegistration = registrationRepository.save(registration);
        return mapToRegistrationResponseDTO(savedRegistration);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegistrationResponseDTO> getParticipantsForEvent(Long eventId) {
        if (eventId == null) {
            throw new IllegalArgumentException("Event ID cannot be null");
        }
        // Verify event exists first
        if (!eventRepository.existsById(eventId)) {
            throw new ResourceNotFoundException("Event not found with ID: " + eventId);
        }

        List<EventRegistration> registrations = registrationRepository
                .findByEventIdOrderByRegistrationDateDesc(eventId);

        return registrations.stream()
                .map(this::mapToRegistrationResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public long getRegistrationCountForEvent(Long eventId) {
        if (eventId == null) {
            throw new IllegalArgumentException("Event ID cannot be null");
        }
        if (!eventRepository.existsById(eventId)) {
            throw new ResourceNotFoundException("Event not found with ID: " + eventId);
        }
        return registrationRepository.countByEventId(eventId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegistrationResponseDTO> getAllRegistrations() {
        return registrationRepository.findAll().stream()
                .map(this::mapToRegistrationResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RegistrationResponseDTO getRegistrationById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Registration ID cannot be null");
        }
        EventRegistration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registration not found with ID: " + id));
        return mapToRegistrationResponseDTO(registration);
    }

    @Override
    public void cancelRegistration(Long registrationId) {
        if (registrationId == null) {
            throw new IllegalArgumentException("Registration ID cannot be null");
        }
        if (!registrationRepository.existsById(registrationId)) {
            throw new ResourceNotFoundException("Registration not found with ID: " + registrationId);
        }
        registrationRepository.deleteById(registrationId);
    }

    // Helper conversion methods
    private EventResponseDTO mapToEventResponseDTO(Event event) {
        long count = registrationRepository.countByEventId(event.getId());
        return new EventResponseDTO(
                event.getId(),
                event.getName(),
                event.getEventDate(),
                event.getVenue(),
                event.getDescription(),
                count,
                event.getCreatedAt() != null ? event.getCreatedAt() : java.time.LocalDateTime.now()
        );
    }

    private RegistrationResponseDTO mapToRegistrationResponseDTO(EventRegistration reg) {
        return new RegistrationResponseDTO(
                reg.getId(),
                reg.getEvent().getId(),
                reg.getEvent().getName(),
                reg.getParticipantName(),
                reg.getParticipantEmail(),
                reg.getRegistrationDate() != null ? reg.getRegistrationDate() : java.time.LocalDateTime.now()
        );
    }
}
