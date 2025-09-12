package augustine;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Augustine {

    public static final String NOT_DONE_ICON = " ";
    public static final String DONE_ICON = "X";
    private static final String FILE_PATH = "./data/augustine.txt";

    public static void main(String[] args) {
        Scanner line = new Scanner(System.in);
        ArrayList<Task> tasks = loadTasks();
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
                    saveTasks(tasks); // === LEVEL 7 CHANGE ===
                    break;

                case "deadline":
                    handleDeadline(argument, tasks); // handle "deadline" command
                    saveTasks(tasks); // === LEVEL 7 CHANGE ===
                    break;

                case "event":
                    handleEvent(argument, tasks);  // handle "event" command
                    saveTasks(tasks); // === LEVEL 7 CHANGE ===
                    break;

                case "mark":
                    handleMark(argument, tasks); // handle "mark" command
                    saveTasks(tasks); // === LEVEL 7 CHANGE ===
                    break;

                case "unmark":
                    handleUnmark(argument, tasks);  // handle "unmark" command
                    saveTasks(tasks); // === LEVEL 7 CHANGE ===
                    break;
                case "delete":
                    handleDelete(argument, tasks);
                    saveTasks(tasks); // === LEVEL 7 CHANGE ===
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

    private static ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            File folder = new File("./data");
            if (!folder.exists()) {
                boolean created = folder.mkdirs();
                if (!created) {
                    System.out.println("Warning: failed to create data folder.");
                }
            }


            File file = new File(FILE_PATH);
            if (!file.exists()) {
                try {
                    boolean created = file.createNewFile();
                    if (!created) {
                        System.out.println("Warning: could not create file " + FILE_PATH);
                    }
                } catch (IOException e) {
                    System.out.println("Error creating file: " + e.getMessage());
                }
            }


            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                Task task = parseTaskFromFile(line);
                if (task != null) tasks.add(task);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }

    // Save tasks to file
    private static void saveTasks(ArrayList<Task> tasks) {
        try {
            File folder = new File("./data");
            if (!folder.exists()) {
                boolean created = folder.mkdirs();
                if (!created) {
                    System.out.println("Warning: failed to create data folder.");
                }
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH));
            for (Task task : tasks) {
                bw.write(formatTaskForFile(task));
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    // Convert a task to a string to store in the file
    private static String formatTaskForFile(Task task) {
        String type = task instanceof Todo ? "T" :
                task instanceof Deadline ? "D" : "E";
        String done = task.getStatusIcon().equals(DONE_ICON) ? "1" : "0";
        String description = task.getDescription();

        if (task instanceof Deadline) {
            return type + " | " + done + " | " + description + " | " + ((Deadline) task).getBy();
        } else if (task instanceof Event) {
            return type + " | " + done + " | " + description + " | " + ((Event) task).getFrom() + " | " + ((Event) task).getTo();
        } else { // Todo
            return type + " | " + done + " | " + description;
        }
    }

    // Parse a line from file back into a Task object
    private static Task parseTaskFromFile(String line) {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task = null;
        switch (type) {
        case "T":
            task = new Todo(description);
            break;
        case "D":
            task = new Deadline(description, parts[3]);
            break;
        case "E":
            task = new Event(description, parts[3], parts[4]);
            break;
        }

        if (task != null && isDone) {
            task.markAsDone();
        }

        return task;
    }

    private static void handleUnmark(String argument, ArrayList<Task> tasks) throws AugustineException {
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

    private static void handleMark(String argument, ArrayList<Task> tasks) throws AugustineException {
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
        if (argument.contains("/from") && argument.contains("/to")) {
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
        if (argument.contains("/by")) {
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

    private static void handleDelete(String argument, ArrayList<Task> tasks) throws AugustineException {
        if (argument.isEmpty()) {
            throw new AugustineException("Please provide a task number to delete!");
        }
        try {
            int index = Integer.parseInt(argument) - 1; // convert to 0-based
            if (index < 0 || index >= tasks.size()) {
                throw new AugustineException("This task doesn't exist!");
            }

            Task removed = tasks.remove(index); // remove task from list
            System.out.println("____________________________________________________________");
            System.out.println("Noted. I've removed this task:");
            System.out.println("  " + removed);
            String taskWord = tasks.size() == 1 ? "task" : "tasks";
            System.out.println("Now you have " + tasks.size() + " " + taskWord + " in the list.");
            System.out.println("____________________________________________________________");
        } catch (NumberFormatException e) {
            throw new AugustineException("Please provide a valid task number!");
        }
    }
}





