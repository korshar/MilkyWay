package iu.grafico;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import logicaJogo.Jogo;
import logicaJogo.carga.Carga;
import logicaJogo.carta.Carta;
import logicaJogo.carta.Planeta;
import logicaJogo.carta.PlanetaPirata;
import logicaJogo.estados.*;

public class UIGrafico extends JFrame implements Observer {
	private static final long serialVersionUID = 1L;
	final private Listeners listeners = new Listeners(this);
	protected Jogo jogo;
	protected Estado estado;

// Componentes Menu Inicial - mi
	
	protected JPanel miPanel = new JPanel();
	private JButton miBtnInciarJogo = new JButton("Iniciar Jogo");
	private JButton miBtnCarregarJogo = new JButton("Carregar Jogo");
	private JButton miBtnSair = new JButton("Sair");
	private JLabel miLblImagem = new JLabel("");

// Componentes Pagina do jogo - j
	protected JPanel jPanel;
	protected Pintar jGlass;
	protected List<JButton> jTabuleiro;
	protected List<JLabel> jRecurso;
    private JLabel jLblImagemCartaNave;
	protected List<JLabel> jCarga;
    protected JLabel jLblNivelArma;
	private JLabel jLblImagemDinheiro;
    private JLabel jLblImagemToken;
	protected JLabel jLblDinheiro;
	protected JTextArea jTaAcontecimentos;
	private JScrollPane jSpAcontecimentos;
	private JButton jBtnSave;
	private JButton jBtnSair;
	
	//Cartas do CardLayout
	private JPanel jCards;
	protected CardLayout cl = new CardLayout();
	
	//Carta 1 - Vender
	private JPanel jCardVender;
	protected JLabel jLblVender;
	protected JLabel jLblVenderCarga1;
	protected JButton jBtnVenderCarga1;
	protected JLabel jLblVenderCarga2;
	protected JButton jBtnVenderCarga2;
	protected JLabel jLblVenderCarga3;
	protected JButton jBtnVenderCarga3;
	private JLabel jLblVenderUpgrade;
	private JButton jBtnVenderUpgradeArma;
	private JButton jBtnVenderUpgradeCargo;
	private JButton jBtnVenderFinalizar;
	
	//Carta 2 - Comprar
	private JPanel jCardComprar;
	private JLabel jLblComprar;
	protected JLabel jLblComprarCarga1;
	protected JButton jBtnComprarCarga1;
	protected JLabel jLblComprarCarga2;
	protected JButton jBtnComprarCarga2;
	private JLabel jLblComprarUpgrade;
	private JButton jBtnComprarUpgradeArma;
	private JButton jBtnComprarUpgradeCargo;
	private JButton jBtnComprarFinalizar;
	
	//Carta 2 - Mover
	private JPanel jCardMover;
	private JLabel jLblMover;
	private JLabel jLblCliqueNaCartaMover;
	private JButton jBtnMoverFinalizar;
	
	//Carta 2 - Wormhole
	private JPanel jCardWormhole;
	private JLabel jLblWormhole;
	private JLabel jLblCliqueNaCartaWormhole;
	
	public UIGrafico() 
	{
		super("The MilkyWay Express");

		// criar funções de preenchimento
		inicializaMenuIniciar();
		
		registarListenersMI();

		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		validate();
	}
	
	protected void inicializaMenuIniciar() 
	{
		jogo=new Jogo();
		miPanel.setLayout(new BorderLayout());
		miPanel.setBackground(Color.WHITE);

		miBtnInciarJogo.setBounds(510, 289, 349, 50);
		miPanel.add(miBtnInciarJogo);

		miBtnCarregarJogo.setBounds(510, 438, 349, 50);
		miPanel.add(miBtnCarregarJogo);
		
		miBtnSair.setBounds(510, 578, 349, 50);
		miPanel.add(miBtnSair);

		miLblImagem.setBounds(31, 11, 393, 34);
		miPanel.add(miLblImagem);
		
		
		setContentPane(miPanel);
	}
	
	protected void registarListenersMI()
	{
		miBtnInciarJogo.addActionListener(listeners.new ListenerMiBtnInciarJogo());
		miBtnCarregarJogo.addActionListener(listeners.new ListenerMiBtnCarregarJogo());
		miBtnSair.addActionListener(listeners.new ListenerMiBtnSair());
	}
	
