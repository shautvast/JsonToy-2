package nl.sander.jsontoy2.java.constantpool;

public class NameAndTypeEntry extends ConstantPoolEntry {
    private final short nameIndex;
    private final short typeIndex;

    public NameAndTypeEntry(short nameIndex, short typeIndex) {
        this.nameIndex = nameIndex;
        this.typeIndex = typeIndex;
    }

    public short getNameIndex() {
        return nameIndex;
    }

    public short getTypeIndex() {
        return typeIndex;
    }

    @Override
    public String toString() {
        return "NameAndTypeEntry{" +
                "nameIndex=" + nameIndex +
                ", typeIndex=" + typeIndex +
                '}';
    }
}
