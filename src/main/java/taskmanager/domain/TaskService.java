package taskmanager.domain;

import org.springframework.stereotype.Service;
import taskmanager.data.DataAccessException;
import taskmanager.data.TaskRepository;
import taskmanager.models.Task;

import java.util.List;
@Service
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
        if(task.getDescription() == null | task.getDescription().isBlank() | task.getDescription().length() < 20) {
            result.addMessage("Desc must not be empty and longer than 20 chars");
            return result;
        }
        if(task.getDueDate() == null | task.getDueDate().isBlank()) {
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

    public TaskResult update(Task task) throws DataAccessException {
        TaskResult result = validate(task);
        if(!result.isSuccess()) {
            return result;
        }
        boolean updated = repository.update(task);
        if(!updated) {
            result.addMessage(String.format("Task with id: %s does not exist", task.getId()));
        }
        return result;
    }

    public TaskResult deleteByid(int taskId) throws DataAccessException {
        TaskResult result = new TaskResult();
        if(!repository.delete(taskId)) {
            result.addMessage(String.format("Task with id: %s does not exist", taskId));
        }
        return result;
    }

}
