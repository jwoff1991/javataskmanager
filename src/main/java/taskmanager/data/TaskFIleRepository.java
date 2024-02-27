package taskmanager.data;

import taskmanager.models.Status;
import taskmanager.models.Task;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class TaskFIleRepository implements TaskRepository {
    //fields
    private static final String DELIMITER = ",";
    private static final String DELIMIER_REPLACEMENT = "@@@";
    private final String filePath;

    public TaskFIleRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Task> findAll() {
        return null;
    }

    @Override
    public Task findById(int taskId) {
        return null;
    }

    @Override
    public Task create(Task task) {
        return null;
    }

    @Override
    public boolean update(Task task) {
        return false;
    }

    @Override
    public boolean delete(int taskId) {
        return false;
    }

    //helper methods

    private String restore(String value) {
        return value.replace(DELIMIER_REPLACEMENT, DELIMITER);
    }

    private String clean(String value) {
        return value.replace(DELIMITER, DELIMIER_REPLACEMENT);
    }

    //deserialize data
    private Task lineToTask(String line) {
        String [] fields = line.split(DELIMITER);
        if(fields.length != 6) {
            return null;
        }
        Task task = new Task(
                Integer.parseInt(fields[0]),
                restore(fields[1]),
                restore(fields[2]),
                restore(fields[3]),
                restore(fields[4]),
                Status.valueOf(fields[5])
        );

        return task;
    }

    //serialize data
    private String taskToLine(Task task) {
        StringBuilder buffer = new StringBuilder(100);

        buffer.append(task.getId()).append(DELIMITER);
        buffer.append(clean(task.getCreatedOn())).append(DELIMITER);
        buffer.append(clean(task.getTitle())).append(DELIMITER);
        buffer.append(clean(task.getDescription())).append(DELIMITER);
        buffer.append(clean(task.getDueDate())).append(DELIMITER);
        buffer.append(task.getStatus());

        return buffer.toString();
    }

    //write to file
    private void writeToFIle(List<Task> tasks) throws DataAccessException {
        try(PrintWriter writer = new PrintWriter(filePath)) {
            writer.println("id,createdOn,title,description,dueDate,status");

            for(Task task : tasks) {
                String line = taskToLine(task);
                writer.println(line);
            }
        } catch (IOException ex) {
            throw new DataAccessException("Could not write to a file path " + filePath);
        }
    }

    //get next id
}
