import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class MemoryGame {
    private JFrame frame = new JFrame("Jogo da Memória");

    // Painel com imagem de fundo
    private JPanel panel = new JPanel() {
        private Image backgroundImage = new ImageIcon("../../assets/mesa.png").getImage();

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    };

    private JButton[] buttons;
    private ImageIcon cardBack;
    private ImageIcon[] cards;
    private List<Integer> cardValues;

    private JButton firstCard = null;
    private JButton secondCard = null;
    private int firstIndex = -1;
    private int secondIndex = -1;
    private boolean canClick = true;

    private int attempts = 0;
    private int matchedPairs = 0;

    public MemoryGame() {
        panel.setLayout(new GridLayout(4, 3, 10, 10));
        buttons = new JButton[12];

        ImageIcon backCardImage = new ImageIcon("../../assets/back-card.png");
        Image resizedImage = backCardImage.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH);
        cardBack = new ImageIcon(resizedImage);

        cards = new ImageIcon[6];
        cardValues = new ArrayList<>();

        // Imagens dos pares
        cards[0] = new ImageIcon("../../assets/DaviDance.gif");
        cards[1] = new ImageIcon("../../assets/DaviCrying.gif");
        cards[2] = new ImageIcon("../../assets/DaviTired.gif");
        cards[3] = new ImageIcon("../../assets/DaviCalma.gif");
        cards[4] = new ImageIcon("../../assets/DaviParty.gif");
        cards[5] = new ImageIcon("../../assets/DaviShower.gif");

        for (int i = 0; i < 6; i++) {
            cardValues.add(i);
            cardValues.add(i);
        }

        Collections.shuffle(cardValues);

        for (int i = 0; i < 12; ++i) {
            final int index = i;
            buttons[i] = new JButton();
            buttons[i].setIcon(cardBack);
            buttons[i].setHorizontalAlignment(SwingConstants.CENTER);
            buttons[i].setVerticalAlignment(SwingConstants.CENTER);
            buttons[i].setBorder(new LineBorder(new Color(139, 69, 19), 3));
            buttons[i].addActionListener(e -> onCardClick(index));
            panel.add(buttons[i]);
        }

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);

        JButton btnReiniciar = new JButton("Reiniciar");
        btnReiniciar.setFont(new Font("Arial", Font.BOLD, 16));
        btnReiniciar.setBackground(new Color(70, 130, 180));
        btnReiniciar.setForeground(Color.WHITE);
        btnReiniciar.addActionListener(e -> {
            frame.dispose();
            new MemoryGame();
        });

        JButton btnMenu = new JButton("Menu");
        btnMenu.setFont(new Font("Arial", Font.BOLD, 16));
        btnMenu.setBackground(new Color(70, 130, 180));
        btnMenu.setForeground(Color.WHITE);
        btnMenu.addActionListener(e -> {
            frame.dispose();
            new MenuInicial(); // Certifique-se de que a classe MenuInicial está implementada
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.add(btnReiniciar);
        bottomPanel.add(btnMenu);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setSize(650, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void onCardClick(int index) {
        if (!canClick || buttons[index] == firstCard || buttons[index].getIcon() != cardBack) return;

        buttons[index].setIcon(cards[cardValues.get(index)]);

        if (firstCard == null) {
            firstCard = buttons[index];
            firstIndex = index;
        } else if (secondCard == null) {
            secondCard = buttons[index];
            secondIndex = index;
            canClick = false;
            attempts++;

            Timer timer = new Timer(1000, e -> {
                if (cardValues.get(firstIndex).equals(cardValues.get(secondIndex))) {
                    firstCard.setEnabled(false);
                    secondCard.setEnabled(false);
                    matchedPairs++;
                    if (matchedPairs == 6) {
                        String nome = JOptionPane.showInputDialog(frame, "Parabéns! Você finalizou com " + attempts + " tentativas.\nDigite seu nome:");
                        if (nome != null && !nome.trim().isEmpty()) {
                            ScoreManager.saveScore(new Score(nome.trim(), attempts));
                        }

                        int opcao = JOptionPane.showConfirmDialog(frame, "Deseja ver o ranking dos 10 melhores?", "Ranking", JOptionPane.YES_NO_OPTION);
                        if (opcao == JOptionPane.YES_OPTION) {
                            new TelaRanking(); // Certifique-se de que esta classe está acessível
                        }
                    }
                } else {
                    firstCard.setIcon(cardBack);
                    secondCard.setIcon(cardBack);
                }
                firstCard = null;
                secondCard = null;
                canClick = true;
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MemoryGame::new);
    }
}
