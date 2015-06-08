package logicaJogo.estados;

import logicaJogo.Jogo;
import logicaJogo.carga.*;
import logicaJogo.carta.*;

public class Compra extends Estado {

    public Compra(Jogo j) {
        super(j);
    }

    @Override
    public Estado upgradeArmas() {
        int nivel = jogo.getUtilizador().getArma();

        if (nivel < 5) {
            if ((jogo.getUtilizador().getDinheiro()) >= (nivel + 1)) {
                jogo.getUtilizador().retiraDinheiro((nivel + 1));
                jogo.getBanco().adicionaDinheiro((nivel + 1));
                jogo.getUtilizador().incrementaArma();
            }
        }
        return this;
    }

    @Override
    public Estado upgradeCarga() {
        if (jogo.getUtilizador().getNumeroCargas() < 3) {
            jogo.getUtilizador().retiraDinheiro(3);
            jogo.getBanco().adicionaDinheiro(3);
            jogo.getUtilizador().adicionaCarga();
        }

        return this;
    }
    
    
    @Override
    public Estado compra(int indice) {
        if (jogo.getUtilizador().getNumeroCargasLivres() != 0) {
            Carta carta = jogo.getCarta(jogo.getUtilizador().getPosicao());
            if (carta instanceof Planeta) {
                if (jogo.getUtilizador().getDinheiro()
                        - ((Planeta) carta).getPrecoVender(indice) < 0) {
                    return this;
                } else {
                    jogo.getUtilizador().retiraDinheiro(
                            ((Planeta) carta).getPrecoVender(indice));
                    jogo.getBanco().adicionaDinheiro(
                            ((Planeta) carta).getPrecoVender(indice));
                    jogo.getUtilizador().compraCarga(
                            ((Planeta) carta).getRecursoVender(indice));
                    ((Planeta) carta).vendeRecurso(indice);
                }
            }
            if (carta instanceof PlanetaPirata) {
                if (jogo.getUtilizador().getDinheiro() - 1 < 0
                        || ((PlanetaPirata) carta).getRecursoVender() instanceof Vazio) {
                    return this;
                } else {
                    jogo.getUtilizador().retiraDinheiro(1);
                    jogo.getBanco().adicionaDinheiro(1);
                    jogo.getUtilizador().compraCarga(
                            ((PlanetaPirata) carta).getRecursoVender());
                    ((PlanetaPirata) carta).vendeRecurso();
                }
            }
        }
        return this;
    }

    @Override
    public Estado finalCompra() {
        return new Mover(getJogo());
    }
}
