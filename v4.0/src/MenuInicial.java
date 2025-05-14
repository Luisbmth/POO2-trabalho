import javax.swing.*;
import java.awt.*;

public class MenuInicial extends JFrame {

    public MenuInicial() {
        setTitle("Jogo da Memoria");
        setSize(400, 380);
        setLocationRelativeTo(null); // Centraliza a janela
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        // Painel principal com fundo semelhante a cortiça
        JPanel painel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(new Color(205, 133, 63)); // cor tipo cortiça
            }
        };
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Título
        JLabel titulo = new JLabel("JOGO DA MEMORIA", JLabel.CENTER);
        titulo.setFont(new Font("Verdana", Font.BOLD, 26));
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botões
        JButton btnJogar = criarBotao("JOGAR");
        btnJogar.addActionListener(e -> {
            dispose(); // Fecha o menu inicial
            SwingUtilities.invokeLater(() -> new MemoryGame()); // Inicia o jogo
        });

        JButton btnInstrucoes = criarBotao("INSTRUCOES");
        btnInstrucoes.addActionListener(e -> {
            JOptionPane.showMessageDialog(null,
                    "Combine os pares de cartas iguais.\nClique em duas cartas para revelá-las.");
        });

        JButton btnRanking = criarBotao("RANKING");
        btnRanking.addActionListener(e -> new TelaRanking());

        // Adiciona os componentes ao painel principal
        painel.add(titulo);
        painel.add(Box.createVerticalStrut(30));
        painel.add(btnJogar);
        painel.add(Box.createVerticalStrut(15));
        painel.add(btnInstrucoes);
        painel.add(Box.createVerticalStrut(15));
        painel.add(btnRanking);

        // Painel inferior com botão de sair
        JButton btnSair = criarBotao("SAIR");
        btnSair.addActionListener(e -> System.exit(0));

        JPanel painelInferior = new JPanel();
        painelInferior.setBackground(new Color(205, 133, 63));
        painelInferior.setLayout(new FlowLayout(FlowLayout.CENTER));
        painelInferior.add(btnSair);

        // Adiciona painéis ao frame
        add(painel, BorderLayout.CENTER);
        add(painelInferior, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        Dimension tamanho = new Dimension(200, 50);
        botao.setPreferredSize(tamanho);
        botao.setMaximumSize(tamanho);
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
        SwingUtilities.invokeLater(MenuInicial::new);
    }
}
