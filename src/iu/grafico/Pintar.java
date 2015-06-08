package iu.grafico;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Pintar extends JComponent 
{
	public JFrame frame;
	private Rectangle rec;
	private String cor;
	
	public Pintar(JFrame frame)
	{
		this.frame=frame;
	}
	  
	  public Color getCor(String cor) 
	  {
		  switch (cor)
      	{
	    	case "Amarelo":
	    		return Color.YELLOW;
	    	case "Azul":
	    		return Color.BLUE;
	    	case "Branco":
	    		return Color.WHITE;
	    	case "Cinzento":
	    		return Color.GRAY;
	    	case "Preto":
	    		return Color.BLACK;
	    	case "Vazio":
	    		return Color.LIGHT_GRAY;
	    	case "Vermelho":
	    		return Color.RED;
    		default:
    			return Color.LIGHT_GRAY;
	    			
		}
	  }
}