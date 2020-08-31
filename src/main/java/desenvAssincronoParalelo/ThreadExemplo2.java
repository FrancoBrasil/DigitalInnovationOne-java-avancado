package desenvAssincronoParalelo;

public class ThreadExemplo2 {
    public static void main(String[] args) {

        Thread thread1 = new Thread(new BarraDeCarregamento22());
        Thread thread2 = new Thread(new BarraDeCarregamento33());

        thread1.start();
        thread2.start();
        System.out.println("Nome da Thread : " + thread1.getName());
        System.out.println("Nome da Thread : " + thread2.getName());
    }

}

class GerarPDF1 implements Runnable {
    @Override
    public void run() {
        System.out.println("Gerar PDF");
    }
}

class BarraDeCarregamento1 implements Runnable {
    @Override
    public void run() {
        System.out.println("Loading.....");
    }
}

class BarraDeCarregamento22 implements Runnable {
    @Override
    public void run() {

        try {
            Thread.sleep(3000);
            System.out.println("rodei barra 2 ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class BarraDeCarregamento33 implements Runnable {
    @Override
    public void run() {

        try {
            Thread.sleep(1000);
            System.out.println("rodei barra 3 ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}