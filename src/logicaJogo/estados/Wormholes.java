package logicaJogo.estados;

import logicaJogo.Jogo;
import logicaJogo.carta.Wormhole;

public class Wormholes extends Estado {

    public Wormholes(Jogo j) {
        super(j);
    }

    @Override
    public Estado escolheWormHole(int id) {

        if (jogo.getCarta(id) instanceof Wormhole && jogo.getCarta(id).visivel() && id != jogo.getUtilizador().getPosicao()) {
            jogo.getUtilizador().setPosicao(id);
            return new Mover(getJogo());
        }
        return this;

    }

}
