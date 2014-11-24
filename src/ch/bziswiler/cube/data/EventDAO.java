package ch.bziswiler.cube.data;

import ch.bziswiler.cube.model.event.Event;
import com.google.common.collect.Lists;

import java.util.List;

public class EventDAO {

    public static final EventDAO INSTANCE = new EventDAO();

    private List<Event> events;

    private EventDAO() {
        this.events = Lists.newArrayList();
        this.events.add(new Event() {
            @Override
            public String toString() {
                return "Foo";
            }
        });
        this.events.add(new Event() {
            @Override
            public String toString() {
                return "Bar";
            }
        });
        this.events.add(new Event() {
            @Override
            public String toString() {
                return "Qux";
            }
        });
    }

    public List<Event> getEvents() {
        return this.events;
    }

    public void addEvent(Event event) {
        this.events.add(event);
    }
}
