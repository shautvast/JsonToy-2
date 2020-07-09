package nl.sander.jsontoy2.readers;

import nl.sander.jsontoy2.JsonValueReader;
import nl.sander.jsontoy2.Parser;

import java.time.ZonedDateTime;
import java.util.Date;

public class DateReader extends AbstractDatesReader<Date> implements JsonValueReader<Date> {

    @Override
    public Date read(Parser parser) {
        ZonedDateTime zdt = getZonedDateTime(parser::parseString);
        return Date.from(zdt.toInstant());
    }

}