	protected void inicializaPaginaJogo() 
	{
		jPanel.setLayout(null);
		
		jGlass = new Pintar(this);
		this.setGlassPane(jGlass);
		jGlass.setVisible(true);
		jGlass.setLayout(null);

		jLblImagemCartaNave = new JLabel("");
		jLblImagemCartaNave.setBounds(981, 53, 150, 150);
		jLblImagemCartaNave.setIcon(new ImageIcon(new ImageIcon("./Imagens/Nave.png").getImage().getScaledInstance(150, 150, 100)));
		jPanel.add(jLblImagemCartaNave);
		
	    jLblNivelArma = new JLabel("");
	    jLblNivelArma.setBounds(1046, 85, 21, 21);
	    jLblNivelArma.setOpaque(true);
	    jLblNivelArma.setBackground(Color.GRAY);
	    jGlass.add(jLblNivelArma);
    	
	    jCarga = new ArrayList<JLabel>();
	    
	    JLabel jLblNivelCarga1 = new JLabel("");
	    jLblNivelCarga1.setBounds(1017, 161, 21, 21);
	    jLblNivelCarga1.setOpaque(true);
	    jLblNivelCarga1.setBackground(Color.LIGHT_GRAY);
	    jCarga.add(jLblNivelCarga1);
	    
    	JLabel jLblNivelCarga2 = new JLabel("");
    	jLblNivelCarga2.setBounds(1045, 161, 21, 21);
    	jLblNivelCarga2.setOpaque(true);
    	jLblNivelCarga2.setBackground(Color.LIGHT_GRAY);
    	jCarga.add(jLblNivelCarga2);
	    
    	JLabel jLblNivelCarga3 = new JLabel("  3");
    	jLblNivelCarga3.setBounds(1073, 161, 21, 21);
    	jLblNivelCarga3.setOpaque(true);
    	jLblNivelCarga3.setBackground(Color.GRAY);
    	jCarga.add(jLblNivelCarga3);
    	
    	for(int i = 0 ; i < jCarga.size(); i++)
    		jGlass.add(jCarga.get(i));
    	
		jLblImagemDinheiro = new JLabel("");
		jLblImagemDinheiro.setBounds(1182, 53, 100, 100);
		jLblImagemDinheiro.setIcon(new ImageIcon(new ImageIcon("./Imagens/Moeda.png").getImage().getScaledInstance(100, 100, 100)));
		jPanel.add(jLblImagemDinheiro);

    	
		jLblImagemToken = new JLabel("");
		jLblImagemToken.setBounds(40, 688, 30, 30);
		jLblImagemToken.setIcon(new ImageIcon(new ImageIcon("./Imagens/Token.png").getImage().getScaledInstance(30, 30, 100)));
    	jGlass.add(jLblImagemToken);

		
		jLblDinheiro = new JLabel("Moedas: 10");
		jLblDinheiro.setBounds(1182, 164, 100, 22);
		jPanel.add(jLblDinheiro);
		
		jTaAcontecimentos = new JTextArea();
		jTaAcontecimentos.setEditable(false);
		jTaAcontecimentos.setLineWrap(true);
		jSpAcontecimentos = new JScrollPane(jTaAcontecimentos);
		jSpAcontecimentos.setBounds(968, 249, 372, 96);
		jPanel.add(jSpAcontecimentos);

		jBtnSave = new JButton("Gravar Jogo");
		jBtnSave.setBounds(968, 582, 163, 66);
		jPanel.add(jBtnSave);

		jBtnSair = new JButton("Sair");
		jBtnSair.setBounds(1177, 582, 163, 66);
		jPanel.add(jBtnSair);

		jCards = new JPanel();
		jCards.setBounds(968, 344, 382, 227);
		jPanel.add(jCards);
		jCards.setLayout(new CardLayout(0, 0));

		jCardVender = new JPanel();
		jCards.add(jCardVender, "Venda");
		jCardVender.setLayout(null);

		jLblVender = new JLabel("Vender");
		jLblVender.setBounds(165, 5, 70, 15);
		jCardVender.add(jLblVender);

		jLblVenderCarga1 = new JLabel("0");
		jLblVenderCarga1.setBounds(63, 48, 15, 15);
		jCardVender.add(jLblVenderCarga1);

		jBtnVenderCarga1 = new JButton("");
		jBtnVenderCarga1.setBounds(77, 48, 15, 15);
		jBtnVenderCarga1.setOpaque(true);
		jBtnVenderCarga1.setBackground(Color.WHITE);
		jCardVender.add(jBtnVenderCarga1);

		jLblVenderCarga2 = new JLabel("0");
		jLblVenderCarga2.setBounds(177, 48, 15, 15);
		jCardVender.add(jLblVenderCarga2);

		jBtnVenderCarga2 = new JButton("");
		jBtnVenderCarga2.setBounds(196, 48, 15, 15);
		jBtnVenderCarga2.setOpaque(true);
		jBtnVenderCarga2.setBackground(Color.WHITE);
		jCardVender.add(jBtnVenderCarga2);

		jLblVenderCarga3 = new JLabel("0");
		jLblVenderCarga3.setBounds(297, 48, 15, 15);
		jCardVender.add(jLblVenderCarga3);

		jBtnVenderCarga3 = new JButton("");
		jBtnVenderCarga3.setBounds(311, 48, 15, 15);
		jBtnVenderCarga3.setOpaque(true);
		jBtnVenderCarga3.setBackground(Color.WHITE);
		jCardVender.add(jBtnVenderCarga3);

		jLblVenderUpgrade = new JLabel("Upgrade");
		jLblVenderUpgrade.setBounds(165, 93, 70, 15);
		jCardVender.add(jLblVenderUpgrade);

		jBtnVenderUpgradeArma = new JButton("Melhorar Arma");
		jBtnVenderUpgradeArma.setBounds(10, 132, 175, 25);
		jCardVender.add(jBtnVenderUpgradeArma);

		jBtnVenderUpgradeCargo = new JButton("Aumentar Capacidade");
		jBtnVenderUpgradeCargo.setBounds(196, 132, 175, 25);
		jCardVender.add(jBtnVenderUpgradeCargo);

		jBtnVenderFinalizar = new JButton("Finalizar fase de venda");
		jBtnVenderFinalizar.setBounds(106, 168, 175, 25);
		jCardVender.add(jBtnVenderFinalizar);

		jCardComprar = new JPanel();
		jCards.add(jCardComprar, "Compra");
		jCardComprar.setLayout(null);

		jLblComprar = new JLabel("Compra");
		jLblComprar.setBounds(165, 5, 70, 15);
		jCardComprar.add(jLblComprar);

		jLblComprarCarga1 = new JLabel("0");
		jLblComprarCarga1.setBounds(63, 48, 15, 15);
		jCardComprar.add(jLblComprarCarga1);

		jBtnComprarCarga1 = new JButton("");
		jBtnComprarCarga1.setOpaque(true);
		jBtnComprarCarga1.setBackground(Color.WHITE);
		jBtnComprarCarga1.setBounds(77, 48, 15, 15);
		jCardComprar.add(jBtnComprarCarga1);

		jLblComprarCarga2 = new JLabel("0");
		jLblComprarCarga2.setBounds(177, 48, 15, 15);
		jCardComprar.add(jLblComprarCarga2);

		jBtnComprarCarga2 = new JButton("");
		jBtnComprarCarga2.setOpaque(true);
		jBtnComprarCarga2.setBackground(Color.WHITE);
		jBtnComprarCarga2.setBounds(196, 48, 15, 15);
		jCardComprar.add(jBtnComprarCarga2);

		jLblComprarUpgrade = new JLabel("Upgrade");
		jLblComprarUpgrade.setBounds(165, 93, 70, 15);
		jCardComprar.add(jLblComprarUpgrade);

		jBtnComprarUpgradeArma = new JButton("Melhorar Arma");
		jBtnComprarUpgradeArma.setBounds(10, 132, 175, 25);
		jCardComprar.add(jBtnComprarUpgradeArma);

		jBtnComprarUpgradeCargo = new JButton("Aumentar Capacidade");
		jBtnComprarUpgradeCargo.setBounds(196, 132, 175, 25);
		jCardComprar.add(jBtnComprarUpgradeCargo);

		jBtnComprarFinalizar = new JButton("Finalizar fase de compra");
		jBtnComprarFinalizar.setBounds(106, 168, 175, 25);
		jCardComprar.add(jBtnComprarFinalizar);

		jCardMover = new JPanel();
		jCards.add(jCardMover, "Mover");
		jCardMover.setLayout(null);

		jLblMover = new JLabel("Mover");
		jLblMover.setBounds(165, 5, 70, 15);
		jCardMover.add(jLblMover);

		jLblCliqueNaCartaMover = new JLabel("Clique na carta para a qual se deseja movimentar");
		jLblCliqueNaCartaMover.setBounds(45, 80, 280, 15);
		jCardMover.add(jLblCliqueNaCartaMover);

		jBtnMoverFinalizar = new JButton("Finalizar Movimento");
		jBtnMoverFinalizar.setBounds(106, 168, 175, 25);
		jCardMover.add(jBtnMoverFinalizar);

		jCardWormhole = new JPanel();
		jCards.add(jCardWormhole, "Wormholes");
		jCardWormhole.setLayout(null);

		jLblWormhole = new JLabel("Wormhole");
		jLblWormhole.setBounds(165, 5, 70, 15);
		jCardWormhole.add(jLblWormhole);

		jLblCliqueNaCartaWormhole = new JLabel("Clique na carta Wormhole destino");
		jLblCliqueNaCartaWormhole.setBounds(95, 103, 280, 15);
		jCardWormhole.add(jLblCliqueNaCartaWormhole);

		cl = (CardLayout) (jCards.getLayout());
	}
	
