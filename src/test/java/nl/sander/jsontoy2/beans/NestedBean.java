package nl.sander.jsontoy2.beans;

import java.util.Objects;

public class NestedBean {

    private InnerBean value;
    private String empty;

    public NestedBean() {
    }

    public NestedBean(InnerBean value) {
        this.value = value;
    }

    public InnerBean getValue() {
        return value;
    }

    public void setValue(InnerBean value) {
        this.value = value;
    }

    public void setEmpty(String empty) {
        this.empty = empty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NestedBean that = (NestedBean) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "NestedBean{" +
                "value=" + value +
                '}';
    }
}
