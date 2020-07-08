package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.IoReader;
import nl.sander.jsontoy2.JsonValueReader;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class LocalDateTimeReader extends AbstractDatesReader<LocalDateTime> implements JsonValueReader<LocalDateTime> {

    @Override
    public LocalDateTime read(IoReader ioReader) {
        ZonedDateTime zdt = getZonedDateTime(ioReader::readString);
        return LocalDateTime.from(zdt);
    }

}
