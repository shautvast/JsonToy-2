package nl.sander.jsontoy2.testobjects;

import java.util.List;

/*
 * test object
 */
public class BooleanListBean {
    private List<Boolean> value;

    public List<Boolean> getValue() {
        return value;
    }

    public void setValue(List<Boolean> value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "BooleanListBean{" +
                "value=" + value +
                '}';
    }
}
