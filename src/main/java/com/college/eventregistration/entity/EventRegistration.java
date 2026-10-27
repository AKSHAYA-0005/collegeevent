package com.college.eventregistration.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * EventRegistration Entity representing a participant registered for an event.
 */
@Entity
@Table(
    name = "event_registrations",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_event_participant_email", 
            columnNames = {"event_id", "participant_email"}
        )
    }
)
public class EventRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Participant name is mandatory")
    @Size(min = 2, max = 100, message = "Participant name must be between 2 and 100 characters")
    @Column(name = "participant_name", nullable = false, length = 100)
    private String participantName;

    @NotBlank(message = "Participant email is mandatory")
    @Email(message = "Participant email must be a valid email format (e.g., student@college.edu)")
    @Column(name = "participant_email", nullable = false, length = 150)
    private String participantEmail;

    @Column(name = "registration_date", updatable = false)
    private LocalDateTime registrationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    public EventRegistration() {
    }

    public EventRegistration(String participantName, String participantEmail, Event event) {
        this.participantName = participantName;
        this.participantEmail = participantEmail;
        this.event = event;
    }

    @PrePersist
    protected void onCreate() {
        this.registrationDate = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public String getParticipantEmail() {
        return participantEmail;
    }

    public void setParticipantEmail(String participantEmail) {
        this.participantEmail = participantEmail;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
