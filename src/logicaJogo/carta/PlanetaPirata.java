package logicaJogo.carta;

import logicaJogo.carga.*;

public class PlanetaPirata extends Carta {

    int preco = 3;
    Carga carga;
    Carga cargaVender;

    public PlanetaPirata(String nome, Carga carga, Boolean visibilidade) {
        this.visibilidade = visibilidade;
        this.carga = carga;
        this.nome = nome;
        cargaVender = new Vazio();
    }

    public Carga getRecursoComprar() {
        return carga;
    }

    public void vendeRecurso() {
        cargaVender = new Vazio();
    }

    public Carga getRecursoVender() {
        return cargaVender;
    }

    public void setRecursoVender(Carga cargaVender) {
        this.cargaVender = cargaVender;
    }
}
