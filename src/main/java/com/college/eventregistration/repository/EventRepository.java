package com.college.eventregistration.repository;

import com.college.eventregistration.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA Repository for Event Entity.
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    /**
     * Find events with name containing search keyword (case-insensitive)
     */
    List<Event> findByNameContainingIgnoreCase(String name);

    /**
     * Search events across name, venue, or description (case-insensitive) ordered by date
     */
    @Query("SELECT e FROM Event e WHERE " +
           "LOWER(e.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(e.venue) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(e.description) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "ORDER BY e.eventDate ASC")
    List<Event> searchEvents(@Param("search") String search);

    /**
     * Find all events ordered by eventDate ascending
     */
    List<Event> findAllByOrderByEventDateAsc();
}

