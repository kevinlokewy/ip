package augustine;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * This class represents a task with a deadline.
 * A deadline has a task description followed by a date / time
 */

public class Deadline extends Task {
    private final String byString; // original input
    private final LocalDateTime by; // parsed datetime if possible

    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

    public Deadline(String description, String byInput) {
        super(description);
        this.byString = byInput;
        LocalDateTime temp = null;
        try {
            temp = LocalDateTime.parse(byInput, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            // leave temp as null; we'll just use byString
        }
        this.by = temp;
    }

    public String getByString() {
        return byString;
    }

    public LocalDateTime getBy() {
        return by;
    }

    @Override
    public String toString() {
        String display = (by != null) ? by.format(OUTPUT_FORMAT) : byString;
        return "[D]" + super.toString() + " (by: " + display + ")";
    }
}
