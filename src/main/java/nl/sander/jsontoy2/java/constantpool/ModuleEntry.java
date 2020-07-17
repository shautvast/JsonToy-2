package nl.sander.jsontoy2.java.constantpool;

import nl.sander.jsontoy2.java.constantpool.ConstantPoolEntry;

public class ModuleEntry extends ConstantPoolEntry {
    private final int nameIndex;

    public ModuleEntry(int nameIndex) {
        this.nameIndex = nameIndex;
    }
}
