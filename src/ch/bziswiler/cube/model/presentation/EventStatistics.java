package ch.bziswiler.cube.model.presentation;

import ch.bziswiler.cube.controller.RemoveDuplicatePersonsPredicate;
import ch.bziswiler.cube.model.presentation.CubeEventModel;
import ch.bziswiler.cube.model.address.City;
import ch.bziswiler.cube.model.event.KeyValue;
import ch.bziswiler.cube.model.event.Visit;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class EventStatistics {

    private final CubeEventModel model;

    private final Predicate<Visit> presentMembersPredicate = visit -> visit.checkOutProperty().isNull().get();

    private ReadOnlyIntegerWrapper numberOfPresentYouthMembersProperty = new ReadOnlyIntegerWrapper();
    private ReadOnlyIntegerWrapper numberOfPresentYouthStaffProperty = new ReadOnlyIntegerWrapper();
    private ReadOnlyIntegerWrapper numberOfPresentAdultStaffProperty = new ReadOnlyIntegerWrapper();
    private ReadOnlyIntegerWrapper numberOfPresentDriversProperty = new ReadOnlyIntegerWrapper();
    private ReadOnlyIntegerWrapper numberOfAllYouthMembersProperty = new ReadOnlyIntegerWrapper();
    private ReadOnlyIntegerWrapper numberOfAllYouthStaffProperty = new ReadOnlyIntegerWrapper();
    private ReadOnlyIntegerWrapper numberOfAllAdultStaffProperty = new ReadOnlyIntegerWrapper();
    private ReadOnlyIntegerWrapper numberOfAllDriversProperty = new ReadOnlyIntegerWrapper();
    private ReadOnlyListWrapper<KeyValue<String, Integer>> visitsDigest = new ReadOnlyListWrapper<>(FXCollections.observableArrayList());

    public EventStatistics(CubeEventModel model) {
        this.model = model;
    }

    public ReadOnlyListProperty<KeyValue<String, Integer>> visitsDigest() {
        return visitsDigest.getReadOnlyProperty();
    }

    public ReadOnlyIntegerProperty numberOfPresentYouthMembersProperty() {
        return numberOfPresentYouthMembersProperty.getReadOnlyProperty();
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

            computeVisitsDigest();
        }
    }

    private CubeEventModel getEvent() {
        return this.model;
    }

    private int computeNumberOfPresentMembers(ListProperty<Visit> visits) {
        return visits.filtered(this.presentMembersPredicate).size();
    }

    private int computeNumberOfAllMembers(ListProperty<Visit> visits) {
        return computeAllMembers(visits).size();
    }

    private void computeVisitsDigest() {
        final FilteredList<Visit> youthMembers = computeAllMembers(getEvent().youthMemberVisitsProperty());
        final FilteredList<Visit> youthStaff = computeAllMembers(getEvent().youthStaffVisitsProperty());
        final Map<City, Integer> map = new HashMap<>();
        countMembersPerCity(youthMembers, map);
        countMembersPerCity(youthStaff, map);
        updateDigest(map);
    }

    private FilteredList<Visit> computeAllMembers(ListProperty<Visit> visits) {
        return visits.filtered(new RemoveDuplicatePersonsPredicate(visits));
    }

    private void countMembersPerCity(FilteredList<Visit> visits, Map<City, Integer> map) {
        for (Visit visit : visits) {
            if (hasNoCity(visit)) {
                continue;
            }
            final City city = visit.personProperty().get().addressProperty().get().getCity();
            increaseCounter(map, city);
        }
    }

    private void updateDigest(Map<City, Integer> map) {
        visitsDigest.clear();
        for (Map.Entry<City, Integer> entry : map.entrySet()) {
            visitsDigest.get().add(new KeyValue<>(entry.getKey().getName(), entry.getValue()));
        }
    }

    private boolean hasNoCity(Visit visit) {
        return visit.personProperty().isNull().or(
                visit.personProperty().get().addressProperty().isNull().or(
                        visit.personProperty().get().addressProperty().get().cityProperty().isNull()
                )
        ).get();
    }

    private void increaseCounter(Map<City, Integer> map, City city) {
        if (!map.containsKey(city)) {
            map.put(city, 1);
        } else {
            final int value = map.get(city) + 1;
            map.put(city, value);
        }
    }
}
