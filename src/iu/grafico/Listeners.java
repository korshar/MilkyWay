package iu.grafico;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import logicaJogo.Jogo;
import logicaJogo.carta.Carta;
import logicaJogo.carta.Planeta;
import logicaJogo.carta.PlanetaPirata;
import logicaJogo.estados.Mover;
import logicaJogo.estados.Wormholes;


public class Listeners {
    private UIGrafico uiGrafico;
    
    public Listeners (UIGrafico uiGrafico)
    {
    	this.uiGrafico = uiGrafico;
    }
    
//Listeners do Menu Inicial
    class ListenerMiBtnInciarJogo implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {            
        	uiGrafico.jPanel = new JPanel();
        	uiGrafico.setContentPane(uiGrafico.jPanel);
        	uiGrafico.jogo = new Jogo();
        	uiGrafico.registarObservers();
        	uiGrafico.inicializaPaginaJogo();
        	uiGrafico.preencheTabuleiro();
        	uiGrafico.registarListenersJ();
        	uiGrafico.jogo.iniciaJogo();
        	uiGrafico.actualizaCartas();
        	uiGrafico.pintaRecursosVenda();
        }
    }

    class ListenerMiBtnCarregarJogo implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {            
        	JFileChooser fileChooser = new JFileChooser();
        	fileChooser.setDialogTitle("Carregar Jogo");
        	fileChooser.setFileFilter(new FileNameExtensionFilter(".bin", new String("bin")));
        	fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") +"/Desktop"));
        	if (fileChooser.showOpenDialog(uiGrafico) == JFileChooser.APPROVE_OPTION) {
        	  File file = fileChooser.getSelectedFile();
        	  uiGrafico.jPanel = new JPanel();
        	  uiGrafico.setContentPane(uiGrafico.jPanel);
	          uiGrafico.jogo = uiGrafico.jogo.getEstado().carregaJogo(file.getPath());
	          uiGrafico.carregaJogo();
	          uiGrafico.registarObservers();
        	}
        }
    }
    class ListenerMiBtnSair implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {           
        	uiGrafico.dispose();
            System.exit(0);
        }
    }
	
//Listeners da Pagina do jogo	
    class ListenerJBtnCarta implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {
        	if(uiGrafico.estado instanceof Mover)
        	{
        		for(int i = 0; i < 25; i++)
        			if(e.getSource() == uiGrafico.jTabuleiro.get(i)){
        				uiGrafico.jogo.movimento(i);
        			}
            	for(int i = 0; i < uiGrafico.jogo.getAcontecimento().size(); i++)
            		uiGrafico.jTaAcontecimentos.append(uiGrafico.jogo.getAcontecimento().get(i)+"\n");
            	uiGrafico.jogo.limparAcontecimento();
        	}
        	if(uiGrafico.estado instanceof Wormholes)
        	{
        		for(int i = 0; i < 25; i++)
        			if(e.getSource() == uiGrafico.jTabuleiro.get(i))
        				uiGrafico.jogo.escolheWormHole(i);
        	}
        }
    }
    class ListenerJBtnSave implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {         
        	JFileChooser fileChooser = new JFileChooser();
        	fileChooser.setDialogTitle("Gravar Jogo");
        	fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") +"/Desktop"));
        	if (fileChooser.showSaveDialog(uiGrafico) == JFileChooser.APPROVE_OPTION) {
        	  File file = fileChooser.getSelectedFile();
        	  uiGrafico.jogo.gravaJogo(file.getPath());
        	}
        }
    }
    class ListenerJBtnSair implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {            
        	uiGrafico.setContentPane(uiGrafico.miPanel);
        	uiGrafico.jGlass.setVisible(false);
        }
    }

//Carta 1 - Vender
    class ListenerJBtnVenderCarga implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {
        	if(e.getSource() == uiGrafico.jBtnVenderCarga1)
        		uiGrafico.jogo.venda(0);
        	if(e.getSource() == uiGrafico.jBtnVenderCarga2)
        		uiGrafico.jogo.venda(1);
        	if(e.getSource() == uiGrafico.jBtnVenderCarga3)
        		uiGrafico.jogo.venda(2);
        }
    }
    class ListenerJBtnUpgradeArma implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {
        	int nivel = uiGrafico.jogo.getUtilizador().getArma();
        	uiGrafico.jogo.upgradeArmas();
        	if (nivel != uiGrafico.jogo.getUtilizador().getArma())
        	{
        		if(uiGrafico.jogo.getUtilizador().getArma() == 4)
        			uiGrafico.jLblNivelArma.setBounds(1016, 114, 21, 21);
    			if(uiGrafico.jogo.getUtilizador().getArma() == 5)
    				uiGrafico.jLblNivelArma.setBounds(1076, 114, 21, 21);
    		}
        }
    }
    class ListenerJBtnUpgradeCargo implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {
        	uiGrafico.jogo.upgradeCarga();
        	if(uiGrafico.jogo.getUtilizador().getNumeroCargas() == 3 && !uiGrafico.jCarga.get(2).getText().equals(""))
        		uiGrafico.jCarga.get(2).setText("");
        	uiGrafico.pintaRecursosVenda();
        }
    }
    class ListenerJBtnVenderFinalizar implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {
        	uiGrafico.jogo.finalVenda();
        }
    }

//Carta 2 - Comprar
    class ListenerJBtnComprarCarga implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {         
        	Carta c = uiGrafico.jogo.getCarta(uiGrafico.jogo.getUtilizador().getPosicao());
        	if(e.getSource() == uiGrafico.jBtnComprarCarga1)
        		uiGrafico.jogo.compra(0);
        	if(e.getSource() == uiGrafico.jBtnComprarCarga2)
        	{
        		if (c instanceof Planeta)
            		uiGrafico.jogo.compra(1);
        		if (c instanceof PlanetaPirata)
            		uiGrafico.jogo.compra(0);
        	}
        }
    }
    class ListenerJBtnComprarFinalizar implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {      
        	uiGrafico.jogo.finalCompra();
        }
    }

//Carta 2 - Mover
    class ListenerJBtnMoverFinalizar implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {            
        	uiGrafico.jogo.finalizarTurno();
        	for(int i = 0; i < uiGrafico.jogo.getAcontecimento().size(); i++)
        		uiGrafico.jTaAcontecimentos.append(uiGrafico.jogo.getAcontecimento().get(i)+"\n");
        	uiGrafico.jogo.limparAcontecimento();
        	uiGrafico.actualizaCartas();
        	uiGrafico.pintaRecursosVenda();
        }
    }
}
