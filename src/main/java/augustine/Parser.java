package augustine;

public class Parser {
    public static String[] parse(String input) {
        return input.split(" ", 2); // ["command", "argument"]
    }
}
