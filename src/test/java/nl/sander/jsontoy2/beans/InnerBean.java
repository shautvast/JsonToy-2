package nl.sander.jsontoy2.beans;

import java.util.Objects;

public class InnerBean {
    private String value;

    public InnerBean() {
    }

    public InnerBean(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InnerBean innerBean = (InnerBean) o;
        return value.equals(innerBean.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "InnerBean{" +
                "value='" + value + '\'' +
                '}';
    }
}
