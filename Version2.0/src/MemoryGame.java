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
        // Sobrescreve o método paintComponent para desenhar o fundo
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Carrega a imagem de fundo
            ImageIcon backgroundImage = new ImageIcon("../../assets/mesa_de_madeira.jpg"); // Imagem de fundo
            g.drawImage(backgroundImage.getImage(), 0, 0, this);  // Desenha a imagem no fundo
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
        this.panel.setLayout(new GridLayout(4, 3, 10, 10)); // 4 linhas x 3 colunas, com espaçamento de 10 entre as cartas
        this.buttons = new JButton[12];
        this.cardBack = new ImageIcon(""); // Card back (imagem de fundo da carta)

        this.cards = new ImageIcon[6];
        this.cardValues = new ArrayList<>();

        // NÃO ALTERAR: Imagens definidas conforme solicitado
        this.cards[0] = new ImageIcon("../../assets/DaviDance.gif");
        this.cards[1] = new ImageIcon("../../assets/DaviCrying.gif");
        this.cards[2] = new ImageIcon("../../assets/DaviTired.gif");
        this.cards[3] = new ImageIcon("../../assets/DaviCalma.gif");
        this.cards[4] = new ImageIcon("../../assets/DaviParty.gif");
        this.cards[5] = new ImageIcon("../../assets/DaviShower.gif");

        // Adiciona 6 pares (valores de 0 a 5)
        for (int i = 0; i < 6; i++) {
            cardValues.add(i);
            cardValues.add(i);
        }

        Collections.shuffle(cardValues);

        for (int i = 0; i < 12; ++i) {
            final int index = i;
            buttons[i] = new JButton();
            buttons[i].setIcon(cardBack);
            buttons[i].setHorizontalAlignment(SwingConstants.CENTER); // Centraliza a imagem horizontalmente
            buttons[i].setVerticalAlignment(SwingConstants.CENTER); // Centraliza a imagem verticalmente
            buttons[i].setBorder(new LineBorder(new Color(139, 69, 19), 3)); // Linha marrom de 3px
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
                    // Par correto: manter as cartas viradas
                    firstCard.setEnabled(false);
                    secondCard.setEnabled(false);
                } else {
                    // Par incorreto: virar de volta
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
