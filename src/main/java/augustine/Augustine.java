package augustine;

import java.util.ArrayList;


public class Augustine {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Augustine(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (AugustineException e) {
            ui.showError("Error loading tasks. Starting with an empty task list.");
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            String input = ui.readCommand().trim();
            if (input.isEmpty()) {
                continue;
            }

            String[] parts = Parser.parse(input);
            String command = parts[0].toLowerCase();
            String argument = parts.length > 1 ? parts[1].trim() : "";

            /**
             * Handles user input commands and performs the corresponding actions on the task list.
             * Supported commands include:
             * <ul>
             *   <li>bye – exits the program</li>
             *   <li>list – displays all tasks</li>
             *   <li>todo – adds a new Todo task</li>
             *   <li>deadline – adds a new Deadline task with a /by date</li>
             *   <li>event – adds a new Event task with /from and /to times</li>
             *   <li>mark – marks a task as done</li>
             *   <li>unmark – marks a task as not done</li>
             *   <li>delete – removes a task from the list</li>
             *   <li>find – searches for tasks containing a keyword</li>
             * </ul>
             *
             * This block also handles exceptions, including:
             * <ul>
             *   <li>AugustineException – thrown for invalid input or missing arguments</li>
             *   <li>NumberFormatException – thrown if a task index is not a valid integer</li>
             * </ul>
             */

            try {
                switch (command) {
                case "bye":
                    ui.showBye();
                    isExit = true;
                    break;

                case "list":
                    ui.showTaskList(tasks);
                    break;

                case "todo":
                    Task todo = new Todo(argument);
                    tasks.add(todo);
                    ui.showTaskAdded(todo, tasks.size());
                    storage.save(tasks.asList());
                    break;

                case "deadline":
                    if (!argument.contains("/by")) {
                        throw new AugustineException("Please provide a deadline in the format: deadline <description> /by <time>");
                    }
                    String[] deadlineParts = argument.split("/by", 2);
                    Task deadline = new Deadline(deadlineParts[0].trim(), deadlineParts[1].trim());
                    tasks.add(deadline);
                    ui.showTaskAdded(deadline, tasks.size());
                    storage.save(tasks.asList());
                    break;

                case "event":
                    if (!(argument.contains("/from") && argument.contains("/to"))) {
                        throw new AugustineException("Please provide an event in the format: event <description> /from <start> /to <end>");
                    }
                    String[] eventParts = argument.split("/from", 2);
                    String description = eventParts[0].trim();
                    String[] timeParts = eventParts[1].split("/to", 2);
                    Task event = new Event(description, timeParts[0].trim(), timeParts[1].trim());
                    tasks.add(event);
                    ui.showTaskAdded(event, tasks.size());
                    storage.save(tasks.asList());
                    break;

                case "mark":
                    try {
                        int markIndex = Integer.parseInt(argument) - 1;
                        if (markIndex < 0 || markIndex >= tasks.size()) {
                            throw new AugustineException("Oops! This task doesn't exist");
                        }
                        Task markTask = tasks.get(markIndex);
                        ui.showTaskMarked(markTask);
                        storage.save(tasks.asList());
                    } catch (AugustineException e) {
                        throw new AugustineException("Oops! This task doesn't exist");
                    } catch (NumberFormatException e) {
                        throw new AugustineException("Please provide a valid task number");
                    }
                    break;

                case "unmark":
                    try {
                        int unmarkIndex = Integer.parseInt(argument) - 1;
                        if (unmarkIndex < 0 || unmarkIndex >= tasks.size()) {
                            throw new AugustineException("Oops! This task doesn't exist!");
                        }
                        Task unmarkTask = tasks.get(unmarkIndex);
                        unmarkTask.markAsNotDone();
                        ui.showTaskUnmarked(unmarkTask);
                        storage.save(tasks.asList());
                    } catch (NumberFormatException e) {
                        throw new AugustineException("Please provide a valid task number");
                    }
                    break;

                case "delete":
                    try {
                        int deleteIndex = Integer.parseInt(argument) - 1;
                        if (deleteIndex < 0 || deleteIndex >= tasks.size()) {
                            throw new AugustineException("Oops! This task doesn't exist");
                        }
                        Task removed = tasks.delete(deleteIndex);
                        ui.showLine();
                        System.out.println("Noted. I've removed this task:");
                        System.out.println("  " + removed);
                        String taskWord = tasks.size() == 1 ? "task" : "tasks";
                        System.out.println("Now you have " + tasks.size() + " " + taskWord + " in the list.");
                        ui.showLine();
                        storage.save(tasks.asList());
                    } catch (NumberFormatException e) {
                        throw new AugustineException("Please provide a valid task number!");
                    }
                    break;
                case "find":
                    try {
                        if (argument.isEmpty()) {
                            throw new AugustineException("Please provide a keyword to search for");
                        }
                        ArrayList<Task> matchedTasks = new ArrayList<>();
                        for (int i = 0; i < tasks.size(); i += 1) {
                            Task t = tasks.get(i);
                            if (t.getDescription().toLowerCase().contains(argument.toLowerCase())) {
                                matchedTasks.add(t);
                            }
                        }

                        if (matchedTasks.isEmpty()) {
                            throw new AugustineException("No tasks found with the keyword you searched for");
                        }
                        ui.showTaskMatches(matchedTasks);

                    } catch (AugustineException e) {
                        ui.showError(e.getMessage());
                    }
                    break;

                default:
                    throw new AugustineException("I don't understand that command. Try: todo, deadline, event, list, mark, unmark, delete, bye");
                }
            } catch (Exception e) {
                ui.showError(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Augustine("data/augustine.txt").run();
    }

    /**
     * Parses a single line from the data file (data.txt) and converts it into a Task object
     * that the program can use. The line is expected to follow this format:
     * <ul>
     *   <li>Todo:     T | done | description</li>
     *   <li>Deadline: D | done | description | by</li>
     *   <li>Event:    E | done | description | from | to</li>
     * </ul>
     * The 'done' field should be "1" if the task is completed, otherwise "0".
     *
     * @return A Task object (Todo, Deadline, or Event) reconstructed from the line.
     */
    public static Task parseTaskFromFile(String line) {
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

    /**
     * Converts a Task object into a string formatted for storage in the data file (data.txt).
     * The output format depends on the task type:
     * <ul>
     *   <li>Todo:     T | done | description</li>
     *   <li>Deadline: D | done | description | by</li>
     *   <li>Event:    E | done | description | from | to</li>
     * </ul>
     * The 'done' field will be "1" if the task is marked as completed, otherwise "0".
     * This format matches the expected input for {@link #parseTaskFromFile(String)},
     * enabling seamless serialization and deserialization of tasks.
     */

    public static String formatTaskForFile(Task task) {
        String type = task instanceof Todo ? "T" :
                task instanceof Deadline ? "D" : "E";
        String done = task.isDone() ? "1" : "0";
        String description = task.getDescription();

        if (task instanceof Deadline) {
            return type + " | " + done + " | " + description + " | " + ((Deadline) task).getByString();
        } else if (task instanceof Event) {
            return type + " | " + done + " | " + description + " | "
                    + ((Event) task).getFromString() + " | " + ((Event) task).getToString();
        } else {
            return type + " | " + done + " | " + description;
        }
    }
}

