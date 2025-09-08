import java.util.Scanner;
import java.util.ArrayList;

public class Augustine {

    public static final String NOT_DONE_ICON = " ";
    public static final String DONE_ICON = "X";

    public static void main(String[] args) {
        Scanner line = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();
        printGreeting();

        while (true) {
            String input = line.nextLine().trim();
            if (input.isEmpty()) {
                continue;
            }

            // Split into command and argument
            String[] parts = splitTask(input);
            String command = getFirstWord(parts);
            String argument = parts.length > 1 ? parts[1].trim() : "";

            try {
                switch (command) {
                case "bye":
                    printBye();
                    line.close();
                    return; // exit program

                case "list":
                    printTaskList(tasks);  // handles "list" command
                    break;

                case "todo":
                    handleToDo(argument, tasks);  // handle "todo" command
                    break;

                case "deadline":
                    handleDeadline(argument, tasks); // handle "deadline" command
                    break;

                case "event":
                    handleEvent(argument, tasks);  // handle "event" command
                    break;

                case "mark":
                    handleMark(argument, tasks); // handle "mark" command
                    break;

                case "unmark":
                    handleUnmark(argument, tasks);  // handle "unmark" command
                    break;

                default:
                    throw new AugustineException("I don't understand that command. Try: todo, deadline, event, list, mark, unmark");
                }
            } catch (AugustineException e) {
                printErrorMessage(e.getMessage());
            }
        }
    }

    // HELPER FUNCTIONS

    private static void handleUnmark(String argument, ArrayList<Task> tasks) throws AugustineException{
        if (argument.isEmpty()) {
            throw new AugustineException("Please provide a task number!");

        }
        try {
            int index = Integer.parseInt(argument) - 1;
            if (index < 0 || index >= tasks.size()) {
                System.out.println("____________________________________________________________");
                System.out.println("This task doesn't exist!");
                System.out.println("____________________________________________________________");
                return;
            }

            Task task = tasks.get(index);
            if (task.getStatusIcon().equals(NOT_DONE_ICON)) {
                System.out.println("____________________________________________________________");
                System.out.println("This item is already unmarked!");
                System.out.println("____________________________________________________________");
            } else {
                task.markAsNotDone();
                System.out.println("____________________________________________________________");
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + task);
                System.out.println("____________________________________________________________");
            }
        } catch (NumberFormatException e) {
            throw new AugustineException("Please provide a valid task number!");
        }
    }

    private static void handleMark(String argument, ArrayList<Task> tasks) throws AugustineException{
        if (argument.isEmpty()) {
            throw new AugustineException("Please provide a task number!");
        }
        try {
            int index = Integer.parseInt(argument) - 1;
            if (index < 0 || index >= tasks.size()) {
                System.out.println("____________________________________________________________");
                System.out.println("This task doesn't exist!");
                System.out.println("____________________________________________________________");
                return;
            }

            Task task = tasks.get(index);
            if (task.getStatusIcon().equals(DONE_ICON)) {
                System.out.println("____________________________________________________________");
                System.out.println("This item is already marked!");
                System.out.println("____________________________________________________________");

            } else {
                task.markAsDone();
                System.out.println("____________________________________________________________");
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + task);
                System.out.println("____________________________________________________________");
            }
        } catch (NumberFormatException e) {
            throw new AugustineException("Please provide a valid task number!");
        }
    }


    private static void handleEvent(String argument, ArrayList<Task> tasks) throws AugustineException {
        if (!argument.isEmpty() && argument.contains("/from") && argument.contains("/to")) {
            String[] eventParts = argument.split("/from", 2);
            String description = eventParts[0].trim();
            String[] timeParts = eventParts[1].split("/to", 2);
            String from = timeParts[0].trim();
            String to = timeParts[1].trim();
            Task task = new Event(description, from, to);
            tasks.add(task);
            printDescription(task, tasks.size());
        } else {
            throw new AugustineException("Please provide an event in the format: event <description> /from <start> /to <end>");
        }
    }

    private static void handleDeadline(String argument, ArrayList<Task> tasks) throws AugustineException {
        if (!argument.isEmpty() && argument.contains("/by")) {
            String[] deadlineParts = argument.split("/by", 2);
            String description = deadlineParts[0].trim();
            String by = deadlineParts[1].trim();
            Task task = new Deadline(description, by);
            tasks.add(task);
            printDescription(task, tasks.size());
        } else {
            throw new AugustineException("Please provide a deadline in the format: deadline <description> /by <time>");
        }
    }

    private static void handleToDo(String argument, ArrayList<Task> tasks) throws AugustineException {
        if (!argument.isEmpty()) {
            Task task = new Todo(argument);
            tasks.add(task);
            printDescription(task, tasks.size());
        } else {
            throw new AugustineException("Please provide a task description!");
        }
    }

    private static void printTaskList(ArrayList<Task> tasks) {
        System.out.println("____________________________________________________________");
        if (tasks.isEmpty()) {
            System.out.println("No tasks added yet!");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(" " + (i + 1) + ". " + tasks.get(i));
            }
        }
        System.out.println("____________________________________________________________");
    }

    private static String getFirstWord(String[] parts) {
        return parts[0].toLowerCase();
    }

    private static String[] splitTask(String input) {
        return input.split(" ", 2);
    }

    private static void printErrorMessage(String message) {
        System.out.println("____________________________________________________________");
        System.out.println(message);
        System.out.println("____________________________________________________________");
    }

    private static void printDescription(Task task, int totalTasks) {
        String taskWord = totalTasks == 1 ? "task" : "tasks"; // singular or plural
        System.out.println("____________________________________________________________");
        System.out.println("Okay! I've added the following task:");
        System.out.println("   " + task);
        System.out.println("You now have " + totalTasks + " " + taskWord + " in the list.");
        System.out.println("____________________________________________________________");
    }


    private static void printGreeting() {
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Augustine");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");
    }

    private static void printBye() {
        System.out.println("____________________________________________________________");
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }
}

