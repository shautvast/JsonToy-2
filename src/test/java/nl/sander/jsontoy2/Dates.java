package nl.sander.jsontoy2;

import nl.sander.jsontoy2.readers.AbstractDatesReader;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Dates {

    @Test
    public void testDate() throws ParseException {
        Date value = JsonReader.read(Date.class, "\"2012-04-23T18:25:43.511Z\"");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date expected = simpleDateFormat.parse("2012-04-23T18:25:43.511Z");
        assertEquals(expected, value);
    }

    @Test
    public void testLocalDateTime() {
        LocalDateTime value = JsonReader.read(LocalDateTime.class, "\"2012-04-23T18:25:43.511Z\"");
        LocalDateTime expected = LocalDateTime.parse("2012-04-23T18:25:43.511Z", AbstractDatesReader.formatter);
        assertEquals(expected, value);
    }

}
