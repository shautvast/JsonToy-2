package nl.sander.jsontoy2.java.constantpool;

public class MethodTypeEntry extends ConstantPoolEntry {
    private final int descriptorIndex;

    public MethodTypeEntry(int descriptorIndex) {
        this.descriptorIndex = descriptorIndex;
    }

    @Override
    public String toString() {
        return "MethodTypeEntry{" +
                "descriptorIndex=" + descriptorIndex +
                '}';
    }
}
