package nl.sander.jsontoy2.java.constantpool;

public class MethodRefEntry extends ConstantPoolEntry {
    private final short classIndex;
    private final short nameAndTypeIndex;

    public MethodRefEntry(short classIndex, short nameAndTypeIndex) {
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    @Override
    public String toString() {
        return "MethodRefEntry{" +
                "classIndex=" + classIndex +
                ", nameAndTypeIndex=" + nameAndTypeIndex +
                '}';
    }
}
