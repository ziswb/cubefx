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
    private ReadOnlyIntegerWrapper numberOfAllYouthMembersProperty = new ReadOnlyIntegerWrapper();
    private ReadOnlyIntegerWrapper numberOfAllYouthStaffProperty = new ReadOnlyIntegerWrapper();
    private ReadOnlyIntegerWrapper numberOfAllAdultStaffProperty = new ReadOnlyIntegerWrapper();
    private ReadOnlyIntegerWrapper numberOfAllDriversProperty = new ReadOnlyIntegerWrapper();

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
        return numberOfAllYouthMembersProperty.getReadOnlyProperty();
    }

    public ReadOnlyIntegerProperty numberOfPresentYouthStaffProperty() {
        return numberOfPresentYouthStaffProperty.getReadOnlyProperty();
    }

    public ReadOnlyIntegerProperty numberOfAllYouthStaffProperty() {
        return numberOfAllYouthStaffProperty.getReadOnlyProperty();
    }

    public ReadOnlyIntegerProperty numberOfPresentAdultStaffProperty() {
        return numberOfPresentAdultStaffProperty.getReadOnlyProperty();
    }

    public ReadOnlyIntegerProperty numberOfAllAdultStaffProperty() {
        return numberOfAllAdultStaffProperty.getReadOnlyProperty();
    }

    public ReadOnlyIntegerProperty numberOfPresentDriversProperty() {
        return numberOfPresentDriversProperty.getReadOnlyProperty();
    }

    public ReadOnlyIntegerProperty numberOfAllDriversProperty() {
        return numberOfAllDriversProperty.getReadOnlyProperty();
    }

    private int computeNumberOfPresentMembers(ListProperty<Visit> visits) {
        return visits.filtered(this.presentMembersPredicate).size();
    }

    private int computeNumberOfAllMembers(ListProperty<Visit> visits) {
        return visits.filtered(new RemoveDuplicatePersonsPredicate(visits)).size();
    }

    public void update() {
        if (getEvent() != null) {

            numberOfPresentYouthMembersProperty.set(computeNumberOfPresentMembers(getEvent().youthMemberVisitsProperty()));
            numberOfAllYouthMembersProperty.set(computeNumberOfAllMembers(getEvent().youthMemberVisitsProperty()));

            numberOfPresentYouthStaffProperty.set(computeNumberOfPresentMembers(getEvent().youthStaffVisitsProperty()));
            numberOfAllYouthStaffProperty.set(computeNumberOfAllMembers(getEvent().youthStaffVisitsProperty()));

            numberOfPresentAdultStaffProperty.set(computeNumberOfPresentMembers(getEvent().adultStaffVisitsProperty()));
            numberOfAllAdultStaffProperty.set(computeNumberOfAllMembers(getEvent().adultStaffVisitsProperty()));

            numberOfPresentDriversProperty.set(computeNumberOfPresentMembers(getEvent().driverVisitsProperty()));
            numberOfAllDriversProperty.set(computeNumberOfAllMembers(getEvent().driverVisitsProperty()));

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