	protected void preencheTabuleiro() 
	{
		jTabuleiro = new ArrayList<JButton>();
		jRecurso = new ArrayList<JLabel>();
		
		JButton jBtnCarta1 = new JButton("");
		jBtnCarta1.setBounds(10, 610, 90, 90);
		jBtnCarta1.setIcon(new ImageIcon(new ImageIcon("./Imagens/Wormhole.png").getImage().getScaledInstance(90, 90,100),"Wormhole"));
		
		JButton jBtnCarta2 = new JButton("");
		jBtnCarta2.setBounds(10, 510, 90, 90);
		jBtnCarta2.setIcon(new ImageIcon(new ImageIcon("./Imagens/VersoCarta.png").getImage().getScaledInstance(90, 90,100),"VersoCarta"));

		JButton jBtnCarta3 = new JButton("");
		jBtnCarta3.setBounds(10, 410, 90, 90);
		jBtnCarta3.setIcon(new ImageIcon(new ImageIcon("./Imagens/VersoCarta.png").getImage().getScaledInstance(90, 90,100),"VersoCarta"));

		JButton jBtnCarta4 = new JButton("");
		jBtnCarta4.setBounds(110, 410, 90, 90);
		jBtnCarta4.setIcon(new ImageIcon(new ImageIcon("./Imagens/VersoCarta.png").getImage().getScaledInstance(90, 90,100),"VersoCarta"));

		JButton jBtnCarta5 = new JButton("");
		jBtnCarta5.setBounds(110, 310, 90, 90);
		jBtnCarta5.setIcon(new ImageIcon(new ImageIcon("./Imagens/VersoCarta.png").getImage().getScaledInstance(90, 90,100),"VersoCarta"));

		JButton jBtnCarta6 = new JButton("");
		jBtnCarta6.setBounds(210, 410, 90, 90);
		jBtnCarta6.setIcon(new ImageIcon(new ImageIcon("./Imagens/VersoCarta.png").getImage().getScaledInstance(90, 90,100),"VersoCarta"));

		JButton jBtnCarta7 = new JButton("");
		jBtnCarta7.setBounds(210, 310, 90, 90);
		jBtnCarta7.setIcon(new ImageIcon(new ImageIcon("./Imagens/VersoCarta.png").getImage().getScaledInstance(90, 90,100),"VersoCarta"));

		JButton jBtnCarta8 = new JButton("");
		jBtnCarta8.setBounds(210, 210, 90, 90);
		jBtnCarta8.setIcon(new ImageIcon(new ImageIcon("./Imagens/VersoCarta.png").getImage().getScaledInstance(90, 90,100),"VersoCarta"));

		JButton jBtnCarta9 = new JButton("");
		jBtnCarta9.setBounds(310, 310, 90, 90);
		jBtnCarta9.setIcon(new ImageIcon(new ImageIcon("./Imagens/VersoCarta.png").getImage().getScaledInstance(90, 90,100),"VersoCarta"));

		JButton jBtnCarta10 = new JButton("");
		jBtnCarta10.setBounds(310, 210, 90, 90);
		jBtnCarta10.setIcon(new ImageIcon(new ImageIcon("./Imagens/VersoCarta.png").getImage().getScaledInstance(90, 90,100),"VersoCarta"));

		JButton jBtnCarta11 = new JButton("");
		jBtnCarta11.setBounds(310, 110, 90, 90);
		jBtnCarta11.setIcon(new ImageIcon(new ImageIcon("./Imagens/VersoCarta.png").getImage().getScaledInstance(90, 90,100),"VersoCarta"));

		JButton jBtnCarta12 = new JButton("");
		jBtnCarta12.setBounds(410, 410, 90, 90);
		jBtnCarta12.setIcon(new ImageIcon(new ImageIcon("./Imagens/VersoCarta.png").getImage().getScaledInstance(90, 90,100),"VersoCarta"));
		
		JButton jBtnCarta13 = new JButton("");
		jBtnCarta13.setBounds(410, 310, 90, 90);
		jBtnCarta13.setIcon(new ImageIcon(new ImageIcon("./Imagens/VersoCarta.png").getImage().getScaledInstance(90, 90,100),"VersoCarta"));

		JButton jBtnCarta14 = new JButton("");
		jBtnCarta14.setBounds(410, 210, 90, 90);
		jBtnCarta14.setIcon(new ImageIcon(new ImageIcon("./Imagens/VersoCarta.png").getImage().getScaledInstance(90, 90,100),"VersoCarta"));

		JButton jBtnCarta15 = new JButton("");
		jBtnCarta15.setBounds(510, 510, 90, 90);
		jBtnCarta15.setIcon(new ImageIcon(new ImageIcon("./Imagens/VersoCarta.png").getImage().getScaledInstance(90, 90,100),"VersoCarta"));

		JButton jBtnCarta16 = new JButton("");
		jBtnCarta16.setBounds(510, 410, 90, 90);
		jBtnCarta16.setIcon(new ImageIcon(new ImageIcon("./Imagens/VersoCarta.png").getImage().getScaledInstance(90, 90,100),"VersoCarta"));
		
		JButton jBtnCarta17 = new JButton("");
		jBtnCarta17.setBounds(510, 310, 90, 90);
		jBtnCarta17.setIcon(new ImageIcon(new ImageIcon("./Imagens/VersoCarta.png").getImage().getScaledInstance(90, 90,100),"VersoCarta"));

		JButton jBtnCarta18 = new JButton("");
		jBtnCarta18.setBounds(610, 410, 90, 90);
		jBtnCarta18.setIcon(new ImageIcon(new ImageIcon("./Imagens/VersoCarta.png").getImage().getScaledInstance(90, 90,100),"VersoCarta"));

		JButton jBtnCarta19 = new JButton("");
		jBtnCarta19.setBounds(610, 310, 90, 90);
		jBtnCarta19.setIcon(new ImageIcon(new ImageIcon("./Imagens/VersoCarta.png").getImage().getScaledInstance(90, 90,100),"VersoCarta"));

		JButton jBtnCarta20 = new JButton("");
		jBtnCarta20.setBounds(610, 210, 90, 90);
		jBtnCarta20.setIcon(new ImageIcon(new ImageIcon("./Imagens/VersoCarta.png").getImage().getScaledInstance(90, 90,100),"VersoCarta"));

		JButton jBtnCarta21 = new JButton("");
		jBtnCarta21.setBounds(710, 310, 90, 90);
		jBtnCarta21.setIcon(new ImageIcon(new ImageIcon("./Imagens/VersoCarta.png").getImage().getScaledInstance(90, 90,100),"VersoCarta"));

		JButton jBtnCarta22 = new JButton("");
		jBtnCarta22.setBounds(710, 210, 90, 90);
		jBtnCarta22.setIcon(new ImageIcon(new ImageIcon("./Imagens/VersoCarta.png").getImage().getScaledInstance(90, 90,100),"VersoCarta"));

		JButton jBtnCarta23 = new JButton("");
		jBtnCarta23.setBounds(810, 210, 90, 90);
		jBtnCarta23.setIcon(new ImageIcon(new ImageIcon("./Imagens/VersoCarta.png").getImage().getScaledInstance(90, 90,100),"VersoCarta"));

		JButton jBtnCarta24 = new JButton("");
		jBtnCarta24.setBounds(810, 110, 90, 90);
		jBtnCarta24.setIcon(new ImageIcon(new ImageIcon("./Imagens/VersoCarta.png").getImage().getScaledInstance(90, 90,100),"VersoCarta"));

		JButton jBtnCarta25 = new JButton("");
		jBtnCarta25.setBounds(810, 10, 90, 90);
		jBtnCarta25.setIcon(new ImageIcon(new ImageIcon("./Imagens/Wormhole.png").getImage().getScaledInstance(90, 90,100),"Wormhole"));
		
		jTabuleiro.add(jBtnCarta1);
		jTabuleiro.add(jBtnCarta2);
		jTabuleiro.add(jBtnCarta3);
		jTabuleiro.add(jBtnCarta4);
		jTabuleiro.add(jBtnCarta5);
		jTabuleiro.add(jBtnCarta6);
		jTabuleiro.add(jBtnCarta7);
		jTabuleiro.add(jBtnCarta8);
		jTabuleiro.add(jBtnCarta9);
		jTabuleiro.add(jBtnCarta10);
		jTabuleiro.add(jBtnCarta11);
		jTabuleiro.add(jBtnCarta12);
		jTabuleiro.add(jBtnCarta13);
		jTabuleiro.add(jBtnCarta14);
		jTabuleiro.add(jBtnCarta15);
		jTabuleiro.add(jBtnCarta16);
		jTabuleiro.add(jBtnCarta17);
		jTabuleiro.add(jBtnCarta18);
		jTabuleiro.add(jBtnCarta19);
		jTabuleiro.add(jBtnCarta20);
		jTabuleiro.add(jBtnCarta21);
		jTabuleiro.add(jBtnCarta22);
		jTabuleiro.add(jBtnCarta23);
		jTabuleiro.add(jBtnCarta24);
		jTabuleiro.add(jBtnCarta25);
		
		for(int i = 0; i < 25; i++)
			jPanel.add(jTabuleiro.get(i));
	}
	
