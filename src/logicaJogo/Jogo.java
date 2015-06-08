package logicaJogo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Random;

import logicaJogo.carga.*;
import logicaJogo.carta.*;
import logicaJogo.estados.*;

public class Jogo extends Observable implements Serializable {
	
    private ArrayList<Carta> tabuleiro;
    private ArrayList<String> acontecimentos;
    private ArrayList<String> historicoAcontecimentos;
    private Banco banco;
    private Utilizador utilizador;
    private Estado estado;
    private int matriz[][];
    private int movimento;

    public Jogo() {
        setEstado(new InicioJogo(this));
    }

    public void iniciaJogo() {
        setEstado(estado.iniciaJogo());
    }

    public void upgradeArmas() {
        setEstado(estado.upgradeArmas());
    }

    public void upgradeCarga() {
        setEstado(estado.upgradeCarga());
    }

    public void venda(int indice) {
        setEstado(estado.venda(indice));
    }

    public void finalVenda() {
        setEstado(estado.finalVenda());
    }

    public void compra(int indice) {
        setEstado(estado.compra(indice));
    }

    public void finalCompra() {
        setEstado(estado.finalCompra());
    }

    public void movimento(int id) {
        setEstado(estado.movimento(id));
    }

    public void gravaJogo(String nome) {
            ObjectOutputStream oout = null;
            FileOutputStream fout = null;
            try {
                fout = new FileOutputStream(nome + ".bin");
                oout = new ObjectOutputStream(fout);
                oout.writeObject(this);
                oout.flush();
                oout.close();
                fout.close();
            } catch (IOException e) {
            	System.out.println("Erro ao gravar o jogo!!!");
                return;
            }
    }

    public void escolheWormHole(int id) {
        setEstado(estado.escolheWormHole(id));
    }

    public void finalizarTurno() {
        setEstado(estado.finalizarTurno());
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
        setChanged();
        notifyObservers();
    }

    public void inicializaJogo() {
        int matrizTmp[][] = {{0, 0, 0, 0, 0, 0, 0, 0, 25},
        {0, 0, 0, 11, 0, 0, 0, 0, 24},
        {0, 0, 8, 10, 14, 0, 20, 22, 23},
        {0, 5, 7, 9, 13, 17, 19, 21, 0},
        {3, 4, 6, 0, 12, 16, 18, 0, 0},
        {2, 0, 0, 0, 0, 15, 0, 0, 0}, {1, 0, 0, 0, 0, 0, 0, 0, 0}};

        matriz = matrizTmp;
        banco = new Banco();
        utilizador = new Utilizador();
        movimento = 0;
        acontecimentos = new ArrayList<String>();
        historicoAcontecimentos = new ArrayList<String>();
        tabuleiro = arrayListCarta();
        long seed = System.nanoTime();
        Collections.shuffle(tabuleiro, new Random(seed));
        tabuleiro.add(0, new Wormhole(true));
        tabuleiro.add(24, new Wormhole(true));
    }

    private ArrayList<Carta> arrayListCarta() {
        ArrayList<Carta> cartaTmp = new ArrayList<Carta>();
        cartaTmp.add(new Planeta("Gethen", new int[]{1, 3, 2, 3}, false));
        cartaTmp.add(new Planeta("Kiber", new int[]{3, 1, 2, 3}, false));
        cartaTmp.add(new Planeta("Reverie", new int[]{1, 2, 3, 3}, false));
        cartaTmp.add(new Planeta("Tiamat", new int[]{3, 2, 1, 3}, false));
        cartaTmp.add(new Planeta("Lamarckia", new int[]{2, 3, 1, 3}, false));
        cartaTmp.add(new Planeta("Arrakis", new int[]{2, 1, 3, 3}, false));
        cartaTmp.add(new PlanetaPirata("Whirl", new Amarelo(), false));
        cartaTmp.add(new PlanetaPirata("Striterax", new Vermelho(), false));
        cartaTmp.add(new PlanetaPirata("Asperta", new Azul(), false));
        cartaTmp.add(new Wormhole(false));
        cartaTmp.add(new Wormhole(false));
        for (int i = 0; i < 12; i++) {
            cartaTmp.add(new Espaco(false));
        }
        return cartaTmp;
    }

    public void explora() {
        int posXAtual = 0, posYAtual = 0, posXDestino = 0, posYDestino = 0;
        for (int j = 0; j < 7; j++) {
            for (int k = 0; k < 9; k++) {
                if (matriz[j][k] == utilizador.getPosicao()+1) {
                    posXAtual = k;
                    posYAtual = j;
                }
            }
        }
        for (int i = 1; i <= 25; i++) {
            for (int j = 0; j < 7; j++) {
                for (int k = 0; k < 9; k++) {
                    if (matriz[j][k] == i) {
                        posXDestino = k;
                        posYDestino = j;
                        if (posXAtual - 1 <= posXDestino
                                && posXDestino <= posXAtual + 1
                                && posYAtual - 1 <= posYDestino
                                && posYDestino <= posYAtual + 1
                                && !getCarta(i - 1).visivel()) {
                            getCarta(i - 1).setVisibilidade(true);
                            if(getCarta(i - 1) instanceof Planeta)
                            {
                            	for(int l = 0; l <2 ; l++)
                            	{
                            		int m = (int) (Math.random() * 3) + 3;
                            		adicionaRecurso(i-1, l ,m);
                            	}
                            }
                            if(getCarta(i - 1) instanceof PlanetaPirata)
                            {
                            	((PlanetaPirata) getCarta(i - 1)).setRecursoVender(new Preto());
                            }
                        }
                    }
                }
            }
        }
    }

