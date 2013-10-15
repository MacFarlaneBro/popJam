package gui;

import inputOutput.Playback;

import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.filechooser.*;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PlayPanel extends GuiPanel{
	
	private JFileChooser fc;
	private JTextArea currentFile;
	private JButton openButton;
	private JButton playButton;
	private Playback player;
	private JButton stopButton;
	private File file;
	
	public PlayPanel(JPanel controller, FlowLayout flowLayout){
		
		super(controller, flowLayout);
		
        try {
			String current = new java.io.File( "." ).getCanonicalPath();
			fc = new JFileChooser(current + "/audio");
	        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        currentFile = new JTextArea(5, 20);
		currentFile.setMargin(new Insets(5,5,5,5));
		currentFile.setEditable(false);
		this.add(currentFile);
		currentFile.setText("No File Selected");
		
		openButton = new JButton("Open a File");
		openButton.addActionListener(this);
		this.add(openButton);
		
		playButton = new JButton("Play");
		playButton.addActionListener(this);
		this.add(playButton);
		
		stopButton = new JButton("Stop");
		stopButton.addActionListener(this);
		this.add(stopButton);
		
		createBackButton();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == openButton){
			int returnVal = fc.showOpenDialog(PlayPanel.this);
			
			if(returnVal == JFileChooser.APPROVE_OPTION){
				file = fc.getSelectedFile();
				player = new Playback(file.getName());
				currentFile.setText(file.getName());
			}
		} else if(e.getSource() == playButton){
			player.play();
		} else if(e.getSource() == stopButton){
			player.stop();
		} else if(e.getSource() == backButton){
			backButtonPressed();
			player.stop();
		}
		
	}

}
