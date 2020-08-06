package nl.sander.jsontoy2;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

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

    /*
     * Creates a Set type for any implementation of it (that the containing bean needs),
     * unless it has no constructor using a java.util.Collection as single parameter.
     *
     * returntype is generic Set, object needs cast afterwards in generated code
     */
    protected Set<?> getSet(String fieldName, Class<? extends Set<?>> type, Map<String, ?> values) {
        List<?> value = (List<?>) values.get(fieldName);
        if (value == null) {
            return null;
        } else {
            try {
                if (type.equals(Set.class)) {
                    return new HashSet<>(value);
                } else {
                    Constructor<? extends Set<?>> constructor = type.getConstructor(Collection.class);
                    return constructor.newInstance(value);
                }
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new JsonParseException(e);
            }
        }
    }

    /*
     * Creates a List type for any implementation of it (that the containing bean needs),
     * unless it has no constructor using a java.util.Collection as single parameter.
     *
     * returntype is generic List, needs cast afterwards in generated code
     */
    protected List<?> getList(String fieldName, Class<?> listImplType, Map<String, ?> values) {
        List<?> value = (List<?>) values.get(fieldName);
        if (value == null) {
            return null;
        } else if (listImplType == ArrayList.class || listImplType.equals(List.class)) {
            return value;
        } else {
            try {
                Constructor<?> constructor = listImplType.getConstructor(Collection.class);
                return (List<?>) constructor.newInstance(value);
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new JsonParseException(e);
            }
        }
    }

    protected Object getArray(String fieldName, Class<?> arrayType, Map<String, ?> values) {
        List<?> value = (List<?>) values.get(fieldName);
        if (value == null) {
            return new Object[]{};
        } else {
            Object[] array = (Object[]) Array.newInstance(arrayType, value.size());
            int index = 0;
            for (Object element : value) {
                array[index] = value.get(index++);
            }
            //TODO this can probably be done smarter
            return array;
        }
    }
}
