package pitchDetection;

import java.io.File;
import utilities.*;

public class PitchCorrection {
	
	private double[] magnitude;
	private double[] frequency;
	private double[] maxFrequency;
	private double[] desiredFrequency;
	
	public File correct(Storage pitch) {
		
		double magHolder;
		double freqHolder;
		double desiredFreq;
		double distance;//the distance in Hz between the desired frequency and the current frequency
		
		frequency = pitch.getFrequencies();
		magnitude = pitch.getMagnitudes();
		maxFrequency = pitch.getMaxFrequencies();
		
		Note[] holder = pitch.getPitchArray();
		
		desiredFrequency = new double[pitch.getPitchArray().length];
		for(int i = 0; i < desiredFrequency.length; i++)
		{
				desiredFrequency[i] = holder[i].getFrequency();
		}
		
		for(int i = 0; i < pitch.getFrameSize(); i++)
		{	
				magHolder = magnitude[i];
				freqHolder = maxFrequency[i];
				desiredFreq = desiredFrequency[i];
				
				if(desiredFreq!= 0.0)
				{
						System.out.println("frequency: " + freqHolder);
						System.out.println("desiredFrequency: " + desiredFreq);
		
						if(desiredFreq > freqHolder){
							distance = desiredFreq-freqHolder;
						} else {
							distance = freqHolder-desiredFreq;
						}
						
						System.out.println(distance);
				}
		}
		
		return null;
	}

}
