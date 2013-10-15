package gui;

import inputOutput.Recorder;

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.*;

import storage.AudioData;

public class RecorderPanel extends GuiPanel{
	
	private JTextField fileName;
	private JTextField bpm;
	private JLabel label;
	private JButton startStop;
	private Runnable theRecorder;
	private File file;
	private Thread recordingThread;
	private Boolean recording = false;
	
	public RecorderPanel(JPanel controller, LayoutManager layout) {
		super(controller, layout);
		
		label = new JLabel("Enter Your TrackName here: ");
		this.add(label);
		
		fileName = new JTextField("Track Name");
		this.add(fileName);
		
		label = new JLabel("Enter Your Chosen BPM here: ");
		this.add(label);
		
		bpm = new JTextField("bpm");
		this.add(bpm);
		
		startStop = new JButton("Start Recording");
		startStop.setActionCommand("stop");
		startStop.addActionListener(this);
		this.add(startStop);
		
		createBackButton();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == startStop){
			if(recording){
				
				Recorder stopper = (Recorder) theRecorder;
				stopper.getLine().close();
				
				recording = false;
				startStop.setText("Start Recording");
				backButton.setEnabled(true);
				
			} else if(!recording) {
				record();
				recording = true;
				startStop.setText("Stop Recording");
				backButton.setEnabled(false);;
			}
		} else if(e.getSource() == backButton){
			backButtonPressed();
		}
	}

	private void record() {
		System.out.println("here");
		file = new File(AudioData.AUDIO_FOLDER + fileName.getText() + ".wav");
		int tempo = Integer.parseInt(bpm.getText());
		theRecorder = new Recorder(file, tempo);
		recordingThread = new Thread(theRecorder);
		recordingThread.start();
	}
}