    public void repoeRecurso() {
        int k = 0, l = 0;
        for (int i = 24; i >= 0; i--) {
            if (getCarta(i) instanceof PlanetaPirata
                    && ((PlanetaPirata) getCarta(i)).getRecursoVender() instanceof Vazio
                    && getCarta(i).visivel()) {
                ((PlanetaPirata) getCarta(i)).setRecursoVender(new Preto());
            }
            if (getCarta(i) instanceof Planeta && getCarta(i).visivel()) {
                for (int j = 0; j < 2; j++) {
                    if (((Planeta) getCarta(i)).getRecursoVender(j) instanceof Branco) {
                        ((Planeta) getCarta(i))
                                .setRecursoVender(j, new Vazio());
                    }
                }
            }
        }
        for (int i = 24; i >= 0; i--) {
            if (getCarta(i) instanceof Planeta && getCarta(i).visivel()) {
                for (int j = 0; j < 2; j++) {
                    if (((Planeta) getCarta(i)).getRecursoVender(j) instanceof Vazio) {
                        while (true) {
                            k = (int) (Math.random() * 6) + 1;
                            if (k == 6) 
                            {
                                l = (int) (Math.random() * 6) + 1;
                                if (l > utilizador.getArma()) {
                                	if(l - utilizador.getArma() > utilizador.getDinheiro() )
                                	{
                                		acontecimentos.add(String.format("Foi atacado por piratas perdeu %d moedas!", utilizador.getDinheiro()));
                                		utilizador.retiraDinheiro(utilizador.getDinheiro());
                                	}
	                                else
	                                {
	                                    acontecimentos.add(String.format("Foi atacado por piratas perdeu %d moedas!", (l - utilizador.getArma())));
	                                    utilizador.retiraDinheiro(l - utilizador.getArma());
	                                }
                                }
                                else
                                	acontecimentos.add(String.format("Foi atacado por piratas mas ganhou a batalha!"));
                            } 
                            else 
                            {
                                break;
                            }
                        }
                        adicionaRecurso(i, j, k);
                    }
                }
            }
        }
    }
    
    private void adicionaRecurso(int carta,int posicao,int nmrRecurso)
    {
    	switch (nmrRecurso) 
    	{
    		case 1:
    			((Planeta) getCarta(carta)).setRecursoVender(posicao, new Branco());
    			for (int m = 0; m < utilizador.getNumeroCargas(); m++)
    			{
    				if (utilizador.getCarga(m) instanceof Preto) 
    				{
    					utilizador.vendeCarga(m);
    					utilizador.retiraDinheiro(4);
    					acontecimentos.add("Foi confiscado a sua carga ilegal perdeu 4 moedas!");
    				}
    			}
        	            break;
    		case 2:
    			((Planeta) getCarta(carta)).setRecursoVender(posicao, new Branco());
    			for (int m = 0; m < utilizador.getNumeroCargas(); m++) 
    			{
    				if (utilizador.getCarga(m) instanceof Preto) 
    				{
    					utilizador.vendeCarga(m);
    					utilizador.retiraDinheiro(4);
    					acontecimentos.add("Foi confiscado a sua carga ilegal perdeu 4 moedas!");
                                    }
                    	}
    			break;
    		case 3:
    			((Planeta) getCarta(carta)).setRecursoVender(posicao, new Amarelo());
    			break;
    		case 4:
    			((Planeta) getCarta(carta)).setRecursoVender(posicao, new Azul());
    			break;
    		case 5:
    			((Planeta) getCarta(carta)).setRecursoVender(posicao, new Vermelho());
    			break;
    	}
    }

    public void limparAcontecimento() {
    	for(int i = 0; i < acontecimentos.size(); i++)
    		historicoAcontecimentos.add(acontecimentos.get(i));
        acontecimentos.clear();
    }

    public void addAcontecimento(String acontecimento) {
        this.acontecimentos.add(acontecimento);
    }

    public ArrayList<String> getAcontecimento() {
        return acontecimentos;
    }
    
    public ArrayList<String> getHistoricoAcontecimento() {
        return historicoAcontecimentos;
    }

    public int[][] getMatriz() {
        return matriz;
    }

    public Estado getEstado() {
        return estado;
    }

    public Utilizador getUtilizador() {
        return this.utilizador;
    }

    public Banco getBanco() {
        return this.banco;
    }

    public int getMovimento() {
        return movimento;
    }

    public void setMovimento(int movimento) {
        this.movimento = movimento;
    }

    public Carta getCarta(int indice) {
        return this.tabuleiro.get(indice);
    }
}
