package nl.sander.jsontoy2.java.constantpool;

public class MethodHandleEntry extends ConstantPoolEntry {
    private final int referenceKind;
    private final int referenceIndex;

    public MethodHandleEntry(int referenceKind, int referenceIndex) {
        this.referenceKind = referenceKind;
        this.referenceIndex = referenceIndex;
    }

    @Override
    public String toString() {
        return "MethodHandleEntry{" +
                "referenceKind=" + referenceKind +
                ", referenceIndex=" + referenceIndex +
                '}';
    }
}
