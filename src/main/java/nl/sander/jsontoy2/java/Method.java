package nl.sander.jsontoy2.java;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class Method implements Comparable<Method> {

    private final String name;
    private final List<String> parameterTypes;
    private final String returnType;

    public Method(String name, List<String> parameterTypes, String returnType) {
        this.name = name;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
    }

    public String getName() {
        return name;
    }

    public String getReturnType() {
        return returnType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Method field = (Method) o;
        return name.equals(field.name) &&
                returnType.equals(field.returnType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, returnType);
    }

    @Override
    public String toString() {
        return "Method{" +
                "name='" + name + '\'' +
                ", parameterTypes=" + parameterTypes +
                ", returnType='" + returnType + '\'' +
                '}';
    }

    @Override
    public int compareTo(@NotNull Method o) {
        return name.compareTo(o.name);
    }
}
