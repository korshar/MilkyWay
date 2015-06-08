package logicaJogo.carga;

import java.io.Serializable;

public abstract class Carga implements Serializable {

    int id;
    String nome;

    public int getId() {
        return this.id;
    }

    public String toString()
    {
        return nome;
    }
}