	protected void registarListenersJ()
	{
	    for(int i = 0; i < 25; i++)
	    {
	    	jTabuleiro.get(i).addActionListener(listeners.new ListenerJBtnCarta());
	    	jTabuleiro.get(i).setRolloverEnabled(false);
	    	jTabuleiro.get(i).setBorderPainted(false);
	    	jTabuleiro.get(i).setFocusable(false);
	    }
		jBtnSave.addActionListener(listeners.new ListenerJBtnSave());
		jBtnSair.addActionListener(listeners.new ListenerJBtnSair());
		
		//Carta 1 - Vender
		jBtnVenderCarga1.addActionListener(listeners.new ListenerJBtnVenderCarga());
		jBtnVenderCarga2.addActionListener(listeners.new ListenerJBtnVenderCarga());
		jBtnVenderCarga3.addActionListener(listeners.new ListenerJBtnVenderCarga());
		jBtnVenderUpgradeArma.addActionListener(listeners.new ListenerJBtnUpgradeArma());
		jBtnVenderUpgradeCargo.addActionListener(listeners.new ListenerJBtnUpgradeCargo());
		jBtnVenderFinalizar.addActionListener(listeners.new ListenerJBtnVenderFinalizar());
		
		//Carta 2 - Comprar
		jBtnComprarCarga1.addActionListener(listeners.new ListenerJBtnComprarCarga());
		jBtnComprarCarga2.addActionListener(listeners.new ListenerJBtnComprarCarga());
		jBtnComprarUpgradeArma.addActionListener(listeners.new ListenerJBtnUpgradeArma());
		jBtnComprarUpgradeCargo.addActionListener(listeners.new ListenerJBtnUpgradeCargo());
		jBtnComprarFinalizar.addActionListener(listeners.new ListenerJBtnComprarFinalizar());
		
		//Carta 2 - Mover
		jBtnMoverFinalizar.addActionListener(listeners.new ListenerJBtnMoverFinalizar());
	}
	
