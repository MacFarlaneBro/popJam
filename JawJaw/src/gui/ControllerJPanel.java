package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


@SuppressWarnings("serial")
public class ControllerJPanel extends JPanel implements ActionListener{
	
	private JButton record;
	private JButton play;
	private JButton generateAccompaniment;
	private JButton getSampleMelody;
	private GuiPanel selectedInstance;
	private JFrame topLevel;
	
	public ControllerJPanel(JFrame jFrame){
		
		super(new GridLayout(4,5));
		
		this.topLevel = jFrame;
		
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
			selectedInstance = new RecorderPanel(this, new GridLayout(4,2));
			System.out.println("buttonClicked!");
		}
		else if(e.getSource() == play)
		{
			selectedInstance = new PlayPanel(this, new FlowLayout());
		}
		else if(e.getSource() == generateAccompaniment)
		{
			selectedInstance = new AccompanimentPanel(this, new FlowLayout());
		}
		else if(e.getSource() == getSampleMelody)
		{
			selectedInstance = new SamplePanel(this, new GridLayout(4,2));
		}
		
		topLevel.add(selectedInstance);
		selectedInstance.setVisible(true);
		this.setVisible(false);
	}
}
