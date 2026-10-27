package com.college.eventregistration.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Event Entity representing a college event.
 */
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Event name is mandatory")
    @Size(min = 3, max = 150, message = "Event name must be between 3 and 150 characters")
    @Column(nullable = false, length = 150)
    private String name;

    @NotNull(message = "Event date is mandatory")
    @Column(name = "event_date", nullable = false)
    private LocalDate eventDate;

    @NotBlank(message = "Event venue is mandatory")
    @Size(min = 2, max = 150, message = "Venue must be between 2 and 150 characters")
    @Column(nullable = false, length = 150)
    private String venue;

    @NotBlank(message = "Short description is mandatory")
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    @Column(length = 1000, nullable = false)
    private String description;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<EventRegistration> registrations = new ArrayList<>();

    public Event() {
    }

    public Event(String name, LocalDate eventDate, String venue, String description) {
        this.name = name;
        this.eventDate = eventDate;
        this.venue = venue;
        this.description = description;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<EventRegistration> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<EventRegistration> registrations) {
        this.registrations = registrations;
    }

    public int getRegistrationCount() {
        return this.registrations != null ? this.registrations.size() : 0;
    }
}
