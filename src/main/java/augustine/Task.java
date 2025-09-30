package augustine;

/**
 * Represents a generic task. A task has a description and can be marked as done or not done.
 * Specific task subclasses such as todo, event, deadline extend this class to add more details.
 */

public class Task {
    private final String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markAsDone() {
        isDone = true;
    }

    public void markAsNotDone() {
        isDone = false;
    }

    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    public String getDescription() {

        return description;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + getDescription();
    }

    public boolean isDone() {
        return isDone;
    }
}
