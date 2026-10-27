package com.college.eventregistration;

import com.college.eventregistration.dto.EventRequestDTO;
import com.college.eventregistration.dto.EventResponseDTO;
import com.college.eventregistration.dto.RegistrationRequestDTO;
import com.college.eventregistration.dto.RegistrationResponseDTO;
import com.college.eventregistration.entity.Event;
import com.college.eventregistration.repository.EventRegistrationRepository;
import com.college.eventregistration.repository.EventRepository;
import com.college.eventregistration.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventRegistrationApplicationTests {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventRegistrationRepository registrationRepository;

    @BeforeEach
    void setUp() {
        registrationRepository.deleteAll();
        eventRepository.deleteAll();
    }

    @Test
    @DisplayName("Test 1: Create and retrieve an event")
    void testCreateAndRetrieveEvent() {
        EventRequestDTO request = new EventRequestDTO(
                "Hackathon 2026",
                LocalDate.now().plusDays(10),
                "Auditorium 1",
                "Flagship college programming competition."
        );

        EventResponseDTO created = eventService.createEvent(request);
        assertNotNull(created.getId());
        assertEquals("Hackathon 2026", created.getName());
        assertEquals(0, created.getRegistrationCount());

        List<EventResponseDTO> allEvents = eventService.getAllEvents(null);
        assertEquals(1, allEvents.size());
    }

    @Test
    @DisplayName("Test 2: Register participant and verify registration count increments")
    void testEventRegistrationAndCount() {
        // Step 1: Create Event
        EventRequestDTO eventRequest = new EventRequestDTO(
                "Robotics Expo",
                LocalDate.now().plusDays(15),
                "Mech Lab",
                "Robotics showcase and demonstration."
        );
        EventResponseDTO event = eventService.createEvent(eventRequest);
        Long eventId = event.getId();

        // Check initial count
        assertEquals(0, eventService.getRegistrationCountForEvent(eventId));

        // Step 2: Register First Participant
        RegistrationRequestDTO reg1 = new RegistrationRequestDTO(
                eventId,
                "Rahul Sharma",
                "rahul.sharma@college.edu"
        );
        RegistrationResponseDTO res1 = eventService.registerParticipant(reg1);
        assertNotNull(res1.getId());
        assertEquals("Rahul Sharma", res1.getParticipantName());

        // Verify count updated to 1
        assertEquals(1, eventService.getRegistrationCountForEvent(eventId));

        // Step 3: Register Second Participant
        RegistrationRequestDTO reg2 = new RegistrationRequestDTO(
                eventId,
                "Priya Patel",
                "priya.patel@college.edu"
        );
        RegistrationResponseDTO res2 = eventService.registerParticipant(reg2);
        assertNotNull(res2.getId());

        // Verify count updated to 2
        assertEquals(2, eventService.getRegistrationCountForEvent(eventId));

        // Step 4: Verify participants list for event
        List<RegistrationResponseDTO> participants = eventService.getParticipantsForEvent(eventId);
        assertEquals(2, participants.size());
    }

    @Test
    @DisplayName("Test 3: Prevent duplicate registration for the same event and email")
    void testDuplicateRegistrationFails() {
        EventRequestDTO eventRequest = new EventRequestDTO(
                "AI Workshop",
                LocalDate.now().plusDays(5),
                "Seminar Hall",
                "Workshop on modern Generative AI techniques."
        );
        EventResponseDTO event = eventService.createEvent(eventRequest);

        RegistrationRequestDTO reg1 = new RegistrationRequestDTO(
                event.getId(),
                "Amit Kumar",
                "amit.kumar@college.edu"
        );
        eventService.registerParticipant(reg1);

        // Attempt registering with same email for the same event should throw DuplicateRegistrationException
        RegistrationRequestDTO duplicate = new RegistrationRequestDTO(
                event.getId(),
                "Amit Kumar Duplicate",
                "amit.kumar@college.edu"
        );

        assertThrows(RuntimeException.class, () -> {
            eventService.registerParticipant(duplicate);
        });
    }

    @Test
    @DisplayName("Test 4: Registration fails when event does not exist")
    void testRegistrationFailsForNonExistentEvent() {
        RegistrationRequestDTO reg = new RegistrationRequestDTO(
                99999L,
                "Ghost Student",
                "ghost@college.edu"
        );

        assertThrows(RuntimeException.class, () -> {
            eventService.registerParticipant(reg);
        });
    }

    @Test
    @DisplayName("Test 5: Update event details successfully")
    void testUpdateEvent() {
        EventRequestDTO original = new EventRequestDTO(
                "Guest Lecture on Cloud",
                LocalDate.now().plusDays(7),
                "Seminar Hall 1",
                "Introduction to AWS and Cloud Native architectures."
        );
        EventResponseDTO created = eventService.createEvent(original);

        EventRequestDTO updatedRequest = new EventRequestDTO(
                "Advanced Cloud Computing & Kubernetes",
                LocalDate.now().plusDays(8),
                "Auditorium 2",
                "Deep dive into microservices and container orchestration."
        );
        EventResponseDTO updated = eventService.updateEvent(created.getId(), updatedRequest);

        assertEquals("Advanced Cloud Computing & Kubernetes", updated.getName());
        assertEquals("Auditorium 2", updated.getVenue());
        assertEquals(LocalDate.now().plusDays(8), updated.getEventDate());
    }

    @Test
    @DisplayName("Test 6: Delete event and cascade delete its registrations")
    void testDeleteEventAndCascadedRegistrations() {
        EventRequestDTO eventRequest = new EventRequestDTO(
                "Web Dev Bootcamp",
                LocalDate.now().plusDays(12),
                "Lab 3",
                "Full stack development with React and Spring Boot."
        );
        EventResponseDTO event = eventService.createEvent(eventRequest);

        RegistrationRequestDTO reg = new RegistrationRequestDTO(
                event.getId(),
                "Suresh Reddy",
                "suresh.reddy@college.edu"
        );
        RegistrationResponseDTO registered = eventService.registerParticipant(reg);
        assertNotNull(registered.getId());

        // Delete event
        eventService.deleteEvent(event.getId());

        // Verify event no longer exists
        assertThrows(RuntimeException.class, () -> eventService.getEventById(event.getId()));

        // Verify registrations were cleaned up
        assertTrue(registrationRepository.findAll().isEmpty());
    }

    @Test
    @DisplayName("Test 7: Search events by name, venue, or description")
    void testSearchEvents() {
        eventService.createEvent(new EventRequestDTO(
                "Cyber Security Conclave",
                LocalDate.now().plusDays(3),
                "Main Hall",
                "Network penetration testing and ethical hacking."
        ));
        eventService.createEvent(new EventRequestDTO(
                "Data Science Summit",
                LocalDate.now().plusDays(6),
                "Research Block",
                "Machine learning workflows and big data analytics."
        ));

        // Search by keyword in name
        List<EventResponseDTO> cyberSearch = eventService.getAllEvents("Cyber");
        assertEquals(1, cyberSearch.size());
        assertEquals("Cyber Security Conclave", cyberSearch.get(0).getName());

        // Search by keyword in venue
        List<EventResponseDTO> venueSearch = eventService.getAllEvents("Research");
        assertEquals(1, venueSearch.size());
        assertEquals("Data Science Summit", venueSearch.get(0).getName());

        // Search by keyword in description
        List<EventResponseDTO> descSearch = eventService.getAllEvents("penetration");
        assertEquals(1, descSearch.size());
        assertEquals("Cyber Security Conclave", descSearch.get(0).getName());
    }

    @Test
    @DisplayName("Test 8: Cancel individual participant registration")
    void testCancelRegistration() {
        EventResponseDTO event = eventService.createEvent(new EventRequestDTO(
                "Open Source Day",
                LocalDate.now().plusDays(4),
                "Open Air Theatre",
                "Contributing to popular open-source repositories."
        ));

        RegistrationResponseDTO reg = eventService.registerParticipant(new RegistrationRequestDTO(
                event.getId(),
                "Kavya Nair",
                "kavya.nair@college.edu"
        ));

        assertEquals(1, eventService.getRegistrationCountForEvent(event.getId()));

        // Cancel registration
        eventService.cancelRegistration(reg.getId());

        assertEquals(0, eventService.getRegistrationCountForEvent(event.getId()));
    }
}
