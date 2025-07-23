package com.example.eventtracker.controller;

import com.example.eventtracker.model.Event;
import com.example.eventtracker.repository.EventRepository;
import com.example.eventtracker.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EmailService emailService;  // Inject EmailService

    // Endpoint to track an event and send notification email
    @PostMapping("/events")
    public String trackEvent(@RequestBody Map<String, String> payload) {
        String userId = payload.get("userId");
        String eventType = payload.get("eventType");

        if (userId == null || eventType == null) {
            return "Missing required fields";
        }

        Event event = new Event(userId, eventType);
        eventRepository.save(event);

        // Send email notification (replace with actual receiver email)
        String toEmail = "kanikakhurana205.com";
        String subject = "New Event Tracked: " + eventType;
        String body = "User " + userId + " just triggered event: " + eventType;
        emailService.sendSimpleEmail(toEmail, subject, body);

        return "Event tracked and email sent!";
    }

    // Endpoint to get aggregated analytics summary by event type
    @GetMapping("/analytics")
    public Map<String, Long> getAnalytics() {
        List<Event> events = eventRepository.findAll();
        Map<String, Long> analytics = new HashMap<>();

        for (Event event : events) {
            String type = event.getEventType();
            analytics.put(type, analytics.getOrDefault(type, 0L) + 1);
        }

        return analytics;
    }

    // Endpoint to get all events stored in the database
    @GetMapping("/events")
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
}
