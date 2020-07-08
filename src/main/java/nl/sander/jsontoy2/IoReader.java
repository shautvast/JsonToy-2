package nl.sander.jsontoy2;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.*;

public class IoReader {
    private static final int HEX_RADIX = 16;
    private final InputStream inputStream;
    private final ByteBuf characterBuffer = new ByteBuf();
    private final ByteBuf encodedCodePointBuffer = new ByteBuf(4);
    private final ByteBuffer convertBuffer = ByteBuffer.allocate(4);
    private boolean escaping = false;
    private boolean encoded = false;
    private byte current;
    private int linecount = 0;

    protected IoReader(InputStream inputStream) {
        this.inputStream = inputStream;
        advance();
    }

    protected IoReader(String jsonString) {
        this.inputStream = new ByteArrayInputStream(jsonString.getBytes());
        advance();
    }

    public Integer readInteger() {
        String value = readNumeric();
        return Double.valueOf(value).intValue();
    }

    public Long readLong() {
        String value = readNumeric();
        return Long.parseLong(value);
    }

    public Float readFloat() {
        String value = readNumeric();
        return Float.parseFloat(value);
    }

    public Double readDouble() {
        String value = readNumeric();
        return Double.parseDouble(value);
    }

    public Short readShort() {
        String value = readNumeric();
        return Short.parseShort(value);
    }

    public Byte readByte() {
        String value = readNumeric();
        return Byte.parseByte(value);
    }

    public Character readCharacter() {
        eatUntil('\"');
        char currentChar = (char) current;
        eatUntil('\"');
        return currentChar;
    }

    public Boolean readBoolean() {
        characterBuffer.clear();
        while (Character.isAlphabetic(current)) {
            characterBuffer.add(current);
            advance();
        }

        return characterBuffer.toString().equalsIgnoreCase("TRUE");
    }

    boolean isNumeric(int c) {
        return Character.isDigit(c) || c == '.' || c == 'e' || c == '-' || c == '+';
    }

    private String readNumeric() {
        characterBuffer.clear();
        if (current == '-') {
            characterBuffer.add(current);
            advance();
        }
        while (current > -1 && isNumeric(current)) {
            characterBuffer.add(current);
            advance();
        }
        return characterBuffer.toString();
    }

    public List<?> readList() {
        skipWhitespace();
        if (current != '[') {
            throw new JsonReadException("no list found");
        }
        List<Object> list = new ArrayList<>();
        advance();
        while (current != -1 && current != ']') {
            Optional<Object> maybeValue = readValue();
            if (maybeValue.isEmpty()) {
                break;
            } else {
                list.add(maybeValue.get());
                eatUntil(',');
            }
        }

        return list;
    }

    public Map<?, ?> readMap() {
        HashMap<Object, Object> map = new HashMap<>();
        skipWhitespace();
        if (current != '{') {
            throw new JsonReadException("no map found");
        }
        while (current != -1 && current != '}') {
            skipWhitespace();
            if (current == '"') {
                String key = readString();
                eatUntil(':');
                skipWhitespace();
                Optional<Object> maybeValue = readValue();
                maybeValue.ifPresent(o -> map.put(key, o));
                eatUntil(',');
            } else {
                advance();
            }
        }
        return map;
    }

    private Optional<Object> readValue() {
        Object value;
        skipWhitespace();
        if (current == ']' || current == '}') {
            return Optional.empty();
        } else if (current == '[') {
            value = readList();
        } else if (current == '{') {
            value = readMap();
        } else if (current == '\"') {
            value = readString();
        } else if (current == 'T' || current == 't' || current == 'F' || current == 'f') {
            value = readBoolean();
        } else {
            String numeric = readNumeric();
            double doubleValue = Double.parseDouble(numeric);
            if ((int) doubleValue == doubleValue) {
                value = (int) doubleValue;
            } else {
                value = doubleValue;
            }
        }
        return Optional.of(value);
    }

    public String readString() {
        eatUntil('\"');

        characterBuffer.clear();
        boolean endOfString = false;
        while (current > -1 && !endOfString) {
            if (current == '\\' && !escaping) {
                escaping = true;
            } else {
                if (!escaping) {
                    // regular character
                    if (current != '\"') {
                        characterBuffer.add(current);
                    } else {
                        endOfString = true;
                    }
                } else {
                    // unicode codepoint
                    if (current == 'u') {
                        encoded = true;
                    } else if (current == 'n') {
                        linecount++;
                        characterBuffer.add("\n".getBytes());
                        escaping = false;
                    } else if (current == '\"') {
                        characterBuffer.add("\"".getBytes());
                        escaping = false;
                    } else if (current == '\'') {
                        throw new JsonReadException("illegal escaped quote in line " + linecount);
                    } else {
                        if (encoded) {
                            // load next 4 characters in special buffer to convert to int
                            encodedCodePointBuffer.add(current);
                            if (encodedCodePointBuffer.length() == 4) {
                                byte[] bytes = parseCodePoint();
                                characterBuffer.add(bytes);
                                encoded = false;
                                escaping = false;
                            }
                        }
                    }
                }
            }
            advance();
        }

        return characterBuffer.toString();
    }

    private byte[] parseCodePoint() {
        String hex = encodedCodePointBuffer.toString();
        int codepoint = Integer.parseInt(hex, HEX_RADIX);
        convertBuffer.clear();
        encodedCodePointBuffer.clear();
        return convertBuffer.putInt(codepoint).array();
    }

    void advance() {
        try {
            current = (byte) inputStream.read();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    void skipWhitespace() {
        try {
            while (current > -1 && Character.isWhitespace(current)) {
                current = (byte) inputStream.read();
            }
            if (current == -1) {
                throw new JsonReadException("end of source reached");
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    String eatUntil(char until) {
        characterBuffer.clear();

        while (current > -1 && (current != until | Character.isWhitespace(current))) {
            characterBuffer.add(current);
            advance();
        }
        advance();
        return characterBuffer.toString();
    }
}
