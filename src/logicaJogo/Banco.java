package logicaJogo;

import java.io.Serializable;

public class Banco implements Serializable {

    private int dinheiro;

    public void adicionaDinheiro(int dinheiro) {
        this.dinheiro += dinheiro;
    }

    public void retiraDinheiro(int dinheiro) {
        this.dinheiro -= dinheiro;
    }
}
