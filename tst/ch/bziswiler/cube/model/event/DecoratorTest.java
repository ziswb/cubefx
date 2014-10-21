package ch.bziswiler.cube.model.event;

import ch.bziswiler.cube.model.person.Person;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class DecoratorTest {

    @Test
    public void testGetDecorator() {
        Person person = new Person();
        PersonMetaDataDecorator decorator = new PersonMetaDataDecorator();
        person.addDecorator(decorator);
        PersonMetaDataDecorator personDecorator = person.getDecorator(PersonMetaDataDecorator.class);
        assertThat(personDecorator, is(not(nullValue())));
    }
}
