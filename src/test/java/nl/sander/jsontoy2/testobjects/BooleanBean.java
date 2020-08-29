package nl.sander.jsontoy2.testobjects;

import java.util.Objects;

/*
 * test object
 */
public class BooleanBean {
    private boolean value;
    private Boolean value2;

    public BooleanBean() {
    }

    public BooleanBean(boolean value, Boolean value2) {
        this.value = value;
        this.value2 = value2;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public Boolean getValue2() {
        return value2;
    }

    public void setValue2(Boolean value2) {
        this.value2 = value2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooleanBean that = (BooleanBean) o;
        return value == that.value &&
                value2.equals(that.value2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, value2);
    }


}
