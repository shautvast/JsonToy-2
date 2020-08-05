package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.Parser;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class LocalDateTimeReader extends AbstractDatesReader<LocalDateTime> {

    @Override
    public LocalDateTime read(Parser parser) {
        ZonedDateTime zdt = getZonedDateTime(parser::parseString);
        return LocalDateTime.from(zdt);
    }

}
