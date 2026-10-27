package com.college.eventregistration.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * Data Transfer Object for creating or updating an Event.
 */
public class EventRequestDTO {

    @NotBlank(message = "Event name is required")
    @Size(min = 3, max = 150, message = "Event name must be between 3 and 150 characters")
    private String name;

    @NotNull(message = "Event date is required")
    private LocalDate eventDate;

    @NotBlank(message = "Event venue is required")
    @Size(min = 2, max = 150, message = "Venue must be between 2 and 150 characters")
    private String venue;

    @NotBlank(message = "Short description is required")
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    public EventRequestDTO() {
    }

    public EventRequestDTO(String name, LocalDate eventDate, String venue, String description) {
        this.name = name;
        this.eventDate = eventDate;
        this.venue = venue;
        this.description = description;
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
}
