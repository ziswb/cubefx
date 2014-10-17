package ch.bziswiler.cube.controller;

import ch.bziswiler.cube.model.event.Visit;
import ch.bziswiler.cube.model.person.Person;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.ListProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.transformation.SortedList;

import java.util.Comparator;
import java.util.function.Predicate;

public class RemoveDuplicatePredicate implements Predicate<Visit> {

    public static final boolean DONT_INCLUDE_THIS_VISIT_IN_LIST = false;
    public static final boolean INCLUDE_THIS_VISIT_IN_LIST = true;
    private final SortedList<Visit> list;

    public RemoveDuplicatePredicate(ListProperty<Visit> list) {
        this.list = list.sorted(new Comparator<Visit>() {
            @Override
            public int compare(Visit o1, Visit o2) {
                final Person person1 = o1.personProperty().get();
                final Person person2 = o2.personProperty().get();
                final StringProperty p1LastName = person1.lastNameProperty();
                final StringProperty p1FirstName = person1.firstNameProperty();
                final StringProperty p2LastName = person2.lastNameProperty();
                final StringProperty p2FirstName = person2.firstNameProperty();
                final String p1FullName = p1LastName.concat(p1FirstName).get();
                final StringExpression p2FullName = p2LastName.concat(p2FirstName.get());
                return p1FullName.compareTo(p2FullName.get());
            }
        });
    }

    @Override
    public boolean test(Visit visit) {
        // FIXME
        boolean firstOccurrenceFound = DONT_INCLUDE_THIS_VISIT_IN_LIST;
        final Person visitsPerson = visit.personProperty().get();
        for (Visit v : list) {
            final Person otherVisitsPerson = v.personProperty().get();
            final boolean duplicateFound = visitsPerson.equals(otherVisitsPerson);
            if (firstOccurrenceFound && duplicateFound) {
                return DONT_INCLUDE_THIS_VISIT_IN_LIST;
            }
            firstOccurrenceFound = duplicateFound;
        }
        return INCLUDE_THIS_VISIT_IN_LIST;
    }
}
