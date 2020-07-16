package nl.sander.jsontoy2.java.constantpool;

public class FieldRefEntry extends ConstantPoolEntry {
    private final short classIndex;
    private final short nameAndTypeIndex;

    public FieldRefEntry(short classIndex, short nameAndTypeIndex) {
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    public short getClassIndex() {
        return classIndex;
    }

    public short getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }

    @Override
    public String toString() {
        return "FieldRefEntry{" +
                "classIndex=" + classIndex +
                ", nameAndTypeIndex=" + nameAndTypeIndex +
                '}';
    }
}
