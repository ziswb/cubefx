package ch.bziswiler.cube.data;

import ch.bziswiler.cube.model.event.Event;
import com.google.common.collect.Lists;

import java.util.List;

public class EventDAO {

    public static final EventDAO INSTANCE = new EventDAO();

    private List<Event> events;

    private EventDAO() {
        this.events = Lists.newArrayList();
        Event event = new Event();
        event.setName("Foo");
        this.events.add(event);
        event = new Event();
        event.setName("Bar");
        this.events.add(event);
        event = new Event();
        event.setName("Qux");
        this.events.add(event);
    }

    public List<Event> getEvents() {
        return this.events;
    }

    public void addEvent(Event event) {
        this.events.add(event);
    }
}
