package nl.sander.jsontoy2.java;

import java.util.HashSet;
import java.util.Set;

public class Info {
    private final int accesFlags;
    private final int nameIndex;
    private final int descriptorIndex;
    private final int attributesCount;
    private final Set<AttributeInfo> attributeInfos = new HashSet<>();


    public Info(int accesFlags, int nameIndex, int descriptorIndex, int attributesCount) {
        this.accesFlags = accesFlags;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.attributesCount = attributesCount;
    }

    public int getNameIndex() {
        return nameIndex;
    }

    public int getDescriptorIndex() {
        return descriptorIndex;
    }

    void add(AttributeInfo attributeInfo) {
        attributeInfos.add(attributeInfo);
    }

    public int getAttributesCount() {
        return attributesCount;
    }
}