package nl.sander.jsontoy2.java;

import java.util.Objects;

public class Method {

    private final String name;
    private final String type;

    public Method(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Method field = (Method) o;
        return name.equals(field.name) &&
                type.equals(field.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }

    @Override
    public String toString() {
        return "Method{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
