import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * Classe que representa o jogo da memória.
 * Implementa a interface gráfica, lógica do jogo,
 * contagem de tentativas e controle de pares encontrados.
 * 
 * O jogo utiliza imagens como cartas e permite que o jogador
 * reinicie ou retorne ao menu inicial.
 * 
 * @author Luis Felipe Leite Santos, João Marcello Santos, Caio de Andrade Ferreira e Bruno de Alencar
 * @version 4.0
 */
public class MemoryGame {
    private JFrame frame = new JFrame("Jogo da Memoria");

    private JPanel panel = new JPanel() {
        private Image backgroundImage = new ImageIcon("../../../assets/mesa.png").getImage();

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

    private JLabel lblTentativas;
    private JLabel lblPares;


    /**
     * Construtor da classe MemoryGame.
     * Inicializa a interface gráfica, embaralha as cartas e
     * configura os botões, eventos e layout do jogo.
     */

    public MemoryGame() {
        panel.setLayout(new GridLayout(4, 3, 10, 10));
        buttons = new JButton[12];

        ImageIcon backCardImage = new ImageIcon("../../../assets/back-card.png");
        Image resizedImage = backCardImage.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH);
        cardBack = new ImageIcon(resizedImage);

        cards = new ImageIcon[6];
        cardValues = new ArrayList<>();

        cards[0] = new ImageIcon("../../../assets/DaviDance.gif");
        cards[1] = new ImageIcon("../../../assets/DaviCrying.gif");
        cards[2] = new ImageIcon("../../../assets/DaviTired.gif");
        cards[3] = new ImageIcon("../../../assets/DaviCalma.gif");
        cards[4] = new ImageIcon("../../../assets/DaviParty.gif");
        cards[5] = new ImageIcon("../../../assets/DaviShower.gif");

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

        // Painel superior com tentativas e pares
        lblTentativas = new JLabel("Tentativas: 0");
        lblTentativas.setFont(new Font("Arial", Font.BOLD, 16));
        lblTentativas.setForeground(Color.BLACK);

        lblPares = new JLabel("Pares: 0/6");
        lblPares.setFont(new Font("Arial", Font.BOLD, 16));
        lblPares.setForeground(Color.BLACK);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        topPanel.setBackground(new Color(245, 222, 179)); // Cor wheat bege clara para o fundo do contador
        topPanel.add(lblTentativas);
        topPanel.add(lblPares);

        // Painel inferior com botões
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
            new MenuInicial();
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.add(btnReiniciar);
        bottomPanel.add(btnMenu);

        // Layout final
        frame.setLayout(new BorderLayout());
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setSize(650, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Método chamado quando um botão (carta) é clicado.
     * Gerencia a lógica de virar cartas, verificar pares,
     * atualiza tentativas e checar o fim do jogo.
     * 
     * @param index O índice da carta clicada no array de botões.
     */

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
            lblTentativas.setText("Tentativas: " + attempts);

            Timer timer = new Timer(1000, e -> {
                if (cardValues.get(firstIndex).equals(cardValues.get(secondIndex))) {
                    firstCard.setEnabled(false);
                    secondCard.setEnabled(false);
                    matchedPairs++;
                    lblPares.setText("Pares: " + matchedPairs + "/6");

                    if (matchedPairs == 6) {
                        String nome = JOptionPane.showInputDialog(frame, "Parabens! Voce finalizou com " + attempts + " tentativas.\nDigite seu nome:");
                        if (nome != null && !nome.trim().isBlank()) {
                            ScoreManager.saveScore(new Score(nome.trim(), attempts));
                        }

                        int opcao = JOptionPane.showConfirmDialog(frame, "Deseja ver o ranking dos 10 melhores?", "Ranking", JOptionPane.YES_NO_OPTION);
                        if (opcao == JOptionPane.YES_OPTION) {
                            new TelaRanking();
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


    /**
     * Método principal que inicia o jogo da memória.
     * 
     * @param args Argumentos de linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MemoryGame::new);
    }
}
