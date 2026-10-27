package com.college.eventregistration.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Event response including registration count.
 */
public class EventResponseDTO {

    private Long id;
    private String name;
    private LocalDate eventDate;
    private String venue;
    private String description;
    private long registrationCount;
    private LocalDateTime createdAt;

    public EventResponseDTO() {
    }

    public EventResponseDTO(Long id, String name, LocalDate eventDate, String venue, 
                            String description, long registrationCount, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.eventDate = eventDate;
        this.venue = venue;
        this.description = description;
        this.registrationCount = registrationCount;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getRegistrationCount() {
        return registrationCount;
    }

    public void setRegistrationCount(long registrationCount) {
        this.registrationCount = registrationCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
