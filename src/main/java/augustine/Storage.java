package augustine;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

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
