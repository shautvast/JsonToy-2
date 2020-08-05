package nl.sander.jsontoy2;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Base class for generated readers
 *
 * @param <T> the type that the reader produces
 */
@SuppressWarnings("unused") /* these methods will be called from generated code*/
public abstract class JsonValueReader<T> {
    public abstract T read(Parser parser);

    protected boolean getBoolean(String fieldName, Map<String, ?> values) {
        Object value = values.get(fieldName);
        return value == null ? false : (Boolean) value;
    }

    protected int getInt(String fieldName, Map<String, ?> values) {
        Object value = values.get(fieldName);
        return value == null ? 0 : (Integer) value;
    }

    protected long getLong(String fieldName, Map<String, ?> values) {
        Object value = values.get(fieldName);
        return value == null ? 0L : (Long) value;
    }

    protected short getShort(String fieldName, Map<String, ?> values) {
        Object value = values.get(fieldName);
        return value == null ? (short) 0 : (Short) value;
    }

    protected byte getByte(String fieldName, Map<String, ?> values) {
        Object value = values.get(fieldName);
        return value == null ? (byte) 0 : (Byte) value;
    }

    protected float getFloat(String fieldName, Map<String, ?> values) {
        Object value = values.get(fieldName);
        return value == null ? 0F : (Float) value;
    }

    protected double getDouble(String fieldName, Map<String, ?> values) {
        Object value = values.get(fieldName);
        return value == null ? 0D : (Double) value;
    }

    protected Set<?> getSet(String fieldName, Map<String, ?> values) {
        Object value = values.get(fieldName);
        return value == null ? null : new HashSet<>((List<?>) value);
    }

}
