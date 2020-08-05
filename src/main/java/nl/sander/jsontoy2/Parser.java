package nl.sander.jsontoy2;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Reads the input as a stream.
 * Implements parsing the lowlevel types (primitives, list, map).
 * <p>
 * Not threadsafe, not meant to be used directly
 */
public class Parser extends Lexer {

    private static final int HEX_RADIX = 16;
    private final ByteBuf encodedCodePointBuffer = new ByteBuf(4);
    private boolean escaping = false;
    private boolean encoded = false;
    private int linecount = 0;

    protected Parser(InputStream inputStream) {
        super(inputStream);
        advance();
    }

    public void init(InputStream inputStream) {
        this.inputStream = inputStream;
        linecount = 0;
        charCount = 0;
        escaping = false;
        encoded = false;
        encodedCodePointBuffer.clear();
        characterBuffer.clear();
        advance();
    }

    public Integer parseInteger() {
        final String value = parseNumber();
        return Integer.valueOf(value);
    }

    public Long parseLong() {
        final String value = parseNumber();
        return Long.parseLong(value);
    }

    public Float parseFloat() {
        final String value = parseNumber();
        return Float.parseFloat(value);
    }

    public Double parseDouble() {
        final String value = parseNumber();
        return Double.parseDouble(value);
    }

    public Short parseShort() {
        final String value = parseNumber();
        return Short.parseShort(value);
    }

    public Byte parseByte() {
        final String value = parseNumber();
        return Byte.parseByte(value);
    }

    public Character parseCharacter() {
        final String string = parseString();
        return string.charAt(0);
    }

    public Boolean parseBoolean() {
        characterBuffer.clear();
        while (Character.isAlphabetic(current)) {
            characterBuffer.add(current);
            advance();
        }

        final String maybeBoolean = characterBuffer.toString();
        boolean returnValue;
        if ((returnValue = maybeBoolean.equals("true")) || maybeBoolean.equals("false")) {
            return returnValue;
        } else {
            throw new JsonParseException("Illegal boolean value: " + maybeBoolean);
        }
    }

    boolean isPartOfNumber(int c) {
        return Character.isDigit(c) || c == '.' || c == 'E' || c == 'e' || c == '-' || c == '+';
    }

    private String parseNumber() {
        characterBuffer.clear();
        if (current == '-') {
            characterBuffer.add(current);
            advance();
        }
        while (current > -1 && isPartOfNumber(current)) {
            characterBuffer.add(current);
            advance();
        }
        return characterBuffer.toString();
    }

    public List<?> parseArray() {
        skipWhitespace();
        if (current != '[') {
            throw new JsonParseException("no list found");
        }
        final List<Object> list = new ArrayList<>();
        advance();
        while (current != -1 && current != ']') {
            final Maybe<Object> maybeValue = parseValue();
            if (!maybeValue.isPresent()) {
                break;
            } else {
                list.add(maybeValue.get());
                eatUntil(',');
            }
        }

        return list;
    }

    public Map<?, ?> parseObject() {
        return parseObject(null);
    }

    public Map<?, ?> parseObject(Class<?> type) {
        final HashMap<Object, Object> map = new HashMap<>();
        skipWhitespace();
        if (current != '{') {
            throw new JsonParseException("no map found");
        }
        advance();
        while (current != -1 && current != '}') {
            skipWhitespace();
            if (current == '"') {
                final String key = parseString();
                skipWhitespace();
                if (current == ':') {
                    advance();
                } else {
                    throw new JsonParseException("expected colon");
                }
                skipWhitespace();
                final Maybe<Object> maybeValue;
                try {
                    maybeValue = type == null ? parseValue() : parseValue(type.getDeclaredField(key).getType());
                    maybeValue.ifPresent(value -> map.put(key, value));
                } catch (NoSuchFieldException e) {
                    throw new JsonParseException(e);
                }
            }
            advance();
            skipWhitespace();
        }
        return map;
    }

