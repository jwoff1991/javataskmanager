package taskmanager.domain;

import org.junit.jupiter.api.Test;
import taskmanager.data.DataAccessException;
import taskmanager.data.TaskRepository;
import taskmanager.data.TaskRepositoryDouble;
import taskmanager.models.Status;
import taskmanager.models.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {
    TaskRepository repository = new TaskRepositoryDouble();
    TaskService service = new TaskService(repository);

    @Test
    public void shouldCreateTask() {

    }

    @Test
    public void shouldNotCreateNullTask() {

    }

    @Test
    public void shouldNotCreateTaskWithoutStartDate() {

    }

    @Test
    public void shouldNotCreateTaskWithoutTitle() {

    }
    @Test
    public void shouldNotCreateTaskWithTitleLongerThan50char() {

    }

    @Test
    public void shouldNotCreateTaskWithoutDescription() {

    }
    @Test
    public void shouldNotCreateTaskWithDescriptionShorterThan20Char() {

    }


    @Test
    public void shouldFindAll() throws DataAccessException {
        List<Task> actual = service.findAll();

        assertEquals(3, actual.size());
        Task task = actual.get(0);
//        1,"2024-04-01", "Bake Cake", "Good ol birthday cake", "2024-4-05", Status.TODO
        assertEquals(1, task.getId());
        assertEquals("2024-04-01", task.getCreatedOn());
        assertEquals("Bake Cake", task.getTitle());
        assertEquals("Good ol birthday cake", task.getDescription());
        assertEquals("2024-4-05", task.getDueDate());
        assertEquals(Status.TODO, task.getStatus());
    }

    @Test
    public void shouldFindExistingById() throws DataAccessException {
        Task task = service.findById(1);
        assertNotNull(task);
        assertEquals(1, task.getId());
        assertEquals("2024-04-01", task.getCreatedOn());
        assertEquals("Bake Cake", task.getTitle());
        assertEquals("Good ol birthday cake", task.getDescription());
        assertEquals("2024-4-05", task.getDueDate());
        assertEquals(Status.TODO, task.getStatus());

    }

    @Test
    public void shouldNotFindNonExistingById() throws DataAccessException {
        Task task = service.findById(987);
        assertNull(task);
    }


}