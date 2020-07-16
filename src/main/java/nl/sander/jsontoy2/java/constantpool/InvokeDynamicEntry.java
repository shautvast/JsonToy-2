package nl.sander.jsontoy2.java.constantpool;

public class InvokeDynamicEntry extends ConstantPoolEntry {
    private final short bootstrapMethodAttrIndex;
    private final short nameAndTypeIndex;

    public InvokeDynamicEntry(short bootstrapMethodAttrIndex, short nameAndTypeIndex) {
        this.bootstrapMethodAttrIndex = bootstrapMethodAttrIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    @Override
    public String toString() {
        return "InvokeDynamicEntry{" +
                "bootstrapMethodAttrIndex=" + bootstrapMethodAttrIndex +
                ", nameAndTypeIndex=" + nameAndTypeIndex +
                '}';
    }
}
