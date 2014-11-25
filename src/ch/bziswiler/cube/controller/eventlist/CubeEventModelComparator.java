package ch.bziswiler.cube.controller.eventlist;

import ch.bziswiler.cube.model.address.Address;
import ch.bziswiler.cube.model.address.City;
import ch.bziswiler.cube.model.presentation.CubeEventModel;

import java.time.LocalDateTime;
import java.util.Comparator;

class CubeEventModelComparator implements Comparator<CubeEventModel> {

    private final SortField sortField;
    private final int direction;

    public CubeEventModelComparator(SortField sortField, SortDirection direction) {
        this.sortField = sortField;
        this.direction = direction == SortDirection.ASC ? 1 : -1;
    }

    @Override
    public int compare(CubeEventModel o1, CubeEventModel o2) {
        if (o1 == null) {
            if (o2 != null) {
                return this.direction;
            }
            return 0;
        } else if (o2 == null) {
            return this.direction * -1;
        }
        switch (sortField) {
            case NAME:
                return compareByName(o1, o2);
            case START:
                return compareByStart(o1, o2);
            case END:
                return compareByEnd(o1, o2);
            case CITY:
                return compareByCity(o1, o2);
            default:
                return this.direction * o1.toString().compareTo(o2.toString());
        }
    }

    private int compareByName(CubeEventModel o1, CubeEventModel o2) {
        return compareStrings(o1.getName(), o2.getName());
    }

    private int compareByStart(CubeEventModel o1, CubeEventModel o2) {
        return compareDates(o1.getStart(), o2.getStart());
    }

    private int compareByEnd(CubeEventModel o1, CubeEventModel o2) {
        return compareDates(o1.getEnd(), o2.getEnd());
    }

    private int compareByCity(CubeEventModel o1, CubeEventModel o2) {
        final Address a1 = o1.getAddress();
        final Address a2 = o2.getAddress();
        final City p1 = a1 == null ? null : a1.getCity();
        final City p2 = a2 == null ? null : a2.getCity();
        if (p1 == null) {
            if (p2 != null) {
                return this.direction;
            }
            return 0;
        } else if (p2 == null) {
            return this.direction * -1;
        }
        return compareStrings(p1.getName(), p2.getName());
    }

    private int compareStrings(String p1, String p2) {
        if (p1 == null) {
            if (p2 != null) {
                return this.direction;
            }
            return 0;
        } else if (p2 == null) {
            return this.direction * -1;
        }
        return this.direction * p1.compareTo(p2);
    }

    private int compareDates(LocalDateTime p1, LocalDateTime p2) {
        if (p1 == null) {
            if (p2 != null) {
                return this.direction;
            }
            return 0;
        } else if (p2 == null) {
            return this.direction * -1;
        }
        return this.direction * p1.compareTo(p2);
    }

    public static enum SortField {
        NAME("Name"), START("Start"), END("End"), CITY("Ort");
        private final String displayName;

        SortField(String displayName) {
            this.displayName = displayName;
        }


        @Override
        public String toString() {
            return displayName;
        }
    }

    public static enum SortDirection {
        ASC, DESC
    }
}
