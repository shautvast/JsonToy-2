package nl.sander.jsontoy2.java;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ClassParser {

    public <T> ClassObject<T> parse(Class<T> type) {
        DataInputStream in = new DataInputStream(new BufferedInputStream(type.getResourceAsStream(getResourceName(type))));
        expect(in, 0xCAFEBABE);
        ClassObject.Builder<T> builder = new ClassObject.Builder<>();
        skip(in, 4); //skip version
        int constantPoolCount = readShort(in);
        builder.constantPoolCount(constantPoolCount);
        for (int i = 1; i < constantPoolCount; i++) {
            readConstantPoolEntry(in, builder);
        }
        return builder.build();
    }

    private <T> void readConstantPoolEntry(DataInputStream in, ClassObject.Builder<T> builder) {
        byte tag = readByte(in);
        switch (tag) {
            case 1: readUtf8(in, builder);
                break;
            case 2: throw new IllegalStateException("2: invalid classpool tag");
            case 3: builder.constantPoolEntry(readInt(in));
                break;
            case 4: builder.constantPoolEntry(readFloat(in));
                break;
            case 5: builder.constantPoolEntry(readLong(in));
                break;
            case 6: builder.constantPoolEntry(readDouble(in));
                break;
            case 7: builder.constantPoolClassEntry(readShort(in));
                break;
            case 8: builder.constantPoolStringEntry(readShort(in));
                break;
            case 9: builder.constantPoolFieldRefEntry(readShort(in), readShort(in));
                break;
            case 10: builder.constantPoolMethodRefEntry(readShort(in), readShort(in));
                break;
            case 11: builder.constantPoolInterfaceMethodRefEntry(readShort(in), readShort(in));
                break;
            case 12: builder.constantPoolNameAndTypeEntry(readShort(in), readShort(in));
                break;
            case 15: builder.constantPoolMethodHandleEntry(readShort(in), readShort(in));
                break;
            case 16: builder.constantPoolMethodTypeEntry(readShort(in));
                break;
            case 18: builder.constantPoolInvokeDynamicEntry(readShort(in), readShort(in));
                break;
        }
    }

    private void readUtf8(DataInputStream in, ClassObject.Builder<?> builder) {
        short length = readShort(in);
        String utf8 = readString(in, length);
        builder.constantPoolEntry(utf8);
    }

    private String readString(DataInputStream in, short length) {
        try {
            byte[] bytes = in.readNBytes(length);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private long skip(InputStream in, long bytecount) {
        try {
            return in.skip(bytecount);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private byte readByte(DataInputStream in) {
        try {
            return in.readByte();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private short readShort(DataInputStream in) {
        try {
            return in.readShort();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int readInt(DataInputStream in) {
        try {
            return in.readInt();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private float readFloat(DataInputStream in) {
        try {
            return in.readFloat();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private long readLong(DataInputStream in) {
        try {
            return in.readLong();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private double readDouble(DataInputStream in) {
        try {
            return in.readDouble();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void expect(DataInputStream in, int expected) {
        try {
            int i = in.readInt();
            if (i != expected) {
                throw new IllegalStateException("class file not valid");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> String getResourceName(Class<T> type) {
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
