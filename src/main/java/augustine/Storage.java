package augustine;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Handles reading and writing tasks to a file.
 * The Storage class is responsible for persisting the task list
 * between sessions, so that the user’s tasks are not lost
 * when the program exits.
 */

public class Storage {
    private final String filePath;
    /**
     * Creates a Storage object to manage saving and loading tasks
     * from a specific file path.
     *
     * @param filePath The path to the file used for storing tasks.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the file.
     * If the file does not exist, it will be created and
     * an empty task list will be returned.
     * returns a list of tasks loaded from the file.
     * throws AugustineException If there is an error reading the file.
     */

    public ArrayList<Task> load() throws AugustineException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
                return tasks; // empty list
            }

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                Task task = Augustine.parseTaskFromFile(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
            br.close();
        } catch (IOException e) {
            throw new AugustineException("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }

    /**
     * Saves tasks to the file.
     */
    public void save(ArrayList<Task> tasks) throws AugustineException {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
            for (Task task : tasks) {
                bw.write(Augustine.formatTaskForFile(task));
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            throw new AugustineException("Error saving tasks: " + e.getMessage());
        }
    }
}
