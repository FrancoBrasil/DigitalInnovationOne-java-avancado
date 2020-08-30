package interfacesFuncionais;

import java.util.function.Supplier;

public class Suppliers {
    public static void main(String[] args) {
        Supplier<Pessoa> suppliers = () -> new Pessoa();
        System.out.println(suppliers.get());
    }
}

class Pessoa {
    private String nome;
    private Integer idade;

    public Pessoa() {
        nome = "João";
        idade = 23;
    }

    @Override
    public String toString() {
        return nome + " " + idade;
    }
}