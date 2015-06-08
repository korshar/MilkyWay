package logicaJogo.estados;

import java.io.Serializable;

import logicaJogo.Jogo;

public class Estado implements Serializable {

    protected Jogo jogo;

    public Estado(Jogo jogo) {
        this.jogo = jogo;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public Estado iniciaJogo() {
        return this;
    }

    public Jogo carregaJogo(String nomeFicheiro){ 
    	return jogo;
    }
    
    public Estado upgradeArmas() {
        return this;
    }

    public Estado upgradeCarga() {
        return this;
    }

    public Estado venda(int indice) {
        return this;
    }

    public Estado finalVenda() {
        return this;
    }

    public Estado compra(int indice) {
        return this;
    }

    public Estado finalCompra() {
        return this;
    }

    public Estado movimento(int id) {
        return this;
    }

    public Estado escolheWormHole(int id) {
        return this;
    }

    public Estado finalizarTurno() {
        return this;
    }

}
