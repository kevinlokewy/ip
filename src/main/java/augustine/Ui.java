package augustine;

import java.util.Scanner;
import java.util.ArrayList;

/**
 * This class handles all user interaction.
 * It is responsible for showing messages to the user
 * and reading inputs from the user
 */


public class Ui {
    private final Scanner scanner = new Scanner(System.in);
    public static final String DONE_ICON = "X";
    public static final String NOT_DONE_ICON = " ";

    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    public void showWelcome() {
        showLine();
        System.out.println(" Hello! I'm Augustine");
        System.out.println(" What can I do for you?");
        showLine();
    }

    public void showBye() {
        showLine();
        System.out.println(" Bye. Hope to see you again soon!");
        showLine();
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showError(String message) {
        showLine();
        System.out.println(message);
        showLine();
    }

    public void showTaskAdded(Task task, int totalTasks) {
        showLine();
        System.out.println("Okay! I've added the following task:");
        System.out.println("   " + task);
        String taskWord = totalTasks == 1 ? "task" : "tasks";
        System.out.println("You now have " + totalTasks + " " + taskWord + " in the list.");
        showLine();
    }

    public void showTaskList(TaskList tasks) {
        showLine();
        if (tasks.isEmpty()) {
            System.out.println("No tasks added yet!");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(" " + (i + 1) + ". " + tasks.get(i));
            }
        }
        showLine();
    }
    public void showTaskMarked (Task task){
        showLine();
        if (task.getStatusIcon().equals(DONE_ICON)) {
            showLine();
            System.out.println("This item is already marked!");
            showLine();
        } else {
            task.markAsDone();
            System.out.println("Okay, I've marked this task as done!");
            System.out.println(" " + task);

        }
        showLine();
    }
    public void showTaskUnmarked(Task task) {
        showLine();
        if (task.getStatusIcon().equals(NOT_DONE_ICON)) {
            System.out.println("This item is already unmarked!");
        } else {
            task.markAsNotDone();
            System.out.println("Okay, I've marked this task as not done yet:");
            System.out.println("  " + task);
        }
        showLine();
    }
    public void showTaskMatches(ArrayList<Task> matchedTasks) {
        showLine();
        if (matchedTasks.isEmpty()) {
            System.out.println("No matching tasks found!");
        } else {
            System.out.println("Here are the matching tasks in your list:");
            for (int i = 0; i < matchedTasks.size(); i++) {
                System.out.println(" " + (i + 1) + ". " + matchedTasks.get(i));
            }
        }
        showLine();
    }
}


