import java.util.Scanner;
import java.util.ArrayList;

public class Augustine {
    public static void main(String[] args) {
        Scanner line = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();
        printGreeting();

        while (true) {
            String input = line.nextLine().trim();

            if (input.toLowerCase().contains("bye")) {
                printBye();
                break;
            } else if (input.equalsIgnoreCase("list")) {
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

            } else if (input.toLowerCase().startsWith("add ")) {
                String description = input.substring(4).trim();
                if (!description.isEmpty()) {
                    Task task = new Task(description);
                    tasks.add(task);
                    printDescription(description);
                } else {
                    System.out.println("Please provide a task description!");
                }

            } else if (input.toLowerCase().startsWith("mark ")) {
                try {
                    int index = Integer.parseInt(input.substring(5).trim()) - 1;
                    if (index < 0 || index >= tasks.size()) {
                        System.out.println("____________________________________________________________");
                        System.out.println("This task doesn't exist!");
                        System.out.println("____________________________________________________________");
                        continue;
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

            } else if (input.toLowerCase().startsWith("unmark ")) {
                try {
                    int index = Integer.parseInt(input.substring(7).trim()) - 1;
                    if (index < 0 || index >= tasks.size()) {
                        System.out.println("____________________________________________________________");
                        System.out.println("This task doesn't exist!");
                        System.out.println("____________________________________________________________");
                        continue;
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

            } else {
                // Echo any other input
                printEcho(input);
            }
        }

        line.close();
    }

    private static void printEcho(String input) {
        System.out.println("____________________________________________________________");
        System.out.println(" " + input);
        System.out.println("____________________________________________________________");
    }

    private static void printDescription(String description) {
        System.out.println("____________________________________________________________");
        System.out.println(" added: " + description);
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
