package ch.bziswiler.cube.controller.eventlist;

import ch.bziswiler.cube.model.presentation.CubeEventModel;

import java.util.Comparator;

class CubeEventModelComparator implements Comparator<CubeEventModel> {

    private final int direction;

    public CubeEventModelComparator(int direction) {
        this.direction = direction;
    }

    @Override
    public int compare(CubeEventModel o1, CubeEventModel o2) {
        if (o1 == null) {
            if (o2 != null) {
                return this.direction;
            } else {
                return 0;
            }
        } else {
            if (o2 == null) {
                return this.direction * -1;
            }
        }
        return this.direction * o1.toString().compareTo(o2.toString());
//                this.direction * return o1.compareTo(o2);
    }
}
