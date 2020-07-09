package nl.sander.jsontoy2;

import java.util.function.Consumer;

/*
 * Option that may contain null
 */
public class Maybe<T> {
    private final boolean hasValue;
    private final T value;

    private Maybe(boolean hasValue, T value) {
        this.hasValue = hasValue;
        this.value = value;
    }

    public static <T> Maybe<T> none() {
        return new Maybe<>(false, null);
    }

    public static <T> Maybe<T> of(T value) {
        return new Maybe<>(true, value);
    }

    public boolean isPresent() {
        return hasValue;
    }

    public T get() {
        return value;
    }

    public void ifPresent(Consumer<T> action) {
        if (hasValue) {
            action.accept(value);
        }
    }


}