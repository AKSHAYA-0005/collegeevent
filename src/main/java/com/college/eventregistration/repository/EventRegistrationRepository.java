package com.college.eventregistration.repository;

import com.college.eventregistration.entity.EventRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA Repository for EventRegistration Entity.
 */
@Repository
public interface EventRegistrationRepository extends JpaRepository<EventRegistration, Long> {

    /**
     * Retrieve all participants registered for a specific event
     */
    List<EventRegistration> findByEventIdOrderByRegistrationDateDesc(Long eventId);

    /**
     * Count registrations for a specific event
     */
    long countByEventId(Long eventId);

    /**
     * Check if a participant with this email is already registered for this event
     */
    boolean existsByEventIdAndParticipantEmailIgnoreCase(Long eventId, String participantEmail);
}
