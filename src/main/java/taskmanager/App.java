package taskmanager;

import taskmanager.data.DataAccessException;
import taskmanager.data.TaskFIleRepository;
import taskmanager.domain.TaskService;
import taskmanager.models.Task;
import taskmanager.ui.Controller;
import taskmanager.ui.View;


import java.util.List;

public class App {
    public static void main(String[] args)  {
        TaskFIleRepository repository = new TaskFIleRepository("./data/tasks.csv");
        TaskService service = new TaskService(repository);
        View view = new View();
        Controller controller = new Controller(view, service);
        controller.run();

    }
}
