package desenvAssincronoParalelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class APIThreadJava8 {
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(3);

    public static void main(String[] args) throws InterruptedException {
        Casa casa = new Casa(new Quarto());
        List<Future<String>> futuros = new CopyOnWriteArrayList<Future<String>>(casa.obterAfazeresDaCasa().stream()
        .map(atividade -> threadPool.submit(() -> {
            try {
                return atividade.realizar();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        })
        ).collect(Collectors.toList()));

        while (true) {
            int contadorAtividadeNaoRealizada = 0;
            for (Future<?> futuro : futuros) {
                if (futuro.isDone()) {
                    try {
                        System.out.println("Tarefa concluída"+futuro.get());
                        futuros.remove(futuro);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                } else {
                    contadorAtividadeNaoRealizada++;
                }
            }
            if (futuros.stream().allMatch(Future::isDone)) {
                break;
            }
            System.out.println("Qtde de atividades não realizadas "+contadorAtividadeNaoRealizada);
            Thread.sleep(500);
        }

        threadPool.shutdown();

    }
}

class Casa {
    private List<Comodo> comodos;

    Casa(Comodo... comodos) { this.comodos = Arrays.asList(comodos); }

    List<Atividade> obterAfazeresDaCasa() {
        return this.comodos.stream().map(Comodo::obterAfazeresDoComodo)
                .reduce(new ArrayList<Atividade>(), (pivo, atividades) -> {
                    pivo.addAll(atividades);
                    return pivo;
                });
    }
}

interface  Atividade {
    String realizar() throws InterruptedException;
}

abstract  class Comodo {
    abstract List<Atividade> obterAfazeresDoComodo();
}

class Quarto extends Comodo {
    @Override
    List<Atividade> obterAfazeresDoComodo() {
        return Arrays.asList(
                this::getArrumarACama,  // = () -> this.getArrumarACama()
                this::varrerOQuarto,    // = () -> this.varrerOQuarto()
                this::arrumarGuardaRoupa// = () -> this.arrumarGuardaRoupa()

        );
    }

    private String arrumarGuardaRoupa() throws InterruptedException {
        Thread.sleep(5000);
        String gr = "Arrumar o guarda roupa!";
        System.out.println("Arrumar o guarda roupa!");
        return gr;
    }

    private String varrerOQuarto() throws InterruptedException {
        Thread.sleep(3000);
        String q = "Varrer quarto!";
        System.out.println("Varrer quarto!");
        return q;
    }

    private String getArrumarACama() throws InterruptedException {
        Thread.sleep(1000);
        String c = "Arrumar a cama!";
        System.out.println("Arrumar a cama!");
        return c;
    }
}

