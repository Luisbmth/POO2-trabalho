import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaRanking extends JFrame {
    public TelaRanking() {
        setTitle("Ranking dos Melhores Jogadores");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 16));

        List<Score> scores = ScoreManager.getTopScores(10);
        StringBuilder sb = new StringBuilder("TOP 10 MELHORES JOGADORES\n\n");

        int pos = 1;
        for (Score score : scores) {
            sb.append(String.format("%2d. %-20s %3d tentativas\n", pos++, score.getName(), score.getAttempts()));
        }

        textArea.setText(sb.toString());
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        setVisible(true);
    }
}
