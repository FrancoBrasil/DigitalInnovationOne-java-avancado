package desenvAssincronoParalelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class API2ThreadJava8 {

    private static final ExecutorService threadPool = Executors.newFixedThreadPool(6);

    public static void main(String[] args) throws InterruptedException {
        Venda vendas = new Venda(new Pedido());
        List<Future<String>> futuros = new CopyOnWriteArrayList<Future<String>>(vendas.obterCliente().stream()
                .map(atividade -> threadPool.submit(() -> {
                            try {
                                return atividade.realizarTarefa();
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

class Venda {
    private List<Processamento> processos;

    Venda(Processamento... processos) { this.processos = Arrays.asList(processos); }

    List<Tarefa> obterCliente() {
        return this.processos.stream().map(Processamento::obterProcessamento)
                .reduce(new ArrayList<Tarefa>(), (pivo, tarefas) -> {
                    pivo.addAll(tarefas);
                    return pivo;
                });
    }
}

interface  Tarefa {
    String realizarTarefa() throws InterruptedException;
}

abstract  class Processamento {
    abstract List<Tarefa> obterProcessamento();
}

class Pedido extends Processamento {
    @Override
    List<Tarefa> obterProcessamento() {
        return Arrays.asList(
                this::validarUsuario,
                this::validarEstoque,
                this::validarPgto,
                this::validarFormaPgto,
                this::vendaConcluida

        );
    }

    private String validarUsuario() throws InterruptedException {
        Thread.sleep(3000);
        String cadastro = " --> Validar Usuário";
        return cadastro;
    }

    private String validarEstoque() throws InterruptedException {
        Thread.sleep(5000);
        String estoque = " --> Validar Produto no Estoque";
        return estoque;
    }

    private String validarFormaPgto() throws InterruptedException {
        Thread.sleep(8000);
        String pgto = " --> Validar Forma de Pgto!";
        return pgto;
    }

    private String validarPgto() throws InterruptedException {
        Thread.sleep(10000);
        String pgto = " --> Validar Pgto!";
        return pgto;
    }

    private String vendaConcluida() throws InterruptedException {
        Thread.sleep(12000);
        String pgto = " --> COMPRA EFETUADA COM SUCESSO!";
        return pgto;
    }
}