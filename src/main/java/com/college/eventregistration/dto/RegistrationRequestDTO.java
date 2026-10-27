package com.college.eventregistration.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for registering a participant for an event.
 */
public class RegistrationRequestDTO {

    @NotNull(message = "Event ID is required to register")
    private Long eventId;

    @NotBlank(message = "Participant name is required")
    @Size(min = 2, max = 100, message = "Participant name must be between 2 and 100 characters")
    private String participantName;

    @NotBlank(message = "Participant email is required")
    @Email(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", 
           message = "Participant email must be a valid email format (e.g. rahul@example.com)")
    private String participantEmail;

    public RegistrationRequestDTO() {
    }

    public RegistrationRequestDTO(Long eventId, String participantName, String participantEmail) {
        this.eventId = eventId;
        this.participantName = participantName;
        this.participantEmail = participantEmail;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
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
}
