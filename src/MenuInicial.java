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
        JLabel titulo = new JLabel("JOGO DA MEMÓRIA", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botão Jogar
        JButton btnJogar = new JButton("Jogar");
        btnJogar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnJogar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Aqui você chamaria a tela do jogo
                JOptionPane.showMessageDialog(null, "Iniciar o jogo...");
            }
        });

        // Botão Instruções
        JButton btnInstrucoes = new JButton("Instruções");
        btnInstrucoes.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnInstrucoes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "Combine os pares de cartas iguais.\nClique em duas cartas para revelá-las.");
            }
        });

        // Botão Sair
        JButton btnSair = new JButton("Sair");
        btnSair.setAlignmentX(Component.CENTER_ALIGNMENT);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuInicial());
    }
}
