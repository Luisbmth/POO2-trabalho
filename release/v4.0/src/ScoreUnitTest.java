import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ScoreUnitTest {

    @Test
    public void testConstructorAndGetters() {
        Score score = new Score("Bruno", 5);
        assertEquals("Bruno", score.getName());
        assertEquals(5, score.getAttempts());
    }

    @Test
    public void testCompareTo() {
        Score score1 = new Score("Bruno", 5);
        Score score2 = new Score("João", 7);
        Score score3 = new Score("Ana", 5);

        assertTrue(score1.compareTo(score2) < 0);
        assertTrue(score2.compareTo(score1) > 0);
        assertEquals(0, score1.compareTo(score3));
    }

    @Test
    public void testToString() {
        Score score = new Score("Bruno", 5);
        assertEquals("Bruno,5", score.toString());
    }

    @Test
    public void testFromString() {
        String line = "Bruno,5";
        Score score = Score.fromString(line);

        assertEquals("Bruno", score.getName());
        assertEquals(5, score.getAttempts());
    }

    @Test
    public void testFromStringInvalidFormat() {
        // Verifica se lança IllegalArgumentException para formato inválido
        assertThrows(IllegalArgumentException.class, () -> {
            Score.fromString("Bruno");  // falta valor de attempts
        });

        // Verifica se lança NumberFormatException para attempts inválido
        assertThrows(NumberFormatException.class, () -> {
            Score.fromString("Bruno,abc");
        });
    }
}
