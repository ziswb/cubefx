package ch.bziswiler.cube.controller;

import ch.bziswiler.cube.model.address.City;
import ch.bziswiler.cube.model.event.Visit;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.collections.transformation.FilteredList;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class EventStatistics {

    private final CubeEventModel model;

    private final Predicate<Visit> presentMembersPredicate = visit -> {
        return visit.checkOutProperty().isNull().get();
    };

    private ReadOnlyIntegerWrapper numberOfPresentYouthMembersProperty = new ReadOnlyIntegerWrapper();
    private ReadOnlyIntegerWrapper numberOfPresentYouthStaffProperty = new ReadOnlyIntegerWrapper();
    private ReadOnlyIntegerWrapper numberOfPresentAdultStaffProperty = new ReadOnlyIntegerWrapper();
    private ReadOnlyIntegerWrapper numberOfPresentDriversProperty = new ReadOnlyIntegerWrapper();
    private ReadOnlyIntegerWrapper totalNumberOfYouthMembersProperty = new ReadOnlyIntegerWrapper();
    private ReadOnlyIntegerWrapper totalNumberOfYouthStaffProperty = new ReadOnlyIntegerWrapper();
    private ReadOnlyIntegerWrapper totalNumberOfAdultStaffProperty = new ReadOnlyIntegerWrapper();
    private ReadOnlyIntegerWrapper totalNumberOfDriversProperty = new ReadOnlyIntegerWrapper();

    public EventStatistics(CubeEventModel model) {
        this.model = model;
    }

    public ReadOnlyIntegerProperty numberOfPresentYouthMembersProperty() {
        return numberOfPresentYouthMembersProperty.getReadOnlyProperty();
    }

    public final Integer numberOfPresentYouthMembers() {
        return numberOfPresentYouthMembersProperty.get();
    }

    public ReadOnlyIntegerProperty numberOfAllYouthMembersProperty() {
        return totalNumberOfYouthMembersProperty.getReadOnlyProperty();
    }

    public ReadOnlyIntegerProperty numberOfPresentYouthStaffProperty() {
        return numberOfPresentYouthStaffProperty.getReadOnlyProperty();
    }

    public ReadOnlyIntegerProperty numberOfAllYouthStaffProperty() {
        return totalNumberOfYouthStaffProperty.getReadOnlyProperty();
    }

    public ReadOnlyIntegerProperty numberOfPresentAdultStaffProperty() {
        return numberOfPresentAdultStaffProperty.getReadOnlyProperty();
    }

    public ReadOnlyIntegerProperty numberOfAllAdultStaffProperty() {
        return totalNumberOfAdultStaffProperty.getReadOnlyProperty();
    }

    public ReadOnlyIntegerProperty numberOfPresentDriversProperty() {
        return numberOfPresentDriversProperty.getReadOnlyProperty();
    }

    public ReadOnlyIntegerProperty numberOfAllDriversProperty() {
        return totalNumberOfDriversProperty.getReadOnlyProperty();
    }

    private int computeNumberOfMembers(ListProperty<Visit> visits) {
        return visits.filtered(this.presentMembersPredicate).size();
    }

    private int computeTotalNumberOfMembers(ListProperty<Visit> visits) {
        return visits.filtered(new RemoveDuplicatePredicate(visits)).size();
    }

    public void update() {
        if (getEvent() != null) {

            numberOfPresentYouthMembersProperty.set(computeNumberOfMembers(getEvent().youthMemberVisitsProperty()));
            totalNumberOfYouthMembersProperty.set(computeTotalNumberOfMembers(getEvent().youthMemberVisitsProperty()));

            numberOfPresentYouthStaffProperty.set(computeNumberOfMembers(getEvent().youthStaffVisitsProperty()));
            totalNumberOfYouthStaffProperty.set(computeTotalNumberOfMembers(getEvent().youthStaffVisitsProperty()));

            numberOfPresentAdultStaffProperty.set(computeNumberOfMembers(getEvent().adultStaffVisitsProperty()));
            totalNumberOfAdultStaffProperty.set(computeTotalNumberOfMembers(getEvent().adultStaffVisitsProperty()));

            numberOfPresentDriversProperty.set(computeNumberOfMembers(getEvent().driverVisitsProperty()));
            totalNumberOfDriversProperty.set(computeTotalNumberOfMembers(getEvent().driverVisitsProperty()));

            computeSomeThing();
        }
    }

    private void computeSomeThing() {
        final FilteredList<Visit> youthMembers = getEvent().youthMemberVisitsProperty().filtered(visit -> {
            return visit.checkOutProperty().isNotNull().get();
        });
        final FilteredList<Visit> youthStaff = getEvent().youthStaffVisitsProperty().filtered(visit -> {
            return visit.checkOutProperty().isNotNull().get();
        });
        final Map<City, Integer> map = new HashMap<>();
        for (Visit visit : youthMembers) {
            if (
                    visit.personProperty().isNull().or(
                            visit.personProperty().get().addressProperty().isNull().or(
                                    visit.personProperty().get().addressProperty().get().cityProperty().isNull()
                            )
                    ).get()
                    ) {
                continue;
            }
            final City city = visit.personProperty().get().addressProperty().get().getCity();
            if (!map.containsKey(city)) {
                map.put(city, 1);
            } else {
                map.put(city, map.get(city) + 1);
            }
        }
        for (Visit visit : youthStaff) {
            if (
                    visit.personProperty().isNull().or(
                            visit.personProperty().get().addressProperty().isNull().or(
                                    visit.personProperty().get().addressProperty().get().cityProperty().isNull()
                            )
                    ).get()
                    ) {
                continue;
            }
            final City city = visit.personProperty().get().addressProperty().get().getCity();
            if (!map.containsKey(city)) {
                map.put(city, 1);
            } else {
                map.put(city, map.get(city) + 1);
            }
        }

    }

    private CubeEventModel getEvent() {
        return this.model;
    }
}
