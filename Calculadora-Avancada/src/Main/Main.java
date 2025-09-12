// Classe principal para rodar

package Main;

import javax.swing.SwingUtilities;

import ui.CalculadoraUI;

public class Main {
    public static void main(String[] args) {
       SwingUtilities.invokeLater(() -> new CalculadoraUI().setVisible(true));
    }
}
