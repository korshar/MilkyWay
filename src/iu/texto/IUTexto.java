package iu.texto;

import java.util.Scanner;

import logicaJogo.*;
import logicaJogo.estados.*;
import logicaJogo.carga.*;
import logicaJogo.carta.*;

public class IUTexto {

    private Jogo jogo;
    private Scanner sc = new Scanner(System.in);
    private int comando;
    Estado estado;
    private String nomeJogo = new String();

    IUTexto() {
        this.jogo = new Jogo();
    }

    public void iniciar() {
        while (true) {

            estado = jogo.getEstado();

            if (estado instanceof InicioJogo) {
                System.out
                        .println("1 - Iniciar Novo Jogo\n2 - Carregar Jogo\n3 - Sair");
                comando = sc.nextInt();

                switch (comando) {
                    case 1:
                        jogo.iniciaJogo();
                        imprimeTabuleiro();
                        break;
                    case 2:
                        System.out.println("Introduza o nome do jogo: ");
                        nomeJogo = sc.next();
                        jogo = jogo.getEstado().carregaJogo(nomeJogo + ".bin");
                        imprimeTabuleiro();
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Opcao Invalida");
                }
            }

            if (estado instanceof Venda) {
            	Carta c = jogo.getCarta(jogo.getUtilizador().getPosicao());
                if (c instanceof Planeta || c instanceof PlanetaPirata) {

                    System.out
                            .println("1 - Vender Carga\n2 - Upgrade Nave\n3 - Finalizar Vendas\n4 - Guardar Jogo\n5 - Sair");
                    comando = sc.nextInt();
                    int i;

                    switch (comando) {
                        case 1:
                            if (jogo.getUtilizador().getNumeroCargas() == 2) {
                                System.out
                                        .println("Indique o indice da carga (1/2)");
                                i = sc.nextInt();
                                if (i >= 1 && i <= 2) {
                                    jogo.venda(i-1);
                                } else {
                                    break;
                                }
                            } else {
                                System.out
                                        .println("Indique o indice da carga (1/2/3)");
                                i = sc.nextInt();
                                if (i >= 1 && i <= 3) {
                                    jogo.venda(i-1);
                                } else {
                                    break;
                                }
                            }
                            imprimeTabuleiro();
                            break;
                        case 2:
                            System.out
                                    .println("1 - Upgrade da Arma\n2 - Upgrade do Cargo\n3 - Voltar");
                            i = sc.nextInt();
                            switch (i) {
                                case 1:
                                    jogo.upgradeArmas();
                                    break;
                                case 2:
                                    jogo.upgradeCarga();
                                    break;
                                case 3:
                                    break;
                                default:
                                    System.out.println("Opcao Invalida");
                            }
                            imprimeTabuleiro();
                            break;
                        case 3:
                            jogo.finalVenda();
                            imprimeTabuleiro();
                            break;
                        case 4:
                            System.out.println("Introduza o nome do jogo: ");
                            nomeJogo = sc.next();
                            jogo.gravaJogo(nomeJogo);
                            break;
                        case 5:
                            return;
                        default:
                            System.out.println("Opcao Invalida");
                    }
                } else {
                    jogo.finalVenda();
                }
            }

            if (estado instanceof Compra) {

            	Carta c = jogo.getCarta(jogo.getUtilizador().getPosicao());
                if (c instanceof Planeta || c instanceof PlanetaPirata) {

                    System.out
                            .println("1 - Comprar Carga\n2 - Upgrade Nave\n3 - Finalizar Compra\n4 - Guardar Jogo\n5 - Sair");
                    comando = sc.nextInt();
                    int i;

                    switch (comando) {
                        case 1:
                            if (c instanceof Planeta) {
                                System.out
                                        .println("Indique o indice da carga (1/2)\n3 - Voltar");
                                i = sc.nextInt();
                                if (i >= 1 && i <= 2) {
                                    jogo.compra(i-1);
                                }
                                if (i == 3) {
                                    break;
                                }
                            } else {
                                System.out
                                        .println("1 - Comprar carga\n 2 - Voltar");
                                i = sc.nextInt();
                                if (i == 1) {
                                    jogo.compra(i-1);
                                } else {
                                    break;
                                }
                            }
                            imprimeTabuleiro();
                            break;
                            
                        case 2:
                        	System.out
                            .println("1 - Upgrade da Arma\n2 - Upgrade do Cargo\n3 - Voltar");
			                i = sc.nextInt();
			                switch (i) {
			                    case 1:
			                        jogo.upgradeArmas();
			                        break;
			                    case 2:
			                        jogo.upgradeCarga();
			                        break;
			                    case 3:
			                        break;
			                    default:
			                        System.out.println("Opcao Invalida");
			                }
			                imprimeTabuleiro();
			                break;
                        case 3:
                            jogo.finalCompra();
                            imprimeTabuleiro();
                            break;
                        case 4:
                            System.out.println("Introduza o nome do jogo: ");
                            nomeJogo = sc.next();
                            jogo.gravaJogo(nomeJogo);
                            break;
                        case 5:
                            return;
                        default:
                            System.out.println("Opcao Invalida");
                    }
                } else {
                    jogo.finalCompra();
                }
            }

            if (estado instanceof Mover) {
                System.out.println("1 - Move\n2 - Finalizar Turno\n3 - Guardar Jogo\n4 Sair");
                comando = sc.nextInt();
                int i;

                switch (comando) {
                    case 1:
                        System.out.println("Introduza a posicao do destino!");
                        i = sc.nextInt();
                        if (i < 1 || i > 25) {
                            break;
                        }
                        jogo.movimento(i-1);
                        imprimeTabuleiro();
                        for (int j = 0; j < jogo.getAcontecimento().size(); j++) {
                            System.out.println(jogo.getAcontecimento().get(j));
                        }
                        jogo.limparAcontecimento();
                        break;
                    case 2:
                        jogo.finalizarTurno();
                        imprimeTabuleiro();
                        for (int j = 0; j < jogo.getAcontecimento().size(); j++) {
                            System.out.println(jogo.getAcontecimento().get(j));
                        }
                        jogo.limparAcontecimento();
                        break;
                    case 3:
                        System.out.println("Introduza o nome do jogo: ");
                        nomeJogo = sc.next();
                        jogo.gravaJogo(nomeJogo);
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Opcao Invalida");
                }
            }

            if (estado instanceof Wormholes) {
                System.out.println("Introduza a posicao do Wormhole destino!");
                comando = sc.nextInt();
                if (0 < comando && comando < 26) {
                    jogo.escolheWormHole(comando);
                    imprimeTabuleiro();
                }
            }
        }
    }

