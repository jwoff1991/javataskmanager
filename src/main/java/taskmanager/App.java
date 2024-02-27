package taskmanager;

import taskmanager.data.DataAccessException;
import taskmanager.data.TaskFIleRepository;
import taskmanager.models.Task;

import java.util.List;

public class App {
    public static void main(String[] args) throws DataAccessException {
        TaskFIleRepository repository = new TaskFIleRepository("./data/tasks.csv");
        List<Task> tasks = repository.findAll();

        for(Task task : tasks) {
            System.out.println(task);
        }

    }
}
