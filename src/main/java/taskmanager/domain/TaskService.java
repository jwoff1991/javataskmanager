package taskmanager.domain;

import taskmanager.data.DataAccessException;
import taskmanager.data.TaskRepository;
import taskmanager.models.Task;

import java.util.List;

public class TaskService {
    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    private final TaskRepository repository;

    public List<Task> findAll() throws DataAccessException {
        return repository.findAll();
    }

    public Task findById(int taskId) throws DataAccessException {
        return repository.findById(taskId);
    }

    public TaskResult validate(Task task) {
        TaskResult result = new TaskResult();
        if (task == null) {
            result.addMessage("task cannot be null");
            return result;
        }
        if(task.getCreatedOn() == null | task.getCreatedOn().isBlank()) {
            result.addMessage("Created on date is required");
            return result;
        }
        if(task.getTitle() == null | task.getTitle().isBlank() | task.getTitle().length() > 50) {
            result.addMessage("title must exist and cant be longer than 50 chars");
            return result;
        }
        if(task.getDescription().isBlank() | task.getDescription().length() < 20 | task.getDescription() == null) {
            result.addMessage("Desc must not be empty and longer than 20 chars");
            return result;
        }
        if(task.getDueDate().isBlank() | task.getDueDate() == null) {
            result.addMessage("must have a due date");
            return result;
        }
        if(task.getStatus() == null) {
            result.addMessage("must have a status");
            return result;
        }
        return result;
    }

    public TaskResult create(Task task) throws DataAccessException {
        TaskResult result = validate(task);
        if(!result.isSuccess()) {
            return result;
        }
        if(task == null) {
            result.addMessage("Task cannot be null");
            return result;
        }
        if(task.getId() > 0) {
            result.addMessage("cannot create existing task");
            return result;
        }
        task = repository.create(task);
        result.setTask(task);
        return result;
    }

}