    public Object parseAny() {
        final Maybe<Object> maybe = parseValue();
        if (maybe.isPresent()) {
            return maybe.get();
        } else {
            throw new JsonParseException("no value found");
        }
    }

    private Maybe<Object> parseValue() {
        final Object value;
        skipWhitespace();
        switch (current) {
            case ']':
            case '}': return Maybe.none();
            case '[': value = parseArray();
                break;
            case '{': value = parseObject();
                break;
            case '\"': value = parseString();
                break;
            case 'T':
            case 't':
            case 'F':
            case 'f': value = parseBoolean();
                break;
            case 'n': value = readNull();
                break;
            default: String numeric = parseNumber();
                double doubleValue = Double.parseDouble(numeric);
                if ((long) doubleValue == doubleValue) {
                    value = (long) doubleValue;
                } else {
                    value = doubleValue;
                }
        }
        return Maybe.of(value);
    }

    private Maybe<Object> parseValue(Class<?> type) {
        final Object value;
        skipWhitespace();

        JsonValueReader<?> reader = ReaderFactory.getReader(type);
        if (reader != null) {
            value = reader.read(this);
        } else {
            value = parseObject();
        }

        return Maybe.of(value);
    }

    private Object readNull() {
        expect(() -> new JsonParseException("Expected 'null', encountered " + (char) current), 'n', 'u', 'l', 'l');
        return null;
    }

    public String parseString() {
        eatUntil('\"');

        characterBuffer.clear();
        boolean endOfString = false;
        while (current > -1 && !endOfString) {
            if (current == '\\' && !escaping) {
                escaping = true;
            } else {
                if (escaping) {
                    parseEscapedSequence();
                } else {
                    endOfString = addOrEndOfString();
                }
            }
            advance();
        }

        return characterBuffer.toString();
    }

    private void parseEscapedSequence() {
        // unicode codepoint
        if (encoded) {
            parseEncoded();
        } else {
            parseNonEncodedEscapes();
        }
    }

    private void parseNonEncodedEscapes() {
        switch (current) {
            case '\\': characterBuffer.add("\\".getBytes()); // backslash
                break;
            case '/': characterBuffer.add("/".getBytes()); // / the infamous escaped slash
                break;
            case 'b': characterBuffer.add((byte) 8); // backspace
                break;
            case 'f': characterBuffer.add((byte) 12); // formfeed
                break;
            case 't': characterBuffer.add((byte) 9); // tab
                break;
            case 'u': encoded = true; // \\ u hex hex hex hex encoded utf16/32
                break;
            case 'n': linecount++; // newline
                characterBuffer.add("\n".getBytes());
                escaping = false;
                break;
            case '\"': characterBuffer.add("\"".getBytes()); // quote
                escaping = false;
                break;
            case '\'': throw new JsonParseException("illegal escaped quote in line " + linecount);
        }
    }

    private void parseEncoded() {
        final StringBuilder buf = new StringBuilder();
        final char codePoint = parseCodePoint();
        buf.append(codePoint);
        if (Character.isHighSurrogate(codePoint)) {
            expect(() -> new JsonParseException("Invalid unicode codepoint at line " + linecount), '\\', 'u');
            char lowSurrogate = parseCodePoint();
            if (Character.isLowSurrogate(lowSurrogate)) {
                buf.append(lowSurrogate);
            }
        }
        characterBuffer.add(buf.toString().getBytes());
        encoded = false;
        escaping = false;
    }

    // load next 4 characters in special buffer to convert to int
    private char parseCodePoint() {
        encodedCodePointBuffer.clear();
        for (int i = 0; i < 4; i++) {
            encodedCodePointBuffer.add(current);
            advance();
        }
        return (char) Integer.parseInt(encodedCodePointBuffer.toString(), HEX_RADIX);
    }

    private boolean addOrEndOfString() {
        // regular character
        if (current != '\"') {
            characterBuffer.add(current);
            return false;
        } else {
            return true;
        }
    }


}
