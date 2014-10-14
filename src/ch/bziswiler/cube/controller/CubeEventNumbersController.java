package ch.bziswiler.cube.controller;

public class CubeEventNumbersController extends EventControllerScaffold {

    @Override
    protected void initialize(CubeEventModel newValue) {
        System.out.println("this = " + this);
    }

    @Override
    protected void dispose(CubeEventModel oldValue) {
        System.out.println("oldValue = " + oldValue);
    }
}
