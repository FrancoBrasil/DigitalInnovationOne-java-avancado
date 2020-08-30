package interfacesFuncionais;

import java.util.function.Function;

public class Functions {
    public static void main(String[] args) {
        Function<String, String> retornarNomeAoContrario = texto -> new StringBuilder(texto).reverse().toString();
        Function<String, Integer> converterStringParaInteger = string -> Integer.valueOf(string);
        System.out.println("Retorna o nome João ao contrário");
        System.out.println(retornarNomeAoContrario.apply("joao"));
        System.out.println("Converte String em Integer");
        System.out.println(converterStringParaInteger.apply("20"));
    }
}
