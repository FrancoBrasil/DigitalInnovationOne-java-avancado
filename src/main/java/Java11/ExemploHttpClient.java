package Java11;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ExemploHttpClient {

    static ExecutorService executorService = Executors.newFixedThreadPool(6, new ThreadFactory() {

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            System.out.println("Nova Thread Criada = "+ (thread.isDaemon() ? "daemo" : "") +
                    " Thread Group = " + thread.getThreadGroup());
            return thread;
        }
    });

    public static void main(String[] args) throws IOException, InterruptedException {
        connectAndPrintURIJavaOracle();
    }

    private static void connectAkHttpClient() throws IOException, InterruptedException {
        System.out.println("Running Http/1.1");
        try {
            HttpClient httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .build();
            long start = System.currentTimeMillis();

            HttpRequest mainRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://http2.akami.com/demo/h2_demo_frame.html"))
                    .build();

            HttpResponse<String> response = httpClient.send(mainRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status code = " + response.statusCode());
            System.out.println("Response Headers = " + response.headers());
            String responseBody = response.body();
            System.out.println(response.body());

            List<Future<?>> futures = new ArrayList<>();

            responseBody
                    .lines()
                    .filter(line -> line.trim().startsWith("<img height"))
                    .map(line -> line.substring(line.indexOf("scr='") + 5, line.indexOf("'/>")))
                    .forEach(image -> {
                        Future<?> imgFuture = executorService.submit(() -> {
                            HttpRequest imgRequest = HttpRequest.newBuilder()
                                    .uri(URI.create("https://http2.akami.com" + image))
                                    .build();

                            try {
                                HttpResponse<String> imgResponse = httpClient.send(imgRequest, HttpResponse.BodyHandlers.ofString());
                                System.out.println("Imagem carregada = " + image + ", status code = " + imgResponse.statusCode());

                            } catch (IOException | InterruptedException e) {
                                System.out.println("Mensagem de erro durante requisição para recuperar imagem" + image);
                            }
                        });
                        futures.add(imgFuture);
                        System.out.println("Submetido um futuro para imagem = " + image);
                    });

            futures.forEach(f -> {
                try {
                    f.get();
                } catch (InterruptedException | ExecutionException e) {
                    System.out.println("Erro ao esperar carregar imagem de future");
                }
            });

            long end = System.currentTimeMillis();
            System.out.println("Tempo total de carregamento = " + (end - start) + " ms");

        } finally {
            executorService.shutdown();
        }
    }

    private static void connectAndPrintURIJavaOracle() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET().uri(URI.create("https://docs.oracle.com/javase/10/language/"))
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status code = " + response.statusCode());
        System.out.println("Headers response = " + response.headers());
        System.out.println(response.body());

    }
}
