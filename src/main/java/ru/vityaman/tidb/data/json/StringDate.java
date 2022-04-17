package ru.vityaman.tidb.data.json;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class to convert date from string to java.util.Date.
 */
public final class StringDate {
    private static final DateFormat DEFAULT_FORMAT
        = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");

    private final String date;
    private final DateFormat format;

    public StringDate(String date) {
        this(date, DEFAULT_FORMAT);
    }

    public StringDate(String date, DateFormat format) {
        this.date = date;
        this.format = format;
    }

    public StringDate(Date date) {
        this(DEFAULT_FORMAT.format(date), DEFAULT_FORMAT);
    }

    /**
     * @return string formatted date.
     */
    public String asString() {
        return this.date;
    }

    /**
     * @return date.
     */
    public Date asDate() {
        try {
            return format.parse(date);
        } catch (ParseException e) {
            throw new IllegalStateException(
                "Can't parse date", e);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        StringDate that = (StringDate) other;
        return this.date.equals(that.date)
               && this.format.equals(that.format);
    }

    @Override
    public int hashCode() {
        return 31 * date.hashCode() + format.hashCode();
    }

    @Override
    public String toString() {
        return date;
    }
}
