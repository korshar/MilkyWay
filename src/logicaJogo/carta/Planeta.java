package logicaJogo.carta;

import java.util.ArrayList;

import logicaJogo.Jogo;
import logicaJogo.carga.*;

public class Planeta extends Carta {

    int[] preco;
    ArrayList<Carga> recurso;
    ArrayList<Carga> recursoVender;

    public Planeta(String nome, int[] preco, Boolean visibilidade) {
        recurso = new ArrayList<Carga>();
        recursoVender = new ArrayList<Carga>();
        recursoVender.add(new Vazio());
        recursoVender.add(new Vazio());
        this.visibilidade = visibilidade;
        recurso.add(new Azul());
        recurso.add(new Amarelo());
        recurso.add(new Vermelho());
        recurso.add(new Preto());
        this.nome = nome;
        this.preco = preco;
    }

    public Carga getRecursoVender(int i) {
        return recursoVender.get(i);
    }

    public Carga setRecursoVender(int i, Carga carga) {
        return recursoVender.set(i, carga);
    }

    public void vendeRecurso(int i) {
        recursoVender.set(i, new Vazio());
    }

    public int getPrecoVender(int i) {
        for (int j = 0; j < recurso.size(); j++) {
            if (recurso.get(j).getClass().equals(recursoVender.get(i).getClass())) {
                return preco[j];
            }
        }
        return 31;
    }

    public int getPrecoComprar(Carga carga) {
        int precoTotal = 0;

        for (int i = 0; i < recurso.size(); i++) {
            if (recurso.get(i).getClass().equals(carga.getClass())) {
                precoTotal = preco[i];
            }
        }
        for (int i = 0; i < recursoVender.size(); i++) {
            if (!recursoVender.get(i).getClass().equals(carga.getClass()) && !(recursoVender.get(i) instanceof Vazio)) {
                precoTotal++;
            }
        }

        return precoTotal;
    }
}
