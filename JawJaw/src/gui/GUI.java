package gui;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GUI implements ActionListener {
	
	JButton button;

	public GUI(){
	
	JFrame frame = new JFrame();
	
	button = new JButton("click me!");
	button.addActionListener(this);
		
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	frame.getContentPane().add(button);
	
	frame.setSize(300,400);
	frame.setVisible(true);
	
//	MyDrawPanel newPanel = new MyDrawPanel();
//	newPanel.paintComponent(null);
//	
//	
	}
	
	public void changeIt(){
		button.setText("I've Been Clicked!");
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		changeIt();
	}
	
	
}
