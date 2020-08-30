package paradigmaFuncional;

import java.util.function.UnaryOperator;

public class ParadigmaFuncional {
    public static void main(String[] args) {
        UnaryOperator<Integer> calcularValorVezes3 = valor -> valor*3; // programação funcional
        int valor = 10;
        System.out.println("O resultado é = " + calcularValorVezes3.apply(valor));
    }
}