	protected void carregaJogo()
	{
        inicializaPaginaJogo();
        preencheTabuleiro();
        registarListenersJ();
        actualizaCartas();
        pintaRecursosVenda();
        actualizaToken();
        ArrayList <String> tmp = jogo.getHistoricoAcontecimento();
        for (int i = 0; i < tmp.size(); i++)
      	  jTaAcontecimentos.append(tmp.get(i) + "\n");
    	int nivel = jogo.getUtilizador().getArma();
		if(nivel == 4)
			jLblNivelArma.setBounds(1016, 114, 21, 21);
		if(nivel == 5)
			jLblNivelArma.setBounds(1076, 114, 21, 21);
		if(jogo.getUtilizador().getNumeroCargas() == 3)
			jCarga.get(2).setText("");
    }
	
	protected void actualizaToken()
	{
    	jLblDinheiro.setText("Moedas: " + jogo.getUtilizador().getDinheiro());
    	int x = jTabuleiro.get(jogo.getUtilizador().getPosicao()).getBounds().x;
    	int y = jTabuleiro.get(jogo.getUtilizador().getPosicao()).getBounds().y;
    	jLblImagemToken.setBounds(x+30, y+30, 30, 30);
	}
	
	protected void actualizaCartas()
	{
		for(int i = 0; i < 25; i++)
		{
			if(jogo.getCarta(i).visivel() && ((ImageIcon)jTabuleiro.get(i).getIcon()).toString().equals("VersoCarta"))
			{
				switch (jogo.getCarta(i).toString())
				{
					case "Espaco":
						jTabuleiro.get(i).setIcon(new ImageIcon(new ImageIcon("./Imagens/Espaco.png").getImage().getScaledInstance(90, 90,100),"Espaco"));
					break;
					
					case "Wormhole":
						jTabuleiro.get(i).setIcon(new ImageIcon(new ImageIcon("./Imagens/Wormhole.png").getImage().getScaledInstance(90, 90,100),"Wormhole"));
					break;
					
                    case "Arrakis":
                    	jTabuleiro.get(i).setIcon(new ImageIcon(new ImageIcon("./Imagens/Arrakis.png").getImage().getScaledInstance(90, 90,100),"Arrakis"));
                    	jRecurso.add(new JLabel(""));
                    	jRecurso.get(jRecurso.size()-1).setOpaque(true);
                    	jGlass.add(jRecurso.get(jRecurso.size()-1));
                    	
                    	jRecurso.add(new JLabel(""));
                    	jRecurso.get(jRecurso.size()-1).setOpaque(true);
                    	jGlass.add(jRecurso.get(jRecurso.size()-1));
                        break;
                        
                    case "Gethen":
                    	jTabuleiro.get(i).setIcon(new ImageIcon(new ImageIcon("./Imagens/Gethen.png").getImage().getScaledInstance(90, 90,100),"Gethen"));
                    	jRecurso.add(new JLabel(""));
                    	jRecurso.get(jRecurso.size()-1).setOpaque(true);
                    	jGlass.add(jRecurso.get(jRecurso.size()-1));
                    	
                    	jRecurso.add(new JLabel(""));
                    	jRecurso.get(jRecurso.size()-1).setOpaque(true);
                    	jGlass.add(jRecurso.get(jRecurso.size()-1));
                        break;
                        
                    case "Kiber":
                    	jTabuleiro.get(i).setIcon(new ImageIcon(new ImageIcon("./Imagens/Kiber.png").getImage().getScaledInstance(90, 90,100),"Kiber"));
                    	jRecurso.add(new JLabel(""));
                    	jRecurso.get(jRecurso.size()-1).setOpaque(true);
                    	jGlass.add(jRecurso.get(jRecurso.size()-1));
                    	
                    	jRecurso.add(new JLabel(""));
                    	jRecurso.get(jRecurso.size()-1).setOpaque(true);
                    	jGlass.add(jRecurso.get(jRecurso.size()-1));
                        break;
                        
                    case "Lamarckia":
                    	jTabuleiro.get(i).setIcon(new ImageIcon(new ImageIcon("./Imagens/Lamarckia.png").getImage().getScaledInstance(90, 90,100),"Lamarckia"));
                    	jRecurso.add(new JLabel(""));
                    	jRecurso.get(jRecurso.size()-1).setOpaque(true);
                    	jGlass.add(jRecurso.get(jRecurso.size()-1));
                    	
                    	jRecurso.add(new JLabel(""));
                    	jRecurso.get(jRecurso.size()-1).setOpaque(true);
                    	jGlass.add(jRecurso.get(jRecurso.size()-1));
                        break;
                        
                    case "Reverie":
                    	jTabuleiro.get(i).setIcon(new ImageIcon(new ImageIcon("./Imagens/Reverie.png").getImage().getScaledInstance(90, 90,100),"Reverie"));
                    	jRecurso.add(new JLabel(""));
                    	jRecurso.get(jRecurso.size()-1).setOpaque(true);
                    	jGlass.add(jRecurso.get(jRecurso.size()-1));
                    	
                    	jRecurso.add(new JLabel(""));
                    	jRecurso.get(jRecurso.size()-1).setOpaque(true);
                    	jGlass.add(jRecurso.get(jRecurso.size()-1));
                        break;
                        
                    case "Tiamat":
                    	jTabuleiro.get(i).setIcon(new ImageIcon(new ImageIcon("./Imagens/Tiamat.png").getImage().getScaledInstance(90, 90,100),"Tiamat"));
                    	jRecurso.add(new JLabel(""));
                    	jRecurso.get(jRecurso.size()-1).setOpaque(true);
                    	jGlass.add(jRecurso.get(jRecurso.size()-1));
                    	
                    	jRecurso.add(new JLabel(""));
                    	jRecurso.get(jRecurso.size()-1).setOpaque(true);
                    	jGlass.add(jRecurso.get(jRecurso.size()-1));
                        break;

                    case "Asperta":
                    	jTabuleiro.get(i).setIcon(new ImageIcon(new ImageIcon("./Imagens/Asperta.png").getImage().getScaledInstance(90, 90,100),"Asperta"));
                    	jRecurso.add(new JLabel(""));
                    	jRecurso.get(jRecurso.size()-1).setOpaque(true);
                    	jGlass.add(jRecurso.get(jRecurso.size()-1));
                        break;
                        
                    case "Striterax":
                    	jTabuleiro.get(i).setIcon(new ImageIcon(new ImageIcon("./Imagens/Striterax.png").getImage().getScaledInstance(90, 90,100),"Striterax"));
                    	jRecurso.add(new JLabel(""));
                    	jRecurso.get(jRecurso.size()-1).setOpaque(true);
                    	jGlass.add(jRecurso.get(jRecurso.size()-1));
                        break;
                        
                    case "Whirl":
                    	jTabuleiro.get(i).setIcon(new ImageIcon(new ImageIcon("./Imagens/Whirl.png").getImage().getScaledInstance(90, 90,100),"Whirl"));
                    	jRecurso.add(new JLabel(""));
                    	jRecurso.get(jRecurso.size()-1).setOpaque(true);
                    	jGlass.add(jRecurso.get(jRecurso.size()-1));
                        break;
				}
			}
		}
	}
	
