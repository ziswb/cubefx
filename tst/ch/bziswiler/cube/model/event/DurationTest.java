package ch.bziswiler.cube.model.event;

import org.junit.Assert;
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
        long years = duration.getYears();
        long months = duration.getMonths();
        long weeks = duration.getWeeks();
        long days = duration.getDays();
        long hours = duration.getHours();
        long minutes = duration.getMinutes();
        assertThat(years, is(1L));
        assertThat(months, is(13L));
        assertThat(weeks, is(56L));
        assertThat(days, is(396L));
        assertThat(hours, is(9505L));
        assertThat(minutes, is(570331L));
    }
}
