package desenvAssincronoParalelo;

public class ThreadExemplo {
    public static void main(String[] args) {
        GeraradorPDF pdf = new GeraradorPDF();
        BarraDeCarregamento barra = new BarraDeCarregamento(pdf);

        pdf.start();
        barra.start();

    }

}

class GeraradorPDF extends Thread {
    @Override
    public void run() {
        try {
            System.out.println("Início de Geração de PDF");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("PDF Gerado");
    }
}

class BarraDeCarregamento extends Thread {
    private Thread pdf;

    public BarraDeCarregamento(Thread pdf) {
        this.pdf = pdf;
    }

    @Override
    public void run() {

        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!pdf.isAlive()) {
                break;
            }
            System.out.println("Loading.....");


        }
    }
}
