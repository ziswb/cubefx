package ch.bziswiler.cube.model.event;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class KeyValue<K, V> {

    private ObjectProperty<K> key;
    private ObjectProperty<V> value;

    public KeyValue(K key, V value) {
        keyProperty().set(key);
        valueProperty().set(value);
    }

    public ObjectProperty<K> keyProperty() {
        if (this.key == null) {
            this.key = new SimpleObjectProperty<>();
        }
        return this.key;
    }

    public ObjectProperty<V> valueProperty() {
        if (this.value == null) {
            this.value = new SimpleObjectProperty<>();
        }
        return this.value;
    }

    public final V getValue() {
        return value.get();
    }

    public final K getKey() {
        return key.get();
    }
}
