package nl.sander.jsontoy2.java;

import nl.sander.jsontoy2.java.constantpool.*;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class ClassReader extends DataReader {

    public <T> ClassObject<T> parse(Class<T> type) {
        DataInputStream in = new DataInputStream(new BufferedInputStream(type.getResourceAsStream(getResourceName(type))));
        expect(in, 0xCAFEBABE);

        ClassObject.Builder<T> builder = new ClassObject.Builder<>();

        skip(in, 4); // u2 minor_version, u2 major_version

        readConstantPool(in, builder);

        skip(in, 6); // u2 access_flags, u2 this_class, u2 super_class
        int interfacesCount = readUnsignedShort(in);
        skip(in, interfacesCount * 2); // interfaces[]
        
        readFields(in, builder);
        readMethods(in, builder);

        return builder.build();
    }

    private <T> void readConstantPool(DataInputStream in, ClassObject.Builder<T> builder) {
        int constantPoolCount = readUnsignedShort(in);
        builder.constantPoolCount(constantPoolCount);
        for (int i = 1; i < constantPoolCount; i++) {
            builder.constantPoolEntry(readConstantPoolEntry(in));
        }
    }

    private <T> void readFields(DataInputStream in, ClassObject.Builder<T> builder) {
        int fieldInfoCount = readUnsignedShort(in);
        builder.fieldInfoCount(fieldInfoCount);
        for (int i = 0; i < fieldInfoCount; i++) {
            builder.fieldInfo(readField(in));
        }
    }

    private <T> void readMethods(DataInputStream in, ClassObject.Builder<T> builder) {
        int methodInfoCount = readUnsignedShort(in);
        builder.methodInfoCount(methodInfoCount);
        for (int i = 0; i < methodInfoCount; i++) {
            builder.methodInfo(readMethod(in));
        }
    }

    private <T> Info readField(DataInputStream in) {
        Info fieldInfo = new Info(readUnsignedShort(in), readUnsignedShort(in), readUnsignedShort(in), readUnsignedShort(in));
        for (int i = 0; i < fieldInfo.getAttributesCount(); i++) {
            fieldInfo.add(readAttribute(in));
        }

        return fieldInfo;
    }

    private <T> Info readMethod(DataInputStream in) {
        Info methodInfo = new Info(readUnsignedShort(in), readUnsignedShort(in), readUnsignedShort(in), readUnsignedShort(in));
        for (int i = 0; i < methodInfo.getAttributesCount(); i++) {
            methodInfo.add(readAttribute(in));
        }

        return methodInfo;
    }

    private AttributeInfo readAttribute(DataInputStream in) {
        int attributeNameIndex = readUnsignedShort(in);
        int attributeLength = readInt(in);
        byte[] info;
        if (attributeLength > 0) {
            info = new byte[attributeLength];
            try {
                in.readFully(info);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            info = new byte[0];
        }
        return new AttributeInfo(attributeNameIndex, info);
    }

    private <T> ConstantPoolEntry readConstantPoolEntry(DataInputStream in) {
        byte tag = readByte(in);
        switch (tag) {
            case 1: return readUtf8Entry(in);
            case 2: throw new IllegalStateException("2: invalid classpool tag");
            case 3: return new IntEntry(readInt(in));
            case 4: return new FloatEntry(readFloat(in));
            case 5: return new LongEntry(readLong(in));
            case 6: return new DoubleEntry(readDouble(in));
            case 7: return new ClassEntry(readUnsignedShort(in));
            case 8: return new StringEntry(readUnsignedShort(in));
            case 9: return new FieldRefEntry(readShort(in), readShort(in));
            case 10: return new MethodRefEntry(readUnsignedShort(in), readUnsignedShort(in));
            case 11: return new InterfaceMethodRefEntry(readUnsignedShort(in), readUnsignedShort(in));
            case 12: return new NameAndTypeEntry(readUnsignedShort(in), readUnsignedShort(in));
            case 15: return new MethodHandleEntry(readUnsignedShort(in), readUnsignedShort(in));
            case 16: return new MethodTypeEntry(readUnsignedShort(in));
            case 18: return new InvokeDynamicEntry(readUnsignedShort(in), readUnsignedShort(in));
            default: throw new IllegalStateException("invalid classpool");
        }
    }

    protected <T> String getResourceName(Class<T> type) {
        StringBuilder typeName = new StringBuilder("/" + type.getName());

        for (int i = 0; i < typeName.length(); i++) {
            if (typeName.charAt(i) == '.') {
                typeName.setCharAt(i, '/');
            }
        }
        typeName.append(".class");
        return typeName.toString();
    }
}
