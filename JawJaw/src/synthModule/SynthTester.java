package synthModule;

import java.io.*;

import utilities.Note;
import utilities.Pitch;

import com.jsyn.*;
import com.jsyn.unitgen.*;

public class SynthTester {
	
	private Synthesizer synth;
	private UnitOscillator osc;
	private LinearRamp lag;
	private LineOut lineOut;
	
	public SynthTester()
	{
		synth = JSyn.createSynthesizer();
		
		// Add a tone generator.
		synth.add( osc = new TriangleOscillator() );
		// Add a lag to smooth out amplitude changes and avoid pops.
		synth.add( lag = new LinearRamp() );
		// Add an output mixer.
		synth.add( lineOut = new LineOut() );
		// Connect the oscillator to the left output.
		osc.output.connect( 0, lineOut.input, 0 );
		// Connect the oscillator to the right output.
		osc.output.connect( 0, lineOut.input, 1 );
		
		// Set the minimum, current and maximum values for the port.
		lag.output.connect( osc.amplitude );
		lag.input.setup( 0.0, 0.5, 1.0 );
		lag.time.set(  0.2 );
	}
	
	public void playPitch(String note){
		
		String startStop = "";
		
		Pitch pitch = new Pitch();
		
		osc.frequency.set(pitch.getFrequency(note));
		
		while(!startStop.equals("exit")){
			System.out.println("When you would like to hear the test pitch please enter any character, then enter any other character to stop, enter exit to exit");
			
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
			try {
				startStop = in.readLine();
				
				if(!startStop.equals("exit")){
					synth.start();
					lineOut.start();
				} else {
					break;
				}
				
				startStop = in.readLine();
				
				synth.stop();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
