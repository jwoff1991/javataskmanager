package taskmanager.data;

import taskmanager.models.Status;
import taskmanager.models.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskRepositoryDouble implements TaskRepository {
    @Override
    public List<Task> findAll() throws DataAccessException {
        List<Task> all = new ArrayList<>();
        all.add(new Task(1,"2024-04-01", "Bake Cake", "Good ol birthday cake", "2024-4-05", Status.TODO));
        all.add(new Task(2,"2023-02-25", "Send invites", "Make sure he has friends at his bday!", "2024-2-30", Status.IN_PROGRESS));
        all.add(new Task(1,"2024-03-20", "Buy presents", "Get that good doggie toy", "2024-03-30", Status.COMPLETE));

        return all;
    }

    @Override
    public Task findById(int taskId) throws DataAccessException {
        for(Task task : findAll()) {
            if(task.getId() == taskId) {
                return task;
            }
        }
        return null;
    }

    @Override
    public Task create(Task task) throws DataAccessException {
        task.setId(99);
        return task;
    }

    @Override
    public boolean update(Task task) throws DataAccessException {
        return task.getId() > 0;
    }

    @Override
    public boolean delete(int taskId) throws DataAccessException {
        return taskId != 999;
    }
}
