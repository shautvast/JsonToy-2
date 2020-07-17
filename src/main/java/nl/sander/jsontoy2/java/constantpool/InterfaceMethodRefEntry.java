package nl.sander.jsontoy2.java.constantpool;

public class InterfaceMethodRefEntry extends ConstantPoolEntry {
    private final int classIndex;
    private final int nameAndTypeIndex;

    public InterfaceMethodRefEntry(int classIndex, int nameAndTypeIndex) {
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
