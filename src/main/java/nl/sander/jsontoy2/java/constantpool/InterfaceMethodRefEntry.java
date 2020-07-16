package nl.sander.jsontoy2.java.constantpool;

public class InterfaceMethodRefEntry extends ConstantPoolEntry {
    private final short classIndex;
    private final short nameAndTypeIndex;

    public InterfaceMethodRefEntry(short classIndex, short nameAndTypeIndex) {
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    @Override
    public String toString() {
        return "InterfaceMethodRefEntry{" +
                "classIndex=" + classIndex +
                ", nameAndTypeIndex=" + nameAndTypeIndex +
                '}';
    }
}
