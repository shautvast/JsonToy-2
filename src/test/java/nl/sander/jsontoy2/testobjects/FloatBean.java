package nl.sander.jsontoy2.testobjects;

/*
 * test object
 */
public class FloatBean {
    private float value;
    private Float value2;

    public FloatBean() {
    }

    public FloatBean(float value, Float value2) {
        this.value = value;
        this.value2 = value2;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Float getValue2() {
        return value2;
    }

    public void setValue2(Float value2) {
        this.value2 = value2;
    }
}
