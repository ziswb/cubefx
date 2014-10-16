package ch.bziswiler.cube.model.event;

public class StringTuple {

    private final String key;
    private final String value;

    public StringTuple(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }
}
