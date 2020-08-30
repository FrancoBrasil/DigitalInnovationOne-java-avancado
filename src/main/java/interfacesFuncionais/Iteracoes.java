package interfacesFuncionais;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Iteracoes {
    public static void main(String[] args) {
        String[] nomes = {"Joao", "Paulo", "Oliveira", "Santos", "Instrutor", "Java"};
        Integer[] numeros = {1, 2, 3, 4, 5};
      //  imprimirNomesFiltrados(nomes);
      //  imprimirTodosNomes(nomes);
        imprimirDobroDeCadaItem(numeros);

        List<String> list = new ArrayList<>();
        list.add("Java");
        list.add(("Python"));
        list.add("C#");
        list.add("C++");
        list.add("Web Services");
        System.out.println("Lista de Linguagens que começam com a letra C");
        list.stream().filter(nome -> nome.startsWith("C")).forEach(System.out::println);
    }

    public static void imprimirNomesFiltrados(String... nomes) {
        String nomesParaImprimir= "";
        for (int i =0; i <nomes.length; i++) {
            if (nomes[i].equals("Joao")) {
                nomesParaImprimir+=""+nomes[i];
            }
        }
        System.out.println("Utilizando o for - Programação Imperativa");
        System.out.println(nomesParaImprimir);

        String nomeImpressoStream = Stream.of(nomes)
                .filter(nome -> nome.equals("Joao"))
                .collect(Collectors.joining());

        System.out.println("Utilizando Stream - Programação Funcional Declarativa");
        System.out.println(nomeImpressoStream);

    }

    public static void imprimirTodosNomes(String... nomes) {
        for (String nome : nomes) {
            System.out.println("Imprimindo pelo for" + nome);
        }
        ;
        Stream.of(nomes).forEach(nome -> System.out.println("Imprimindo pelo foreach: " + nome));
    }

    public static void imprimirDobroDeCadaItem(Integer... numeros) {// Integer... msm coisa q Integer[]
      //  for (Integer n : numeros) {
      //      System.out.println(n*2);
     //   }
        Stream.of(numeros).map(numero -> numero *2).forEach(System.out::println);
    }
}
