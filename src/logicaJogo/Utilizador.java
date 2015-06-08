package logicaJogo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

import logicaJogo.carga.Carga;
import logicaJogo.carga.Vazio;

public class Utilizador implements Serializable {

    ArrayList<Carga> cargas;
    int dinheiro;
    int arma;
    int posicao;

    public Utilizador() {
        cargas = new ArrayList<Carga>();
        cargas.add(new Vazio());
        cargas.add(new Vazio());
        posicao = 0;
        arma = 3;
        dinheiro = 10;
    }

    public int getArma() {
        return this.arma;
    }

    public int getDinheiro() {
        return this.dinheiro;
    }

    public void adicionaDinheiro(int dinheiro) {
        this.dinheiro += dinheiro;
    }

    public void retiraDinheiro(int dinheiro) {
        this.dinheiro -= dinheiro;
        
        if(this.dinheiro < 0) {
    		this.dinheiro = 0;
    	}
    }

    public void incrementaArma() {
        this.arma++;
    }

    public int getNumeroCargas() {
        return this.cargas.size();
    }

    public int getNumeroCargasLivres() {
        int numero = 0;
        for (int i = 0; i < cargas.size(); i++) {
            if (cargas.get(i) instanceof Vazio) {
                numero++;
            }
        }
        return numero;
    }

    public void adicionaCarga() {
        this.cargas.add(new Vazio());
    }

    public void compraCarga(Carga carga) {
        for (int i = 0; i < cargas.size(); i++) {
            if (cargas.get(i) instanceof Vazio) {
                cargas.set(i, carga);
                break;
            }
        }
    }

    public void vendeCarga(int indice) {
        cargas.set(indice, new Vazio());
    }

    public Carga getCarga(int i) {
        return this.cargas.get(i);
    }

    public int getPosicao() {
        return this.posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public boolean verificaDestino(int posicao) {
        int[][] matriz
                = {{0, 0, 0, 0, 0, 0, 0, 0, 25},
                {0, 0, 0, 11, 0, 0, 0, 0, 24},
                {0, 0, 8, 10, 14, 0, 20, 22, 23},
                {0, 5, 7, 9, 13, 17, 19, 21, 0},
                {3, 4, 6, 0, 12, 16, 18, 0, 0},
                {2, 0, 0, 0, 0, 15, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0}};
        int posXAtual = 0, posYAtual = 0, posXDestino = 0, posYDestino = 0;
        if (posicao == this.posicao) {
            return false;
        }
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 9; j++) {
                if (matriz[i][j] == posicao + 1) {
                    posXDestino = j;
                    posYDestino = i;
                }
                if (matriz[i][j] == this.posicao + 1) {
                    posXAtual = j;
                    posYAtual = i;
                }
            }
        }

        if (posXAtual - 1 <= posXDestino && posXDestino <= posXAtual + 1 && posYAtual - 1 <= posYDestino && posYDestino <= posYAtual + 1) {
            return true;
        } else {
            return false;
        }
    }
}
