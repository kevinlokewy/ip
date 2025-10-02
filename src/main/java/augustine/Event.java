/**
 * This class represents a task with a duration. This type of task is called an event
 * An event has a task description followed by the duration in the form of /from /to
 */

package augustine;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    private final String fromString;
    private final String toString;
    private final LocalDateTime from;
    private final LocalDateTime to;

    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

    public Event(String description, String fromInput, String toInput) {
        super(description);
        this.fromString = fromInput;
        this.toString = toInput;

        LocalDateTime tempFrom = null;
        LocalDateTime tempTo = null;

        try {
            tempFrom = LocalDateTime.parse(fromInput, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            // leave null because we want both DD/MM/YYYY format and String format to work
        } try {
            tempTo = LocalDateTime.parse(toInput, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            // leave null because we want both DD/MM/YYYY format and String format to work
        }

        this.from = tempFrom;
        this.to = tempTo;
    }

    public String getFromString() {
        return fromString;
    }

    public String getToString() {
        return toString;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    @Override
    public String toString() {
        String displayFrom = (from != null) ? from.format(OUTPUT_FORMAT) : fromString;
        String displayTo = (to != null) ? to.format(OUTPUT_FORMAT) : toString;
        return "[E]" + super.toString() + " (from: " + displayFrom + " to: " + displayTo + ")";
    }
}
