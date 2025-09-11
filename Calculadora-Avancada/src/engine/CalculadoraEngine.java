// lógica dos cálculos

// CalculadoraEngine.java
package engine;

public class CalculadoraEngine {
    private double valor1 = 0;
    private double valor2 = 0;
    private String operador = "";
    private String operadorAnterior = "";

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public void setOperadorAnterior(String operadorAnterior) {
        this.operadorAnterior = operadorAnterior;
    }

    public double calcular(double numeroAtual) {
    valor2 = numeroAtual;
    double resultado = 0;

    if (operador.equals("%")) {
        switch (operadorAnterior) {
            case "+":
                resultado = valor1 + (valor1 * valor2 / 100.0);
                break;
            case "-":
                resultado = valor1 - (valor1 * valor2 / 100.0);
                break;
            case "x":
                resultado = valor1 * (valor2 / 100.0);
                break;
            case "÷":
                if (valor2 != 0) resultado = valor1 / (valor2 / 100.0);
                else resultado = Double.POSITIVE_INFINITY;
                break;
            default:
                resultado = valor2 / 100.0;
                break;
        }
    } else {
        switch (operador) {
            case "+": resultado = valor1 + valor2; break;
            case "-": resultado = valor1 - valor2; break;
            case "x": resultado = valor1 * valor2; break;
            case "÷": 
                if (valor2 != 0) resultado = valor1 / valor2; 
                else resultado = Double.POSITIVE_INFINITY;
                break;
            default: resultado = valor2; break;
        }
    }

    valor1 = resultado;
    return resultado;
}


    public double getValor1() {
        return valor1;
    }

    public void reset() {
        valor1 = valor2 = 0;
        operador = "";
        operadorAnterior = "";
    }
}
