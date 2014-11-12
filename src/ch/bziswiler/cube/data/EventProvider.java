package ch.bziswiler.cube.data;

import ch.bziswiler.cube.model.address.Address;
import ch.bziswiler.cube.model.event.Event;
import com.google.common.collect.Lists;

import java.util.List;

public class EventProvider {

    private final List<Event> events;

    public EventProvider() {
        this.events = Lists.newArrayList();
        loadEvents();
    }

    private void loadEvents() {
        final Event event = new Event();
        final Address address = new Address();
    }

    public List<Event> getEvents() {
        return events;
    }
}
