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

}