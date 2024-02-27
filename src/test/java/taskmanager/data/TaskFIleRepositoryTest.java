package taskmanager.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskmanager.models.Status;
import taskmanager.models.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskFIleRepositoryTest {
    private static final String SEED_FILE_PATH = "./data/tasks-seed.csv";
    private static final String TEST_FILE_PATH = "./data/tasks-test.csv";

    private final TaskFIleRepository repository = new TaskFIleRepository(TEST_FILE_PATH);

    //known good state
    @BeforeEach
    public void setup() throws IOException {
        Path seedpath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);

        Files.copy(seedpath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    public void shouldFindAll() throws DataAccessException {
        List<Task> actual = repository.findAll();
        assertEquals(3, actual.size());

        //1,2024-2-20,Review Curriculum, check content for spelling and grammar, 2024-2-21,TODO
        Task task = actual.get(0);
        assertEquals(1, task.getId());
        assertEquals("2024-2-20", task.getCreatedOn());
        assertEquals("Review Curriculum", task.getTitle());
        assertEquals(" check content for spelling and grammar", task.getDescription());
        assertEquals(" 2024-2-21", task.getDueDate());
        assertEquals(Status.TODO, task.getStatus());
    }

    @Test
    public void shouldFindByExistingId() throws DataAccessException {
        Task taskOne = repository.findById(1);
        assertNotNull(taskOne);
        assertEquals(1, taskOne.getId());
        assertEquals("2024-2-20", taskOne.getCreatedOn());
        assertEquals("Review Curriculum", taskOne.getTitle());
        assertEquals(" check content for spelling and grammar", taskOne.getDescription());
        assertEquals(" 2024-2-21", taskOne.getDueDate());
        assertEquals(Status.TODO, taskOne.getStatus());
    }

    @Test
    public void shouldNotFindNonExistingId() throws DataAccessException {
        Task notValid = repository.findById(99999);
        assertNull(notValid);
    }

    @Test
    public void shouldCreate() throws DataAccessException {
        Task task = new Task(
                0,
                "2024-02-01",
                "Call mom",
                "Be sure to call ya mom",
                "2024-02-01",
                Status.TODO
        );
        Task actual = repository.create(task);
        assertEquals(4, actual.getId());

        List<Task> all = repository.findAll();
        assertEquals(4, all.size());

        assertNotNull(actual);
        assertEquals(4, actual.getId());
        assertEquals("2024-02-01", actual.getCreatedOn());
        assertEquals("Call mom", actual.getTitle());
        assertEquals("Be sure to call ya mom", actual.getDescription());
        assertEquals("2024-02-01", actual.getDueDate());
        assertEquals(Status.TODO, actual.getStatus());
    }

    @Test
    public void shouldCreateWithCommas() throws DataAccessException {
        Task task = new Task(
                0,
                "2024-02-01",
                "Call, mom",
                "Be sure, to call, ya mom",
                "2024-02-01",
                Status.TODO
        );
        Task actual = repository.create(task);
        assertEquals(4, actual.getId());

        List<Task> all = repository.findAll();
        assertEquals(4, all.size());

        assertNotNull(actual);
        assertEquals(4, actual.getId());
        assertEquals("2024-02-01", actual.getCreatedOn());
        assertEquals("Call, mom", actual.getTitle());
        assertEquals("Be sure, to call, ya mom", actual.getDescription());
        assertEquals("2024-02-01", actual.getDueDate());
        assertEquals(Status.TODO, actual.getStatus());
    }

    @Test
    public void shouldUpdate() throws DataAccessException {
        Task task = repository.findById(1);
        task.setStatus(Status.IN_PROGRESS);
        task.setDescription("Do something funny");

        boolean result = repository.update(task);
        assertTrue(result);
        assertNotNull(result);


        assertEquals(1, task.getId());
        assertEquals("2024-2-20", task.getCreatedOn());
        assertEquals("Review Curriculum", task.getTitle());
        assertEquals("Do something funny", task.getDescription());
        assertEquals(" 2024-2-21", task.getDueDate());
        assertEquals(Status.IN_PROGRESS, task.getStatus());

    }


    @Test
    public void shouldNotUpDateUnknown() throws DataAccessException {
        Task task = new Task(999999, "", "", "", "", Status.TODO);

        boolean result = repository.update(task);
        assertFalse(result);
    }


    @Test
    public void shouldDelete() throws DataAccessException {
        boolean result = repository.delete(1);
        assertTrue(result);

        List<Task> all = repository.findAll();
        assertEquals(2, all.size());

        Task task = repository.findById(1);
        assertNull(task);
    }

    @Test
    public void shouldNotDeleteUnknown() throws DataAccessException {
        boolean result = repository.delete(99999);
        assertFalse(result);
    }

}