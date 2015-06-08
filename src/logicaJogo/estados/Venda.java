package logicaJogo.estados;

import logicaJogo.Jogo;
import logicaJogo.carga.*;
import logicaJogo.carta.*;

public class Venda extends Estado {

    public Venda(Jogo j) {
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
    public Estado venda(int indice) {
        Carta aux = jogo.getCarta(jogo.getUtilizador().getPosicao());
        if (jogo.getUtilizador().getCarga(indice) instanceof Vazio) {
            return this;
        }
        if (aux instanceof Planeta) {
            int preco = ((Planeta) aux).getPrecoComprar(jogo.getUtilizador().getCarga(indice));
            jogo.getUtilizador().adicionaDinheiro(preco);
            jogo.getBanco().retiraDinheiro(preco);
            jogo.getUtilizador().vendeCarga(indice);
        }
        if (aux instanceof PlanetaPirata && ((PlanetaPirata) aux).getRecursoComprar().getClass()
                .equals(jogo.getUtilizador().getCarga(indice).getClass())) {
            jogo.getUtilizador().adicionaDinheiro(3);
            jogo.getBanco().retiraDinheiro(3);
            jogo.getUtilizador().vendeCarga(indice);
        }
        return this;
    }

    @Override
    public Estado finalVenda() {
        return new Compra(getJogo());
    }

}
