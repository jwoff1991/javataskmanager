package taskmanager.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
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
    public void shouldCreateTask() throws DataAccessException {
        TaskResult actual = service.create(new Task(0, "2023-01-14", "Do something", "doinging something cool",
                "2024-05-67", Status.COMPLETE));

        assertNotNull(actual.getTask());
        assertTrue(actual.isSuccess());

        assertEquals(99, actual.getTask().getId());

    }

    @Test
    public void shouldNotCreateNullTask() throws DataAccessException {
        TaskResult actual = service.create(null);

        assertFalse(actual.isSuccess());
        assertNull(actual.getTask());


    }

    @Test
    public void shouldNotCreateTaskWithoutStartDate() throws DataAccessException {
        TaskResult actual = service.create(new Task(0, "", "Do something", "doinging something cool",
                "2024-05-67", Status.COMPLETE));

        assertFalse(actual.isSuccess());

    }

    @Test
    public void shouldNotCreateTaskWithoutTitle() throws DataAccessException {
        TaskResult actual = service.create(new Task(0, "2023-01-14", "", "doinging something cool",
                "2024-05-67", Status.COMPLETE));
        assertFalse(actual.isSuccess());
    }
    @Test
    public void shouldNotCreateTaskWithTitleLongerThan50char() throws DataAccessException {
        TaskResult actual = service.create(new Task(0, "2023-01-14", "Do somethingDo somethingDo somethingDo somethingDo somethingDo somethingDo something",
                "doinging something cool","2024-05-67", Status.COMPLETE));
        assertFalse(actual.isSuccess());

    }

    @Test
    public void shouldNotCreateTaskWithoutDescription() throws DataAccessException {
        TaskResult actual = service.create(new Task(0, "2023-01-14", "Do something", "",
                "2024-05-67", Status.COMPLETE));
        assertFalse(actual.isSuccess());

    }
    @Test
    public void shouldNotCreateTaskWithDescriptionShorterThan20Char() throws DataAccessException {
        TaskResult actual = service.create(new Task(0, "2023-01-14", "Do something", "doinging s",
                "2024-05-67", Status.COMPLETE));
        assertFalse(actual.isSuccess());

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

    @Test
    public void shouldUpdateExistingTask() throws DataAccessException {
        List<Task> all = service.findAll();
        Task toUpdate = all.get(0);
        toUpdate.setDescription("This is the updated description");

        TaskResult actual = service.update(toUpdate);
        assertTrue(actual.isSuccess());
        assertEquals(0, actual.getMessages().size());
        assertEquals("This is the updated description", all.get(0).getDescription());

    }

    @Test
    public void shouldNotUpdateNonExistingTask() throws DataAccessException {
        Task task = new Task(-2,"2024-04-01", "FAKE", "FAKE FAKE FAKE FAKE FAKE", "2024-4-05", Status.TODO);
        TaskResult actual = service.update(task);
        assertFalse(actual.isSuccess());
        assertEquals(1, actual.getMessages().size());


    }

    @Test
    public void shouldDeleteExistingTask() throws DataAccessException {
        TaskResult actual = service.deleteByid(1);
        assertTrue(actual.isSuccess());
    }

    @Test
    public void shouldNotDeleteNonExistingTask() throws DataAccessException {
        TaskResult actual = service.deleteByid(999);
        assertFalse(actual.isSuccess());
        assertEquals(1, actual.getMessages().size());

    }


}