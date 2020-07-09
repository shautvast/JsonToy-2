package nl.sander.jsontoy2.readers;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.function.Supplier;

public abstract class AbstractDatesReader<T> {

    private static final ZoneId zone = ZoneId.systemDefault();

    public static final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            // date and time
            .appendPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
            // optional offset - prints +0000 when it's zero (instead of Z)
            .optionalStart().appendOffset("+HHMM", "+0000").optionalEnd()
            // optional zone id (so it parses "Z")
            .optionalStart()
            .appendZoneId()
            // add default value for offset seconds when field is not present
            .parseDefaulting(ChronoField.OFFSET_SECONDS, 0)
            .optionalEnd()
            // create formatter using the "UTC-cloned" zone
            .toFormatter().withZone(zone);

    protected ZonedDateTime getZonedDateTime(Supplier<String> supplier) {
        return formatter.parse(supplier.get(), ZonedDateTime::from);
    }

}
