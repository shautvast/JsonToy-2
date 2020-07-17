package nl.sander.jsontoy2.java;

import nl.sander.jsontoy2.java.constantpool.Utf8Entry;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class DataReader {

    protected long readS64(DataInputStream in) {
        try {
            return in.readLong();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected float readF32(DataInputStream in) {
        try {
            return in.readFloat();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    protected double readF64(DataInputStream in) {
        try {
            return in.readDouble();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected int readU16(DataInputStream in) {
        try {
            return in.readUnsignedShort();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected int readS32(DataInputStream in) {
        try {
            return in.readInt();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected Utf8Entry readUtf8Entry(DataInputStream in) {
        int length = readU16(in);
        return new Utf8Entry(readString(in, length));
    }

    protected String readString(DataInputStream in, int length) {
        try {
            byte[] bytes = in.readNBytes(length);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    protected byte readByte(DataInputStream in) {
        try {
            return in.readByte();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void expect(DataInputStream in, int expected) {
        try {
            int i = in.readInt();
            if (i != expected) {
                throw new IllegalStateException("class file not valid");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected long skip(InputStream in, long bytecount) {
        try {
            return in.skip(bytecount);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
