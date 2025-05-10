import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class MemoryGame {
   private JFrame frame = new JFrame("Jogo da Memória");
   private JPanel panel = new JPanel();
   private JButton[] buttons;
   private ImageIcon cardBack; // Imagem de fundo da carta
   private ImageIcon[] cards; // Imagens das cartas
   private List<Integer> cardValues; // Valores das cartas para comparação

   private JButton firstCard, secondCard;
   private int firstIndex, secondIndex;

   public MemoryGame() {
      this.panel.setLayout(new GridLayout(4, 4));
      this.buttons = new JButton[16];
      this.cardBack = new ImageIcon("assets/DaviDance.gif"); // Imagem de fundo (carta virada para baixo)
      this.cards = new ImageIcon[8]; // Supondo 8 pares de cartas
      this.cardValues = new ArrayList<>();

      // Carregar as imagens para os pares de cartas (Aqui, estamos usando a mesma imagem para todos os pares)
      for (int i = 0; i < 8; i++) {
         this.cards[i] = new ImageIcon("assets/DaviDance.gif"); // Pode adicionar outras imagens para diferentes pares
         this.cardValues.add(i);
         this.cardValues.add(i); // Adiciona duas vezes para formar um par
      }

      // Embaralhar os valores das cartas para que elas fiquem em posições aleatórias
      Collections.shuffle(cardValues);

      // Exibir as cartas viradas para baixo inicialmente
      for (int var1 = 0; var1 < 16; ++var1) {
         this.buttons[var1] = new JButton(cardBack);
         this.buttons[var1].addActionListener((var1x) -> {
            this.onCardClick(var1x);
         });
         this.panel.add(this.buttons[var1]);
      }

      this.frame.add(this.panel);
      this.frame.setSize(400, 400);
      this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.frame.setVisible(true);
   }

   private void onCardClick(ActionEvent var1) {
      JButton clickedCard = (JButton)var1.getSource();
      int clickedIndex = -1;

      // Encontrar o índice do botão clicado
      for (int i = 0; i < buttons.length; i++) {
         if (buttons[i] == clickedCard) {
            clickedIndex = i;
            break;
         }
      }

      // Verifica se a carta clicada é a primeira ou segunda
      if (firstCard == null) {
         firstCard = clickedCard;
         firstIndex = clickedIndex;
         clickedCard.setIcon(cards[cardValues.get(firstIndex)]); // Mostra a carta virada
      } else if (secondCard == null && clickedCard != firstCard) {
         secondCard = clickedCard;
         secondIndex = clickedIndex;
         clickedCard.setIcon(cards[cardValues.get(secondIndex)]); // Mostra a carta virada

         // Verificar se as cartas são iguais
         if (cardValues.get(firstIndex) != cardValues.get(secondIndex)) {
            // Se não forem iguais, virá-las novamente após um pequeno intervalo
            Timer timer = new Timer(1000, e -> {
                firstCard.setIcon(cardBack);
                secondCard.setIcon(cardBack);
                firstCard = null;
                secondCard = null;
            });
            timer.setRepeats(false);
            timer.start();
         } else {
            // Se forem iguais, manter as cartas viradas
            firstCard = null;
            secondCard = null;
         }
      }
   }

   public static void main(String[] var0) {
      SwingUtilities.invokeLater(MemoryGame::new);
   }
}