    private void imprimeTabuleiro() {
        int matriz[][] = jogo.getMatriz();
        System.out.println("");

        System.out.println("P-Planeta\nPP-Planeta Pirata\n"
                + "W-Wormhole\nE-Espaco\nD-Desconhecido\nN-Nave");

        System.out.println("");
        System.out.println("Recurso: (Azul/Amarelo/Vermelho/Preto)");

        System.out.println("");
        System.out.println("Planetas Descobertos:");
        for (int i = 24; i >= 0; i--) {
            if (jogo.getCarta(i) instanceof Planeta
                    && jogo.getCarta(i).visivel()) {
                Planeta p = ((Planeta) jogo.getCarta(i));
                switch (p.toString()) {
                    case "Gethen":
                        System.out.println(p.getPosicao() + " - Gethen(1/3/2/3)"
                                + " - " + p.getRecursoVender(0).toString() + " "
                                + p.getRecursoVender(1).toString());
                        break;
                    case "Kiber":
                        System.out.println(p.getPosicao() + " - Kiber(3/1/2/3)"
                                + " - " + p.getRecursoVender(0).toString() + " "
                                + p.getRecursoVender(1).toString());
                        break;
                    case "Reverie":
                        System.out.println(p.getPosicao() + " - Reverie(1/2/3/3)"
                                + " - " + p.getRecursoVender(0).toString() + " "
                                + p.getRecursoVender(1).toString());
                        break;
                    case "Tiamat":
                        System.out.println(p.getPosicao() + " - Tiamat(3/2/1/3)"
                                + " - " + p.getRecursoVender(0).toString() + " "
                                + p.getRecursoVender(1).toString());
                        break;
                    case "Lamarckia":
                        System.out.println(p.getPosicao() + " - Lamarckia(2/3/1/3)"
                                + " - " + p.getRecursoVender(0).toString() + " "
                                + p.getRecursoVender(1).toString());
                        break;
                    case "Arrakis":
                        System.out.println(p.getPosicao() + " - Arrakis(2/1/3/3)"
                                + " - " + p.getRecursoVender(0).toString() + " "
                                + p.getRecursoVender(1).toString());
                        break;
                }
            }
        }

        System.out.println("");
        System.out.println("Planetas Piratas Descobertos:");
        for (int i = 24; i >= 0; i--) {
            if (jogo.getCarta(i) instanceof PlanetaPirata
                    && jogo.getCarta(i).visivel()) {
                PlanetaPirata pp = ((PlanetaPirata) jogo.getCarta(i));
                switch (pp.toString()) {
                    case "Whirl":
                        System.out.println(pp.getPosicao() + " - Whirl(-/3/-/-)"
                                + " - " + pp.getRecursoVender().toString());
                        break;
                    case "Striterax":
                        System.out.println(pp.getPosicao()
                                + " - Striterax(-/-/3/-)" + " - "
                                + pp.getRecursoVender().toString());
                        break;
                    case "Asperta":
                        System.out.println(pp.getPosicao() + " - Asperta(3/-/-/-)"
                                + " - " + pp.getRecursoVender().toString());
                        break;
                }
            }
        }

        System.out.println("");
        Utilizador u = jogo.getUtilizador();
        if (u.getNumeroCargas() == 2) {
            System.out.println("Nave: Moedas: " + u.getDinheiro() + " Arma: "
                    + u.getArma() + " Cargo: " + u.getCarga(0).toString() + " "
                    + u.getCarga(1).toString());
        } else {
            System.out.println("Nave: Moedas: " + u.getDinheiro() + " Arma: "
                    + u.getArma() + " Cargo: " + u.getCarga(0).toString() + " "
                    + u.getCarga(1).toString() + " " + u.getCarga(2).toString());
        }

        System.out.println("");
        System.out.println("Mapa:");
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 9; j++) {
                if (matriz[i][j] == 0) {
                    System.out.print("        ");
                } else {
                    if (jogo.getCarta(matriz[i][j] - 1).visivel()) {
                        if (jogo.getUtilizador().getPosicao() +1 != matriz[i][j]) {
                            if (jogo.getCarta(matriz[i][j] - 1) instanceof Espaco) {
                                System.out.print(String.format("%02d",
                                        matriz[i][j]) + "-E    ");
                            }
                            if (jogo.getCarta(matriz[i][j] - 1) instanceof Planeta) {
                                System.out.print(String.format("%02d",
                                        matriz[i][j]) + "-P    ");
                            }
                            if (jogo.getCarta(matriz[i][j] - 1) instanceof PlanetaPirata) {
                                System.out.print(String.format("%02d",
                                        matriz[i][j]) + "-PP   ");
                            }
                            if (jogo.getCarta(matriz[i][j] - 1) instanceof Wormhole) {
                                System.out.print(String.format("%02d",
                                        matriz[i][j]) + "-W    ");
                            }
                        } else {
                            if (jogo.getCarta(matriz[i][j] - 1) instanceof Espaco) {
                                System.out.print(String.format("%02d",
                                        matriz[i][j]) + "-E  N ");
                            }
                            if (jogo.getCarta(matriz[i][j] - 1) instanceof Planeta) {
                                System.out.print(String.format("%02d",
                                        matriz[i][j]) + "-P  N ");
                            }
                            if (jogo.getCarta(matriz[i][j] - 1) instanceof PlanetaPirata) {
                                System.out.print(String.format("%02d",
                                        matriz[i][j]) + "-PP N ");
                            }
                            if (jogo.getCarta(matriz[i][j] - 1) instanceof Wormhole) {
                                System.out.print(String.format("%02d",
                                        matriz[i][j]) + "-W  N ");
                            }
                        }
                    } else {
                        System.out.print(String.format("%02d", matriz[i][j])
                                + "-D    ");
                    }
                }
            }
            System.out.println("");
        }
        System.out.println("");
    }

}
