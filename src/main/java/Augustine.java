import java.util.Scanner;
import java.util.ArrayList;

public class Augustine {
    public static void main(String[] args) {
        Scanner line = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();
        printGreeting();

        while (true) {
            String input = line.nextLine().trim();
            if (input.isEmpty()) continue;

            // Split into command and argument
            String[] parts = input.split(" ", 2);
            String command = parts[0].toLowerCase();
            String argument = parts.length > 1 ? parts[1].trim() : "";

            switch (command) {
            case "bye":
                printBye();
                line.close();
                return; // exit program

            case "list":
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
                break;

            case "todo":
                if (!argument.isEmpty()) {
                    Task task = new Todo(argument);
                    tasks.add(task);
                    printDescription(task, tasks.size());
                } else {
                    System.out.println("Please provide a task description!");
                }
                break;

            case "deadline":
                if (!argument.isEmpty() && argument.contains("/by")) {
                    String[] deadlineParts = argument.split("/by", 2);
                    String description = deadlineParts[0].trim();
                    String by = deadlineParts[1].trim();
                    Task task = new Deadline(description, by);
                    tasks.add(task);
                    printDescription(task, tasks.size());
                } else {
                    System.out.println("Usage: deadline <description> /by <time>");
                }
                break;

            case "event":
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
                    System.out.println("Usage: event <description> /from <start> /to <end>");
                }
                break;

            case "mark":
                if (argument.isEmpty()) {
                    System.out.println("Please provide a task number!");
                    break;
                }
                try {
                    int index = Integer.parseInt(argument) - 1;
                    if (index < 0 || index >= tasks.size()) {
                        System.out.println("____________________________________________________________");
                        System.out.println("This task doesn't exist!");
                        System.out.println("____________________________________________________________");
                        break;
                    }

                    Task task = tasks.get(index);
                    if (task.getStatusIcon().equals("X")) {
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
                    System.out.println("Please provide a valid task number!");
                }
                break;

            case "unmark":
                if (argument.isEmpty()) {
                    System.out.println("Please provide a task number!");
                    break;
                }
                try {
                    int index = Integer.parseInt(argument) - 1;
                    if (index < 0 || index >= tasks.size()) {
                        System.out.println("____________________________________________________________");
                        System.out.println("This task doesn't exist!");
                        System.out.println("____________________________________________________________");
                        break;
                    }

                    Task task = tasks.get(index);
                    if (task.getStatusIcon().equals(" ")) {
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
                    System.out.println("Please provide a valid task number!");
                }
                break;

            default:
                printEcho(input);
                break;
            }
        }
    }
    private static void printEcho(String input) {
        System.out.println("____________________________________________________________");
        System.out.println(" " + input);
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

    private static void printGreeting(String x, String x1) {
        System.out.println("____________________________________________________________");
        System.out.println(x);
        System.out.println(x1);
        System.out.println("____________________________________________________________");
    }
}
