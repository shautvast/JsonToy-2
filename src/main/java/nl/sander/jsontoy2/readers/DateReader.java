package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.IoReader;
import nl.sander.jsontoy2.JsonValueReader;

import java.time.ZonedDateTime;
import java.util.Date;

public class DateReader extends AbstractDatesReader<Date> implements JsonValueReader<Date> {

    @Override
    public Date read(IoReader ioReader) {
        ZonedDateTime zdt = getZonedDateTime(ioReader::readString);
        return Date.from(zdt.toInstant());
    }

}
