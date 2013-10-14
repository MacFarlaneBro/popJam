package applet;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import userInterface.Controller;

@SuppressWarnings("serial")
public class ControllerJPanel extends JPanel implements ActionListener{
	
	private JButton record;
	private JButton play;
	private JButton generateAccompaniment;
	private JButton getSampleMelody;
	private GuiPanel selectedInstance;
	private WebGui startingPane;
	
	public ControllerJPanel(){
		
		super(new GridLayout(4, 5));	
		
		setOpaque(true);
						
		record = new JButton("record");
		record.setActionCommand("record");
		record.addActionListener(this);
		this.add(record);	
		
		play = new JButton("play");
		play.setActionCommand("play");
		play.addActionListener(this);
		this.add(play);
		
		generateAccompaniment = new JButton("generateAccompaniment");
		generateAccompaniment.setActionCommand("generate");
		generateAccompaniment.addActionListener(this);
		this.add(generateAccompaniment);
		
		getSampleMelody = new JButton("getSampleMelody");
		getSampleMelody.setActionCommand("sample");
		getSampleMelody.addActionListener(this);
		this.add(getSampleMelody);
				
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == record)
		{
			selectedInstance = new RecorderPanel();
			System.out.println("buttonClicked!");
		}
		else if(e.getSource() == play)
		{
			selectedInstance = new PlayPanel();
		}
		else if(e.getSource() == generateAccompaniment)
		{
			selectedInstance = new AccompanimentPanel();
		}
		else if(e.getSource() == getSampleMelody)
		{
			selectedInstance = new SamplePanel();
		}
		
		this.add(selectedInstance);
	}
}
