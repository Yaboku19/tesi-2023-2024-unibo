package tesi.unibo.dynamic.prenotationSystem;

import java.util.*;

public class PrenotationSystem {
    private Map<String, Event> events = new HashMap<>();
    private Map<String, Set<String>> userTickets = new HashMap<>();

    public void addEvent(String eventId, Event event) {
        if (event.getName().isEmpty()) {
            throw new IllegalArgumentException("Event name cannot be empty");
        }
        events.put(eventId, event);
    }

    public boolean reserveTicket(String userId, String eventId) {
        Event event = events.get(eventId);
        if (event == null) {
            return false;
        }
        if (userTickets.containsKey(userId) && userTickets.get(userId).contains(eventId)) {
            return false;
        }

        for (String ticketID : userTickets.getOrDefault(userId, new HashSet<>())) {
            Event userEvent = events.get(ticketID);
            if (userEvent.getDate().equals(event.getDate())) {
                return false;
            }
        }

        if (event.getCapacity() <= userTickets.getOrDefault(eventId, new HashSet<>()).size()) {
            return false;
        }
        userTickets.putIfAbsent(userId, new HashSet<>());
        userTickets.get(userId).add(eventId);
        return true;
    }

    public boolean deleteEvent(String eventId) {
        if (!events.containsKey(eventId)) {
            return false;
        }
        events.remove(eventId);
        userTickets.values().forEach(tickets -> tickets.remove(eventId));
        return true;
    }

    public Set<String> getUserTickets(String userId) {
        Set<String> tickets = userTickets.get(userId);
        if (tickets == null || tickets.isEmpty()) {
            userTickets.remove(userId);
            return null;
        }
        return tickets;
    }
}