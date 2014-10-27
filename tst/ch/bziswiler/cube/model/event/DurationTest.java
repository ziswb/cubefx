package ch.bziswiler.cube.model.event;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DurationTest {

    @Test
    public void testDuration() {
        Duration duration = new Duration();
        LocalDateTime start = LocalDateTime.of(2014, 9, 22, 21, 0);
        LocalDateTime end = LocalDateTime.of(2015, 10, 23, 22, 31);
        duration.setStart(start);
        duration.setEnd(end);
        long hours = duration.getHours();
        long minutes = duration.getMinutes();
        assertThat(hours, is(9505L));
        assertThat(minutes, is(31L));
    }
}
