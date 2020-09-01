package Java11;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

public class ExemploRequisicaoHttp {

    public static void main(String[] args) {
        connectAndPrintURLJavaOracle();
    }

    private static void connectAndPrintURLJavaOracle() {
        try {
            var url = new URL("https://docs.oracle.com/javase/10/language/");
            var urlConnection = url.openConnection();

            try(var bufferedRead = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
                System.out.println(bufferedRead.lines().collect(Collectors.joining()).replaceAll(" ", "\n"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

}
}