	protected void pintaRecursosVenda()
	{
		Carta c;
		String cor;
		int x,y,width = 14,height = 14;
		int cargaAtual = 0;
		
		for(int i = 0; i < 25; i++)
		{
			c = jogo.getCarta(i);
			x = jTabuleiro.get(i).getBounds().x;
			y = jTabuleiro.get(i).getBounds().y + 73;
			if(c instanceof Planeta && ((Planeta)c).visivel())
			{
				
				cor = ((Planeta)c).getRecursoVender(0).toString();
				jRecurso.get(cargaAtual).setBackground(jGlass.getCor(cor));
		    	jRecurso.get(cargaAtual).setBounds(x+30, y, width, height);
				cargaAtual++;
				
				cor = ((Planeta)c).getRecursoVender(1).toString();
				jRecurso.get(cargaAtual).setBackground(jGlass.getCor(cor));
		    	jRecurso.get(cargaAtual).setBounds(x+46, y, width, height);
				cargaAtual++;

			}
			if(c instanceof PlanetaPirata && ((PlanetaPirata)c).visivel())
			{
				cor = ((PlanetaPirata)c).getRecursoVender().toString();
				jRecurso.get(cargaAtual).setBackground(jGlass.getCor(cor));
		    	jRecurso.get(cargaAtual).setBounds(x+38, y, width, height);
				cargaAtual++;
			}
		}
		
		for(int i = 0; i < jogo.getUtilizador().getNumeroCargas(); i++)
			jCarga.get(i).setBackground(jGlass.getCor(jogo.getUtilizador().getCarga(i).toString()));
	}
	
