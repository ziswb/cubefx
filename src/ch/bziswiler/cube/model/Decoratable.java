package ch.bziswiler.cube.model;

public interface Decoratable<T> {

    <E extends Decorator<T>> E getDecorator(Class<E> decorator);
    <E extends Decorator<T>> void addDecorator(E decorator);
}
