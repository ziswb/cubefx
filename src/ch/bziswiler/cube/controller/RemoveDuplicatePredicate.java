package ch.bziswiler.cube.controller;

import ch.bziswiler.cube.model.event.Visit;
import ch.bziswiler.cube.model.person.Person;
import javafx.beans.property.ListProperty;

import java.util.function.Predicate;

public class RemoveDuplicatePredicate implements Predicate<Visit> {

    public static final boolean DONT_INCLUDE_THIS_VISIT_IN_LIST = false;
    public static final boolean INCLUDE_THIS_VISIT_IN_LIST = true;
    private final ListProperty<Visit> list;

    public RemoveDuplicatePredicate(ListProperty<Visit> list) {
        this.list = list;
    }

    @Override
    public boolean test(Visit visit) {
        final Person visitsPerson = visit.personProperty().get();
        final int indexOfVisit = list.indexOf(visit);
        for (Visit v : list) {
            final Person otherVisitsPerson = v.personProperty().get();
            if (visitsPerson.equals(otherVisitsPerson)) {
                final int indexOfOtherVisit = list.indexOf(v);
                if (indexOfVisit != indexOfOtherVisit) {
                    return DONT_INCLUDE_THIS_VISIT_IN_LIST;
                }
            }
        }
        return INCLUDE_THIS_VISIT_IN_LIST;
    }
}
