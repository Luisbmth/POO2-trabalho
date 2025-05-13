import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import javax.swing.Timer;

public class MemoryGame {
    private JFrame frame = new JFrame("Jogo da Memória");
    private JPanel panel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            ImageIcon backgroundImage = new ImageIcon("../../assets/mesa.jpg");
            g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this); // Preenche 100% do fundo
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

    public MemoryGame() {
        this.panel.setLayout(new GridLayout(4, 3, 10, 10)); // 4 linhas x 3 colunas, com espaçamento
        this.buttons = new JButton[12];

        // Carrega e redimensiona a imagem de trás da carta
        ImageIcon backCardImage = new ImageIcon("../../assets/back-card.png");
        Image resizedImage = backCardImage.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH);
        this.cardBack = new ImageIcon(resizedImage);

        this.cards = new ImageIcon[6];
        this.cardValues = new ArrayList<>();

        // Imagens dos pares
        this.cards[0] = new ImageIcon("../../assets/DaviDance.gif");
        this.cards[1] = new ImageIcon("../../assets/DaviCrying.gif");
        this.cards[2] = new ImageIcon("../../assets/DaviTired.gif");
        this.cards[3] = new ImageIcon("../../assets/DaviCalma.gif");
        this.cards[4] = new ImageIcon("../../assets/DaviParty.gif");
        this.cards[5] = new ImageIcon("../../assets/DaviShower.gif");

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

        frame.add(panel);
        frame.setSize(650, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void onCardClick(int index) {
        if (!canClick) return;
        if (buttons[index] == firstCard || buttons[index].getIcon() != cardBack) return;

        buttons[index].setIcon(cards[cardValues.get(index)]);

        if (firstCard == null) {
            firstCard = buttons[index];
            firstIndex = index;
        } else if (secondCard == null) {
            secondCard = buttons[index];
            secondIndex = index;
            canClick = false;

            Timer timer = new Timer(1000, e -> {
                if (cardValues.get(firstIndex).equals(cardValues.get(secondIndex))) {
                    firstCard.setEnabled(false);
                    secondCard.setEnabled(false);
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