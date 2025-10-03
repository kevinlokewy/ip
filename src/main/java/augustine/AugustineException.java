package augustine;

/**
 * Custom exception class for handling errors specific to the Augustine application.
 * This exception is thrown when the application encounters invalid user input,
 * illegal operations, or other application-specific error conditions.
 */

public class AugustineException extends Exception {
    /**
     * Constructs a new AugustineException with the specified error message.
     *
     * @param message the detailed error message explaining what went wrong
     */
    public AugustineException(String message) {
        super(message);
    }
}