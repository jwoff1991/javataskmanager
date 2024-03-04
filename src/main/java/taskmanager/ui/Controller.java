package taskmanager.ui;

import taskmanager.data.DataAccessException;
import taskmanager.domain.TaskResult;
import taskmanager.domain.TaskService;
import taskmanager.models.Task;

import java.util.List;

public class Controller {
    public Controller(View view, TaskService taskService) {
        this.view = view;
        this.taskService = taskService;
    }

    private final View view;
    private final TaskService taskService;

    public void run() {
        view.displayHeader("Welcome to the task manager");
        try{
            runMenu();
        }catch(DataAccessException ex){
            view.displayText("Something went wrong");
            view.displayText(ex.getMessage());

        }
        view.displayText("Goodbye");
    }

    private void runMenu() throws DataAccessException {
        boolean exit = false;
        while(!exit) {
            int selection = view.getMenuOption();
            switch (selection) {
                case 1:
                    addTask();
                    break;
                case 2:
                    viewTasks();
                    break;
                case 3:
                    updateTask();
                    break;
                case 4:
                    deleteTask();
                    break;
                case 5:
                    exit = true;
                    break;

            }
        }
    }


    private void addTask() throws DataAccessException {
        Task task = view.makeTask();

        TaskResult result = taskService.create(task);

        if(result.isSuccess()) {
            view.displayText("Your task was successfully created.");
        } else {
            view.displayErrors(result.getMessages());
        }

    }

    private void viewTasks() throws DataAccessException {
        List<Task> tasks = taskService.findAll();
        view.displayTasks(tasks);
    }

    private void updateTask() throws DataAccessException {
        view.displayHeader("Update a task");
        int id = view.updateById();
        Task task = taskService.findById(id);
        if(task != null) {
            Task updatedTask = view.makeTask();
            updatedTask.setId(task.getId());
            TaskResult result = taskService.update(updatedTask);
            if(result.isSuccess()) {
                view.displayText("Your task was successfully updated.");
            }
        }else {
            view.displayErrors(List.of(String.format("There is no task with id $s", id)));
        }
    }

    private void deleteTask() throws DataAccessException {
        view.displayHeader("Delete a task");
        Task task = taskService.findById(view.updateById());
        if(task != null) {
            TaskResult result = taskService.deleteByid(task.getId());
            if(result.isSuccess()) {
                view.displayText("Your task was successfully deleted.");
            }
        }else {
            view.displayErrors(List.of("There is no task with that id"));
        }
    }
}
