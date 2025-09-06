// Interface gráfica

package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import engine.CalculadoraEngine;

public class CalculadoraUI extends JFrame implements ActionListener {
    private JTextField display;
    private JLabel operacaoLabel;
    private JButton[] botoes;
    private String[] rotulos = {
        "AC", "%", "<-", "÷",
        "7", "8", "9", "x",
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "00", "0", ".", "=",
    };

    private String operacaoExtensa = "";
    private boolean novoValor = true;

    private CalculadoraEngine engine;

    public CalculadoraUI() {
        engine = new CalculadoraEngine();
        initUI();
    }

    private void initUI() {
        setTitle("Calculadora");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(true);

        operacaoLabel = new JLabel("");
        operacaoLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        operacaoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        operacaoLabel.setForeground(Color.GREEN);
        operacaoLabel.setPreferredSize(new Dimension(300, 40));
        operacaoLabel.setOpaque(true);
        operacaoLabel.setBackground(Color.BLACK);
        operacaoLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        display = new JTextField();
        display.setEditable(true);
        display.setCaretColor(Color.GREEN);
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setBackground(Color.BLACK);
        display.setForeground(Color.GREEN);
        display.setPreferredSize(new Dimension(300, 120));

        JPanel painelSuperior = new JPanel();
        painelSuperior.setLayout(new BoxLayout(painelSuperior, BoxLayout.Y_AXIS));
        painelSuperior.add(operacaoLabel);
        painelSuperior.add(display);
        painelSuperior.setBackground(Color.BLACK);
        add(painelSuperior, BorderLayout.NORTH);

        botoes = new JButton[rotulos.length];
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(5,4));
        for (int i = 0; i < rotulos.length; i++) {
            final int index = i;
            botoes[i] = new JButton(rotulos[i]);
            botoes[i].setFont(new Font("Arial", Font.PLAIN, 24));
            botoes[i].setBackground(Color.BLACK);
            botoes[i].setForeground(Color.GREEN);
            botoes[i].setFocusable(false);
            botoes[i].addActionListener(this);
            painelBotoes.add(botoes[i]);
        }
        add(painelBotoes, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        // Aqui você pode implementar os eventos dos botões
        // Números, operadores, AC, igual etc.
        // Use engine.calcular() para fazer os cálculos
    }
}
