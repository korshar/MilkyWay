package logicaJogo.estados;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import logicaJogo.Jogo;

public class InicioJogo extends Estado {

    public InicioJogo(Jogo j) {
        super(j);
    }

    @Override
    public Estado iniciaJogo() {
        jogo.inicializaJogo();
        jogo.explora();
        jogo.repoeRecurso();
        return new Venda(getJogo());
    }

    @Override
    public Jogo carregaJogo(String nomeFicheiro){
        ObjectInputStream ois;
			try {
				ois = new ObjectInputStream(new FileInputStream(nomeFicheiro));
				jogo = (Jogo) ois.readObject();
		        ois.close();
			} catch (IOException | ClassNotFoundException e) {
				System.out.println("Erro na abertura do jogo!!!");
			}
	    return jogo;
    }
}
