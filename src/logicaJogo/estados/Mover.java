package logicaJogo.estados;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import logicaJogo.Jogo;
import logicaJogo.carta.*;

public class Mover extends Estado {

    public Mover(Jogo j) {
        super(j);
    }

    @Override
    public Estado movimento(int id) {
        if (jogo.getUtilizador().verificaDestino(id)
                && jogo.getCarta(id).visivel()) {
            if (jogo.getUtilizador().getDinheiro() <= 0 && jogo.getMovimento() == 0) {
                jogo.addAcontecimento("Perdeu o jogo!");
                return new InicioJogo(getJogo());
            }
            
            if (!(jogo.getCarta(jogo.getUtilizador().getPosicao()) instanceof PlanetaPirata) && !(jogo.getCarta(jogo.getUtilizador().getPosicao()) instanceof Planeta) && jogo.getUtilizador().getDinheiro() <= 0) {
                jogo.addAcontecimento("Perdeu o jogo!");
                return new InicioJogo(getJogo());
            }

            jogo.getUtilizador().retiraDinheiro(1);
            jogo.getUtilizador().setPosicao(id);
            jogo.setMovimento(jogo.getMovimento() + 1);

            if (jogo.getCarta(id) instanceof Wormhole) {
                return new Wormholes(getJogo());
            } else {
                return this;
            }
        }
        return this;
    }

    @Override
    public Estado finalizarTurno() {
        if (jogo.getMovimento() != 0) {
            if (jogo.getCarta(jogo.getUtilizador().getPosicao()) instanceof PlanetaPirata) {
                int k = (int) (Math.random() * 6) + 1;
                k += (int) (Math.random() * 6) + 1;
                if (k > jogo.getUtilizador().getArma()) {
                	if(k - jogo.getUtilizador().getArma() > jogo.getUtilizador().getDinheiro() )
                	{
                		jogo.addAcontecimento(String.format("Foi atacado por piratas perdeu %d moedas!", jogo.getUtilizador().getDinheiro()));
                		jogo.getUtilizador().retiraDinheiro(jogo.getUtilizador().getDinheiro());
                	}
                	else
                	{
                		jogo.addAcontecimento(String.format("Foi atacado por piratas perdeu %d moedas!", (k - jogo.getUtilizador().getArma())));
                		jogo.getUtilizador().retiraDinheiro(k - jogo.getUtilizador().getArma());
                	}
                }
                else
                	jogo.addAcontecimento(String.format("Foi atacado por piratas mas ganhou a batalha!"));
            }

            if (!(jogo.getCarta(jogo.getUtilizador().getPosicao()) instanceof PlanetaPirata) && !(jogo.getCarta(jogo.getUtilizador().getPosicao()) instanceof Planeta) && jogo.getUtilizador().getDinheiro() <= 0) {
                jogo.addAcontecimento("Perdeu o jogo!");
                return new InicioJogo(getJogo());
            }
            for (int i = 0; i < 25; i++) {
                if (!jogo.getCarta(i).visivel()) {
                    break;
                }
                if (i == 24 && jogo.getUtilizador().getDinheiro() >= 10) {
                    jogo.addAcontecimento("Parabens venceu o jogo!");
                    return new InicioJogo(getJogo());
                }
                if (i == 24 && jogo.getUtilizador().getDinheiro() < 10) {
                    jogo.addAcontecimento("Perdeu o jogo!");
                    return new InicioJogo(getJogo());
                }

            }
            jogo.setMovimento(0);
            jogo.explora();
            jogo.repoeRecurso();
            return new Venda(getJogo());
        } else {
            return this;
        }
    }

}
