package taskmanager.data;


import taskmanager.models.Task;

import java.util.List;

public interface TaskRepository {
    List<Task> findAll();
    Task findById(int taskId);

    Task create(Task task);

    boolean update(Task task);

    boolean delete(int taskId);
}
