package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.IoReader;
import nl.sander.jsontoy2.JsonObjectReader;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Date;

public class DateReader extends AbstractDatesReader<Date> implements JsonObjectReader<Date> {

    @Override
    public Date read(IoReader ioReader) {
        ZonedDateTime zdt = getZonedDateTime(ioReader::readString);
        return Date.from(zdt.toInstant());
    }

}
