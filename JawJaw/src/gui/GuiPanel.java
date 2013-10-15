package gui;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;


@SuppressWarnings("serial")
public abstract class GuiPanel extends JPanel implements ActionListener{
	
	JButton backButton;
	JButton button;
	JPanel controller;
	LayoutManager layout;
	
	public GuiPanel(JPanel controller, LayoutManager layout){
		
		this.layout = layout;
		
		backButton = new JButton("back");		
		this.setLayout(layout);
		this.controller = controller;
		
	}
	
	public void createBackButton(){
		backButton.addActionListener(this);
		this.add(backButton);
	}
	
	public void backButtonPressed(){
		this.setVisible(false);
		controller.setVisible(true);
		
	}
	
	
}
