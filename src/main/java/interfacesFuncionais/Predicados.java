package interfacesFuncionais;

import java.util.function.Predicate;

public class Predicados {
    public static void main(String[] args) {
        Predicate<String> estaVazio = valor -> valor.isEmpty();
        System.out.println("Testando uma String vazia");
        System.out.println(estaVazio.test(""));
        System.out.println("Testando uma String NÃO vazia");
        System.out.println(estaVazio.test("Franco"));

    }

}
