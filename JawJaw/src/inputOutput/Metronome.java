package inputOutput;

import java.io.File;
import java.io.IOException;
import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.data.AudioSample;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.VariableRateStereoReader;
import com.jsyn.util.SampleLoader;

public class Metronome implements Runnable{
	
	private Synthesizer synth;
	private double bpm;
	private LineOut lineOut;
	private AudioSample hiHatSample;
	private VariableRateStereoReader player;
	private File hihat = new File(System.getProperty("user.dir") + "/drumSounds/hihatClosed.wav");

	public Metronome(double bpm){
		synth = JSyn.createSynthesizer();
		this.bpm = bpm;
		
	}

	@Override
	public void run() {
		start();
	}
	

	public void start(){
		
		try {
			hiHatSample = SampleLoader.loadFloatSample(hihat);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		synth.add(player = new VariableRateStereoReader());
		synth.add(lineOut = new LineOut());
		
		int numberOfFrames = hiHatSample.getNumFrames();
		double frameRate = hiHatSample.getFrameRate();
		double sampleLength = numberOfFrames/frameRate;//length of the hiHatSample in seconds
		
		double relative = 60/bpm;//representative tempo relative to 60bpm
		double multiplier = sampleLength/relative;
		double finalRate = frameRate*multiplier;//standard is a rate of 60bpm or one beat per minute
		player.rate.set(finalRate);
		player.dataQueue.queueLoop(hiHatSample, 0, hiHatSample.getNumFrames());
		player.output.connect(0, lineOut.getInput(), 0);
		player.output.connect(0, lineOut.getInput(), 1);
		
		player.amplitude.set(0.5);
				
		double sumTime = 60;
		
		//starting the synth, lineOut and metronome
		synth.start();
		player.start();
		lineOut.start();
		
		try
		{
			double time = synth.getCurrentTime();
			// Sleep for a 60s, after which the metronome will stop.
            synth.sleepUntil(time + sumTime);
		} 
		
		catch( InterruptedException e )
		{
			System.out.println("Metronome has closed");
		} finally {
			
			//making sure all the line outs, oscillators and synths have ceased function before exiting
			player.stop();
			lineOut.stop();
			synth.stop();
		}	
	}
}