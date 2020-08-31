package desenvAssincronoParalelo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class ParallelStream {

    public static void main(String[] args) {
        long inicio = System.currentTimeMillis();
        IntStream.range(1, 500).forEach(num -> fatorial(num));
        long fim = System.currentTimeMillis();
        System.out.println("Tempo de Execução Serial = "+(fim-inicio));

        inicio = System.currentTimeMillis();
        IntStream.range(1, 500).parallel().forEach(num -> fatorial(num));
        fim = System.currentTimeMillis();
        System.out.println("Tempo de Execução Parallel = "+(fim-inicio));

        List<String> nomes = Arrays.asList("Franco", "João", "Vanessa", "Laura");
        nomes.parallelStream().forEach(System.out::println);

    }

    public static long fatorial(long num) {
        long fat = 1;

        for (int i = 2; i < num; i++) {
            fat*=i;
        }
        return fat;
    }
}
