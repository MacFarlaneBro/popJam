package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import accompaniment.SynthModule;

public class SamplePanel extends GuiPanel{
	
	private String notes[] = {"A", "B", "C", "D", "E", "F", "G"};
	private JLabel label;
	private JComboBox<String> rootNote;
	private JTextField bpm;
	private JComboBox<String> scaleType;
	private JButton startStop;
	private String scales[] = {"major", "minor", "pentatonic"};
	private Boolean playing = false;
	private SynthModule synth;

	public SamplePanel(JPanel controller, GridLayout flowLayout) {
		super(controller, flowLayout);
		
		label = new JLabel("Select Your root note here: ");
		this.add(label);
		
		rootNote = new JComboBox<String>(notes);
		this.add(rootNote);
		
		label = new JLabel("Enter Your Chosen BPM here: ");
		this.add(label);
		
		bpm = new JTextField("60");
		this.add(bpm);
		
		label = new JLabel("Enter Your Chosen Scale Type: ");
		this.add(label);
		
		scaleType = new JComboBox<String>(scales);
		this.add(scaleType);
		
		startStop = new JButton("Start");
		startStop.setActionCommand("stop");
		startStop.addActionListener(this);
		this.add(startStop);
		
		createBackButton();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == startStop){
			if(playing){
				playing = false;
				startStop.setText("Start playing");
				backButton.setEnabled(true);
				stopMelody();
			} else if(!playing) {
				playing = true;
				startStop.setText("Stop playing");
				backButton.setEnabled(false);;
				startMelody();
			}
		} else if(e.getSource() == backButton){
			backButtonPressed();
		}
		
	}

	private void startMelody() {
		synth = new SynthModule();
		synth.melodyGenerator((String) rootNote.getSelectedItem(), Integer.parseInt(bpm.getText()), (String) scaleType.getSelectedItem());
	}
	
	private void stopMelody(){
		synth.stopper();
	}

}
