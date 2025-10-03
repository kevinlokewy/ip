package augustine;

import java.util.Scanner;
import java.util.ArrayList;

/**
 * Handles all user interaction for the Augustine application.
 * This class is responsible for displaying messages to the user via the console
 * and reading user input commands. It provides a consistent interface with
 * decorative separator lines for better readability.
 */


public class Ui {
    private final Scanner scanner = new Scanner(System.in);
    public static final String DONE_ICON = "X";
    public static final String NOT_DONE_ICON = " ";

    /**
     * Displays a horizontal separator line to visually separate sections of output.
     * The line consists of 60 underscore characters.
     */

    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays the welcome message when the application starts.
     * Shows the application name and prompts the user for input.
     */

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

    /**
     * Displays a confirmation message after a task has been successfully added.
     * Shows the added task and the updated total number of tasks in the list.
     * The word "task" or "tasks" is properly pluralized based on the count.
     *
     * @param task the task that was added
     * @param totalTasks the total number of tasks in the list after adding
     */
    public void showTaskAdded(Task task, int totalTasks) {
        showLine();
        System.out.println("Okay! I've added the following task:");
        System.out.println("   " + task);
        String taskWord = totalTasks == 1 ? "task" : "tasks";
        System.out.println("You now have " + totalTasks + " " + taskWord + " in the list.");
        showLine();
    }

    /**
     * Displays all tasks in the task list.
     * If the list is empty, shows a message indicating no tasks have been added yet.
     * Otherwise, displays each task with its index (starting from 1).
     *
     * @param tasks the TaskList containing all tasks to display
     */
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

    /**
     * Displays a confirmation message after marking a task as done.
     * If the task is already marked as done, shows a message indicating this.
     * Otherwise, marks the task as done and displays the updated task.
     *
     * @param task the task to be marked as done
     */
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

    /**
     * Displays tasks that match a search query.
     * If no matching tasks are found, shows a message indicating this.
     * Otherwise, displays each matching task with its index (starting from 1).
     *
     * @param matchedTasks the list of tasks that match the search criteria
     */
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


