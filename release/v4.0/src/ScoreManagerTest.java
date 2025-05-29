import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.util.List;

public class ScoreManagerTest {

    private static final String TEST_FILE_NAME = "placar_test.csv";

    @BeforeEach
    public void setup() throws IOException {
        new File(TEST_FILE_NAME).delete();  // limpa antes
    }

    @Test
    public void testSaveScoreAndRetrieve() {
        Score score1 = new Score("Bruno", 5);
        Score score2 = new Score("Ana", 3);

        ScoreManager.saveScoreToFile(score1, TEST_FILE_NAME);
        ScoreManager.saveScoreToFile(score2, TEST_FILE_NAME);

        List<Score> scores = ScoreManager.getTopScoresFromFile(10, TEST_FILE_NAME);
        assertEquals(2, scores.size());
        assertEquals("Ana", scores.get(0).getName()); // 3 tentativas é melhor que 5
        assertEquals("Bruno", scores.get(1).getName());
    }

    @Test
    public void testGetTopScoresLimit() {
        for (int i = 0; i < 15; i++) {
            ScoreManager.saveScoreToFile(new Score("Jogador" + i, i), TEST_FILE_NAME);
        }
        List<Score> top5 = ScoreManager.getTopScoresFromFile(5, TEST_FILE_NAME);
        assertEquals(5, top5.size());
    }

    @AfterEach
    public void cleanup() {
        new File(TEST_FILE_NAME).delete();
    }
}
