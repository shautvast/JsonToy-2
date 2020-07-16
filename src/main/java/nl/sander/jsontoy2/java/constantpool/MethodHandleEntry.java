package nl.sander.jsontoy2.java.constantpool;

public class MethodHandleEntry extends ConstantPoolEntry {
    private final short referenceKind;
    private final short referenceIndex;

    public MethodHandleEntry(short referenceKind, short referenceIndex) {
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
