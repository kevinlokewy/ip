package augustine;

/**
 * Parses a user input string into a command and its arguments.
 * Splits the input at the first space, separating the command from the rest of the input.
 * If there is no space in the input, the entire string is treated as the command
 * and the argument will be an empty array element.
 * <p>
 * Examples:
 * <ul>
 *   <li>"todo read book" returns ["todo", "read book"]</li>
 *   <li>"list" returns ["list"]</li>
 *   <li>"delete 3" returns ["delete", "3"]</li>
 * </ul>
 *
 * @param input the raw user input string to be parsed
 * @return a String array where index 0 is the command and index 1 (if present)
 *         contains the remaining arguments
 */

public class Parser {
    public static String[] parse(String input) {
        return input.split(" ", 2); // ["command", "argument"]
    }
}
