package nl.sander.jsontoy2.testobjects;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/*
 * test object
 */
public class StringMapBean {

    private Map<String, String> map = new HashMap<>();

    public StringMapBean() {
        this.map = new HashMap<>();
    }

    public StringMapBean(String... keysAndValues) {
        if (keysAndValues.length % 2 == 1) {
            throw new IllegalArgumentException("uneven number of arguments is not allowed here");
        }
        for (int i = 0; i < keysAndValues.length; ) {
            String key = keysAndValues[i++];
            String value = keysAndValues[i++];
            map.put(key, value);
        }
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public Map<String, String> getMap() {
        return map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringMapBean that = (StringMapBean) o;
        return map.equals(that.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map);
    }
}
