// Interface gráfica
// CalculadoraUI.java
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
    private String operadorAtual = "";
    private String operadorAnterior = "";

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

        // Operação Label
        operacaoLabel = new JLabel("");
        operacaoLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        operacaoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        operacaoLabel.setForeground(Color.GREEN);
        operacaoLabel.setPreferredSize(new Dimension(300, 40));
        operacaoLabel.setOpaque(true);
        operacaoLabel.setBackground(Color.BLACK);
        operacaoLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        // Display principal
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

        // Painel de botões
        botoes = new JButton[rotulos.length];
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(5,4));

        for (int i=0; i<rotulos.length; i++) {
            final int index = i;
            botoes[i] = new JButton(rotulos[i]);
            botoes[i].setFont(new Font("Arial", Font.PLAIN, 24));
            botoes[i].setBackground(Color.BLACK);
            botoes[i].setForeground(Color.GREEN);
            botoes[i].setFocusable(false);
            botoes[i].addActionListener(this);
            painelBotoes.add(botoes[i]);

            botoes[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    botoes[index].setBackground(Color.BLUE);
                }
                @Override
                public void mouseReleased(MouseEvent e) {
                    botoes[index].setBackground(Color.BLACK);
                }
            });
        }

        add(painelBotoes, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        // Números e ponto
        if (comando.matches("[0-9]") || comando.equals("00")) {
            if (comando.equals("00")) {
                if (novoValor) {
                    display.setText("0");
                    novoValor = false;
                } else {
                    display.setText(display.getText() + "00");
                }
            } else {
                if (novoValor) {
                    display.setText(comando);
                    novoValor = false;
                } else {
                    display.setText(display.getText() + comando);
                }
            }
            operacaoExtensa += comando;
            operacaoLabel.setText(operacaoExtensa);
        }

        // Operadores aritméticos
        else if (comando.equals("+") || comando.equals("-") || comando.equals("x") || comando.equals("÷")) {
            double numeroAtual = parseDisplay();

            if (!operadorAtual.isEmpty() && !operadorAtual.equals("%")) {
                double resultado = engine.calcular(numeroAtual);
                display.setText(format(resultado));
            } else {
                engine.calcular(numeroAtual);
            }

            operadorAtual = comando;
            engine.setOperador(comando);
            operacaoExtensa += " " + comando + " ";
            operacaoLabel.setText(operacaoExtensa);
            novoValor = true;
        }

        // Porcentagem
        else if (comando.equals("%")) {
            double numeroAtual = parseDisplay();
            if (operadorAtual.isEmpty()) {
                numeroAtual = numeroAtual / 100.0;
                display.setText(format(numeroAtual));
                engine.calcular(numeroAtual);
                operadorAtual = "%";
            } else {
                operadorAnterior = operadorAtual;
                engine.setOperadorAnterior(operadorAnterior);
                switch (operadorAtual) {
                    case "+": case "-": numeroAtual = engine.getValor1() * numeroAtual / 100.0; break;
                    case "x": case "÷": numeroAtual = numeroAtual / 100.0; break;
                }
                display.setText(format(numeroAtual));
                operadorAtual = "%";
            }
            operacaoExtensa += "%";
            operacaoLabel.setText(operacaoExtensa);
            novoValor = true;
        }

        // Igual
        else if (comando.equals("=")) {
            double numeroAtual = parseDisplay();
            double resultado = engine.calcular(numeroAtual);
            display.setText(format(resultado));
            operacaoExtensa = "";
            operacaoLabel.setText(operacaoExtensa);
            operadorAtual = "";
            operadorAnterior = "";
            novoValor = true;
        }

        // Limpar tudo
        else if (comando.equals("AC")) {
            display.setText("");
            operacaoLabel.setText("");
            engine.reset();
            operadorAtual = "";
            operadorAnterior = "";
            operacaoExtensa = "";
            novoValor = true;
        }

        // Backspace
        else if (comando.equals("<-")) {
            String texto = display.getText();
            if (!texto.isEmpty()) {
                display.setText(texto.substring(0, texto.length()-1));
                if (!operacaoExtensa.isEmpty()) {
                    operacaoExtensa = operacaoExtensa.substring(0, operacaoExtensa.length()-1);
                    operacaoLabel.setText(operacaoExtensa);
                }
            }
        }

        // Ponto decimal
        else if (comando.equals(".") || comando.equals(",")) {
            String texto = display.getText();
            if (!texto.contains(".") && !texto.contains(",")) {
                display.setText(texto + ",");
                operacaoExtensa += ",";
                operacaoLabel.setText(operacaoExtensa);
                novoValor = false;
            }
        }
    }

    private double parseDisplay() {
        String txt = display.getText().replace(',', '.');
        if (txt.isEmpty()) return 0;
        try {
            return Double.parseDouble(txt);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private String format(double v) {
        if (Double.isInfinite(v)) return "Erro";
        if (v == (long)v) return String.valueOf((long)v);
        else return String.valueOf(v);
    }

}
