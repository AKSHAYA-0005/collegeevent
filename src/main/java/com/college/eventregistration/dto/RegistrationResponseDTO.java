package com.college.eventregistration.dto;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for registration confirmation and participant list response.
 */
public class RegistrationResponseDTO {

    private Long id;
    private Long eventId;
    private String eventName;
    private String participantName;
    private String participantEmail;
    private LocalDateTime registrationDate;

    public RegistrationResponseDTO() {
    }

    public RegistrationResponseDTO(Long id, Long eventId, String eventName, 
                                   String participantName, String participantEmail, 
                                   LocalDateTime registrationDate) {
        this.id = id;
        this.eventId = eventId;
        this.eventName = eventName;
        this.participantName = participantName;
        this.participantEmail = participantEmail;
        this.registrationDate = registrationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
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
}
