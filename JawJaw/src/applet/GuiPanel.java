package applet;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.*;

public abstract class GuiPanel extends JPanel{
	
	public GuiPanel(){
		
		super(new GridLayout(6, 5));	
		setOpaque(true);
		setBackground(Color.black);
		setVisible(true);
	}
	
	
}
