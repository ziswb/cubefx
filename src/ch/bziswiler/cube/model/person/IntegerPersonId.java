package ch.bziswiler.cube.model.person;

import java.util.Objects;

public class IntegerPersonId implements PersonId<Integer> {

    private final Integer id;

    public IntegerPersonId(Integer id) {
        this.id = id;
    }

    public static PersonId<Integer> fromString(String id) {
        return new IntegerPersonId(Integer.valueOf(id));
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    @Override
    // todo falsch
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof IntegerPersonId) {
            return Objects.equals(this.id, ((IntegerPersonId) obj).getId());
        }
        return false;
    }

    @Override
    public Integer getId() {
        return id;
    }
}
