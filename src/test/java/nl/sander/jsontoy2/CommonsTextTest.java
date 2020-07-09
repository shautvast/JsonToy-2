package nl.sander.jsontoy2;

import org.apache.commons.text.StringEscapeUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommonsTextTest {

    @Test
    public void testunicode() {
        assertEquals("\uD834\uDD1E", StringEscapeUtils.unescapeJson("\\uD834\\uDD1E"));
    }
}
