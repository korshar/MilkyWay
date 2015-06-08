package logicaJogo.carta;

import java.io.Serializable;

public class Carta implements Serializable {

    boolean visibilidade = false;
    int posicao;
    String nome;
    boolean token = false;

    public boolean visivel() {
        return visibilidade;
    }

    public void setVisibilidade(boolean visibilidade) {
        this.visibilidade = visibilidade;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }
    
    public String toString()
    {
        return nome;
    }
}
