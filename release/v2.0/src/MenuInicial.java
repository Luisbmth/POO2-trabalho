import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuInicial extends JFrame {

    public MenuInicial() {
        setTitle("Jogo da Memória");
        setSize(400, 300);
        setLocationRelativeTo(null); // Centraliza a janela
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Painel principal
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Título
        JLabel titulo = new JLabel("JOGO DA MEMORIA", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botão Jogar
        JButton btnJogar = criarBotao("Jogar");
        btnJogar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fecha o menu inicial
                SwingUtilities.invokeLater(() -> new MemoryGame()); // Inicia o jogo
            }
        });

        // Botão Instruções
        JButton btnInstrucoes = criarBotao("Instrucoes");
        btnInstrucoes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "Combine os pares de cartas iguais.\nClique em duas cartas para revelá-las.");
            }
        });

        // Botão Sair
        JButton btnSair = criarBotao("Sair");
        btnSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Adiciona os componentes ao painel
        painel.add(titulo);
        painel.add(Box.createVerticalStrut(30));
        painel.add(btnJogar);
        painel.add(Box.createVerticalStrut(15));
        painel.add(btnInstrucoes);
        painel.add(Box.createVerticalStrut(15));
        painel.add(btnSair);

        // Adiciona painel à janela
        add(painel);
        setVisible(true);
    }

    private JButton criarBotao(String texto) {
    JButton botao = new JButton(texto);
    botao.setAlignmentX(Component.CENTER_ALIGNMENT);
    Dimension tamanho = new Dimension(200, 50); // tamanho uniforme
    botao.setPreferredSize(tamanho);
    botao.setMaximumSize(tamanho); // <- força o botão a não passar desse tamanho
    botao.setMinimumSize(tamanho);
    botao.setFont(new Font("Arial", Font.BOLD, 16));
    botao.setBackground(new Color(70, 130, 180));
    botao.setForeground(Color.WHITE);
    botao.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 2));

    botao.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            botao.setBackground(new Color(100, 149, 237));
        }

        public void mouseExited(java.awt.event.MouseEvent evt) {
            botao.setBackground(new Color(70, 130, 180));
        }
    });

    return botao;
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuInicial());
    }
}
