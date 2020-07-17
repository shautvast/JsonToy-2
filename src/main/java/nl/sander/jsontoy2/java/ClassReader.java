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
        int interfacesCount = readU16(in);
        skip(in, interfacesCount * 2); // interfaces[u2;]

        readFields(in, builder);
        readMethods(in, builder);

        return builder.build();
    }

    private <T> void readConstantPool(DataInputStream in, ClassObject.Builder<T> builder) {
        int constantPoolCount = readU16(in);
        builder.constantPoolCount(constantPoolCount);
        for (int i = 1; i < constantPoolCount; i++) {
            builder.constantPoolEntry(readConstantPoolEntry(in));
        }
    }

    private <T> void readFields(DataInputStream in, ClassObject.Builder<T> builder) {
        int fieldInfoCount = readU16(in);
        builder.fieldInfoCount(fieldInfoCount);
        for (int i = 0; i < fieldInfoCount; i++) {
            builder.fieldInfo(readField(in));
        }
    }

    private <T> void readMethods(DataInputStream in, ClassObject.Builder<T> builder) {
        int methodInfoCount = readU16(in);
        builder.methodInfoCount(methodInfoCount);
        for (int i = 0; i < methodInfoCount; i++) {
            builder.methodInfo(readMethod(in));
        }
    }

    private <T> Info readField(DataInputStream in) {
        Info fieldInfo = new Info(readU16(in), readU16(in), readU16(in), readU16(in));
        for (int i = 0; i < fieldInfo.getAttributesCount(); i++) {
            fieldInfo.add(readAttribute(in));
        }

        return fieldInfo;
    }

    private <T> Info readMethod(DataInputStream in) {
        Info methodInfo = new Info(readU16(in), readU16(in), readU16(in), readU16(in));
        for (int i = 0; i < methodInfo.getAttributesCount(); i++) {
            methodInfo.add(readAttribute(in));
        }

        return methodInfo;
    }

    private AttributeInfo readAttribute(DataInputStream in) {
        int attributeNameIndex = readU16(in);
        int attributeLength = readS32(in);
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
            case 3: return new IntEntry(readS32(in));
            case 4: return new FloatEntry(readF32(in));
            case 5: return new LongEntry(readS64(in));
            case 6: return new DoubleEntry(readF64(in));
            case 7: return new ClassEntry(readU16(in));
            case 8: return new StringEntry(readU16(in));
            case 9: return new FieldRefEntry(readU16(in), readU16(in));
            case 10: return new MethodRefEntry(readU16(in), readU16(in));
            case 11: return new InterfaceMethodRefEntry(readU16(in), readU16(in));
            case 12: return new NameAndTypeEntry(readU16(in), readU16(in));
            case 15: return new MethodHandleEntry(readU16(in), readU16(in));
            case 16: return new MethodTypeEntry(readU16(in));
            case 18: return new InvokeDynamicEntry(readU16(in), readU16(in));
            case 19: return new ModuleEntry(readU16(in));
            case 20: return new PackageEntry(readU16(in));
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