    protected void registarObservers()
    {
        jogo.addObserver(this);
        jogo.setEstado(jogo.getEstado());
    }
    
	public void update(Observable arg0, Object arg1) {
		estado = jogo.getEstado();
		
        if (estado instanceof InicioJogo)
        {
        	
        }
		
        if (estado instanceof Venda)
        {
        	jLblDinheiro.setText("Moedas: " + jogo.getUtilizador().getDinheiro());
        	Carta p = jogo.getCarta(jogo.getUtilizador().getPosicao());
            if (p instanceof Planeta || p instanceof PlanetaPirata) 
            {
            	cl.show(jCards, "Venda");
            	Carga c;
            	
            	if (p instanceof PlanetaPirata)
            	{
            		for(int i = 0; i < jogo.getUtilizador().getNumeroCargas(); i++)
            		{
            			c = jogo.getUtilizador().getCarga(i);
            			switch(i)
            			{
	            			case 0:
	            				if (c.getClass().equals(((PlanetaPirata)p).getRecursoComprar().getClass()))
            						jLblVenderCarga1.setText("3");
	            				else
	            					jLblVenderCarga1.setText("-");
	            				break;
	            			case 1:
	            				if (c.getClass().equals(((PlanetaPirata)p).getRecursoComprar().getClass()))
            						jLblVenderCarga2.setText("3");
	            				else
	            					jLblVenderCarga2.setText("-");
	            				break;
	            			case 2:
	            				if (c.getClass().equals(((PlanetaPirata)p).getRecursoComprar().getClass()))
            						jLblVenderCarga3.setText("3");
	            				else
	            					jLblVenderCarga3.setText("-");
	            				break;
            			}
            		}
            	}
            	
            	c = jogo.getUtilizador().getCarga(0);
            	switch (c.toString())
            	{
	            	case "Amarelo":
	            		if (p instanceof Planeta)
	            			jLblVenderCarga1.setText(new String(""+((Planeta)p).getPrecoComprar(c)));
	            		jBtnVenderCarga1.setBackground(Color.YELLOW);
	            		break;
	            	case "Azul":
	            		if (p instanceof Planeta)
	            			jLblVenderCarga1.setText(new String(""+((Planeta)p).getPrecoComprar(c)));
	            		jBtnVenderCarga1.setBackground(Color.BLUE);
	            		break;
	            	case "Branco":
	            		jLblVenderCarga1.setText(new String("-"));
	            		jBtnVenderCarga1.setBackground(Color.WHITE);
	            		break;
	            	case "Vazio":
	            		jLblVenderCarga1.setText(new String("-"));
	            		jBtnVenderCarga1.setBackground(Color.LIGHT_GRAY);
	            		break;
	            	case "Vermelho":
	            		if (p instanceof Planeta)
	            			jLblVenderCarga1.setText(new String(""+((Planeta)p).getPrecoComprar(c)));
	            		jBtnVenderCarga1.setBackground(Color.RED);
	            		break;
	            	case "Preto":
	            		if (p instanceof Planeta)
	            			jLblVenderCarga1.setText(new String(""+((Planeta)p).getPrecoComprar(c)));
	            		jBtnVenderCarga1.setBackground(Color.BLACK);
	            		break;
            	}
            	
            	c = jogo.getUtilizador().getCarga(1);
            	switch (c.toString())
            	{
	            	case "Amarelo":
	            		if (p instanceof Planeta)
	            			jLblVenderCarga2.setText(new String(""+((Planeta)p).getPrecoComprar(c)));
	            		jBtnVenderCarga2.setBackground(Color.YELLOW);
	            		break;
	            	case "Azul":
	            		if (p instanceof Planeta)
	            			jLblVenderCarga2.setText(new String(""+((Planeta)p).getPrecoComprar(c)));
	            		jBtnVenderCarga2.setBackground(Color.BLUE);
	            		break;
	            	case "Branco":
	            		jLblVenderCarga2.setText(new String("-"));
	            		jBtnVenderCarga2.setBackground(Color.WHITE);
	            		break;
	            	case "Vazio":
	            		jLblVenderCarga2.setText(new String("-"));
	            		jBtnVenderCarga2.setBackground(Color.LIGHT_GRAY);
	            		break;
	            	case "Vermelho":
	            		if (p instanceof Planeta)
	            			jLblVenderCarga2.setText(new String(""+((Planeta)p).getPrecoComprar(c)));
	            		jBtnVenderCarga2.setBackground(Color.RED);
	            		break;
	            	case "Preto":
	            		if (p instanceof Planeta)
	            			jLblVenderCarga2.setText(new String(""+((Planeta)p).getPrecoComprar(c)));
	            		jBtnVenderCarga2.setBackground(Color.BLACK);
	            		break;
            	}
            	
            	if(jogo.getUtilizador().getNumeroCargas() == 2)
            	{
            		jLblVenderCarga3.setText("-");
            		jBtnVenderCarga3.setBackground(Color.DARK_GRAY);
            	}
            	else
            	{
                	c = jogo.getUtilizador().getCarga(2);
                	switch (c.toString())
                	{
    	            	case "Amarelo":
    	            		if (p instanceof Planeta)
    	            			jLblVenderCarga3.setText(new String(""+((Planeta)p).getPrecoComprar(c)));
    	            		jBtnVenderCarga3.setBackground(Color.YELLOW);
    	            		break;
    	            	case "Azul":
    	            		if (p instanceof Planeta)
    	            			jLblVenderCarga3.setText(new String(""+((Planeta)p).getPrecoComprar(c)));
    	            		jBtnVenderCarga3.setBackground(Color.BLUE);
    	            		break;
    	            	case "Branco":
    	            		jLblVenderCarga3.setText(new String("-"));
    	            		jBtnVenderCarga3.setBackground(Color.WHITE);
    	            		break;
    	            	case "Vazio":
    	            		jLblVenderCarga3.setText(new String("-"));
    	            		jBtnVenderCarga3.setBackground(Color.LIGHT_GRAY);
    	            		break;
    	            	case "Vermelho":
    	            		if (p instanceof Planeta)
    	            			jLblVenderCarga3.setText(new String(""+((Planeta)p).getPrecoComprar(c)));
    	            		jBtnVenderCarga3.setBackground(Color.RED);
    	            		break;
    	            	case "Preto":
    	            		if (p instanceof Planeta)
    	            			jLblVenderCarga3.setText(new String(""+((Planeta)p).getPrecoComprar(c)));
    	            		jBtnVenderCarga3.setBackground(Color.BLACK);
    	            		break;
                	}
            	}
        		for(int i = 0; i < jogo.getUtilizador().getNumeroCargas(); i++)
        			jCarga.get(i).setBackground(jGlass.getCor(jogo.getUtilizador().getCarga(i).toString()));
            }
            else
            	jogo.finalVenda();
        }

        if (estado instanceof Compra)
        {
        	Carta p = jogo.getCarta(jogo.getUtilizador().getPosicao());
        	jLblDinheiro.setText("Moedas: " + jogo.getUtilizador().getDinheiro());
            if (p instanceof Planeta) 
            {
            	cl.show(jCards, "Compra");
            	jLblComprarCarga1.setVisible(true);
            	jBtnComprarCarga1.setVisible(true);
            	
            	Planeta c = (Planeta)p;
            	switch (c.getRecursoVender(0).toString())
            	{
	            	case "Amarelo":
	            		jLblComprarCarga1.setText(new String(""+c.getPrecoVender(0)));
	            		jBtnComprarCarga1.setBackground(Color.YELLOW);
	            		break;
	            	case "Azul":
	            		jLblComprarCarga1.setText(new String(""+c.getPrecoVender(0)));
	            		jBtnComprarCarga1.setBackground(Color.BLUE);
	            		break;
	            	case "Branco":
	            		jLblComprarCarga1.setText(new String("-"));
	            		jBtnComprarCarga1.setBackground(Color.WHITE);
	            		break;
	            	case "Vazio":
	            		jLblComprarCarga1.setText(new String("-"));
	            		jBtnComprarCarga1.setBackground(Color.LIGHT_GRAY);
	            		break;
	            	case "Vermelho":
	            		jLblComprarCarga1.setText(new String(""+c.getPrecoVender(0)));
	            		jBtnComprarCarga1.setBackground(Color.RED);
	            		break;
            	}
            	
            	switch (c.getRecursoVender(1).toString())
            	{
	            	case "Amarelo":
	            		jLblComprarCarga2.setText(new String(""+c.getPrecoVender(1)));
	            		jBtnComprarCarga2.setBackground(Color.YELLOW);
	            		break;
	            	case "Azul":
	            		jLblComprarCarga2.setText(new String(""+c.getPrecoVender(1)));
	            		jBtnComprarCarga2.setBackground(Color.BLUE);
	            		break;
	            	case "Branco":
	            		jLblComprarCarga2.setText(new String("-"));
	            		jBtnComprarCarga2.setBackground(Color.WHITE);
	            		break;
	            	case "Vazio":
	            		jLblComprarCarga2.setText(new String("-"));
	            		jBtnComprarCarga2.setBackground(Color.LIGHT_GRAY);
	            		break;
	            	case "Vermelho":
	            		jLblComprarCarga2.setText(new String(""+c.getPrecoVender(1)));
	            		jBtnComprarCarga2.setBackground(Color.RED);
	            		break;
            	}
            	pintaRecursosVenda();
            }
            else if(p instanceof PlanetaPirata)
            {
            	cl.show(jCards, "Compra");
            	jLblComprarCarga1.setVisible(false);
            	jBtnComprarCarga1.setVisible(false);
            	PlanetaPirata c = (PlanetaPirata)p;
            	
            	switch (c.getRecursoVender().toString())
            	{
	            	case "Preto":
	            		jLblComprarCarga2.setText("1");
	            		jBtnComprarCarga2.setBackground(Color.BLACK);
	            		break;
	            	case "Branco":
	            		jLblComprarCarga2.setText(new String("-"));
	            		jBtnComprarCarga2.setBackground(Color.WHITE);
	            		break;
	            	case "Vazio":
	            		jLblComprarCarga2.setText(new String("-"));
	            		jBtnComprarCarga2.setBackground(Color.LIGHT_GRAY);
	            		break;
            	}
        	    pintaRecursosVenda();
            }
            else
            	jogo.finalCompra();
        }
        
        if (estado instanceof Mover)
        {
        	cl.show(jCards, "Mover");
        	
        	actualizaToken();
        }
        
        if (estado instanceof Wormholes)
        {
        	cl.show(jCards, "Wormholes");
        	
        	actualizaToken();
        }
	}
}