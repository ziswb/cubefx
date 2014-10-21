package ch.bziswiler.cube.model;

import ch.bziswiler.cube.controller.CubeEventModel;
import ch.bziswiler.cube.model.event.Visit;
import ch.bziswiler.cube.model.person.Person;

import java.util.List;
import java.util.Optional;

public final class VisitsUtil {

    private VisitsUtil() {
        // nop
    }

    public static Optional<Visit> findNonCheckOutVisit(CubeEventModel event, Person person) {
        Optional<Visit> visit;
        visit = findNonCheckOutVisit(event.youthMemberVisitsProperty(), person);
        if (visit.isPresent()) {
            return visit;
        }
        visit = findNonCheckOutVisit(event.youthStaffVisitsProperty(), person);
        if (visit.isPresent()) {
            return visit;
        }
        visit = findNonCheckOutVisit(event.adultStaffVisitsProperty(), person);
        if (visit.isPresent()) {
            return visit;
        }
        visit = findNonCheckOutVisit(event.driverVisitsProperty(), person);
        if (visit.isPresent()) {
            return visit;
        }
        return visit;
    }

    public static Optional<Visit> findNonCheckOutVisit(List<Visit> visits, Person person) {
        Visit v = null;
        for (Visit visit : visits) {
            if (isPersonsNonCheckOutVisit(person, visit)) {
                v = visit;
                break;
            }
        }
        return Optional.ofNullable(v);
    }

    private static boolean isPersonsNonCheckOutVisit(Person person, Visit visit) {
        return visit.personProperty().isNotNull().and(visit.personProperty().isEqualTo(person)).and(visit.checkOutProperty().isNull()).get();
    }
}
