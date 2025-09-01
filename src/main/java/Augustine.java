import java.util.Scanner;

public class Augustine {
    public static void main(String[] args) {
        Scanner line = new Scanner(System.in);
        String[] tasks = new String[100];
        int taskCount = 0;
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Augustine");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");

        while (true) {
            String input = line.nextLine().trim();

            if (input.toLowerCase().contains("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            } else if (input.equalsIgnoreCase("list")) {
                System.out.println("____________________________________________________________");
                if (taskCount == 0) {
                    System.out.println("No tasks added yet!");
                } else {
                    for (int i = 0; i < taskCount; i += 1) {
                        System.out.println(" " + (i + 1) + ". " + tasks[i]);
                    }
                    System.out.println("____________________________________________________________");
                }

            } else if (input.toLowerCase().startsWith("add")) {
                if (taskCount < 100) {
                    String task = input.substring(4).trim(); // remove "add " prefix
                    tasks[taskCount] = input;
                    taskCount += 1;
                    System.out.println("____________________________________________________________");
                    System.out.println(" added:" + input);
                    System.out.println("____________________________________________________________");
                } else {
                    System.out.println("____________________________________________________________");
                    System.out.println("Task list is full! Cannot add anymore tasks");
                    System.out.println("____________________________________________________________");
                }
            } else {

                System.out.println("____________________________________________________________");
                System.out.println(" " + input);
                System.out.println("____________________________________________________________");

            }
        }
        line.close();

    }
}

