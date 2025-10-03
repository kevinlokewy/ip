package augustine;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
        * Represents a task with a deadline.
        * A deadline task includes a description and a due date/time that can be specified
 * in either a structured date format or as free text.
        * <p>
 * The class attempts to parse the deadline using the format "d/M/yyyy HHmm".
        * If parsing fails, the original string is stored and displayed as-is.
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

        }
        this.by = temp;
    }

    public String getByString() {
        return byString;
    }

    public LocalDateTime getBy() {
        return by;
    }
    /**
     * Returns a string representation of this deadline task.
     * The format is "[D][status] description (by: deadline)" where the deadline
     * is formatted as "MMM dd yyyy HH:mm" if successfully parsed, or the original
     * input string otherwise.
     *
     * @return a formatted string representation of the deadline task
     */
    @Override
    public String toString() {
        String display = (by != null) ? by.format(OUTPUT_FORMAT) : byString;
        return "[D]" + super.toString() + " (by: " + display + ")";
    }
}
