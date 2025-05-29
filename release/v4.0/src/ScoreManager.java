import java.io.*;
import java.util.*;

public class ScoreManager {
    private static final String FILE_NAME = "placar.csv";

    public static void saveScore(Score score) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(score.toString());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Score> getTopScores(int maxScores) {
        List<Score> scores = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                scores.add(Score.fromString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(scores);
        return scores.subList(0, Math.min(scores.size(), maxScores));
    }






    
    public static void saveScoreToFile(Score score, String fileName) {
        try (FileWriter fw = new FileWriter(fileName, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(score.toString());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static List<Score> getTopScoresFromFile(int maxScores, String fileName) {
        List<Score> scores = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                scores.add(Score.fromString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(scores);
        return scores.subList(0, Math.min(scores.size(), maxScores));
    }
}

