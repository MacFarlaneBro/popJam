package pitchDetection;

import java.io.File;
import utilities.*;

public class PitchCorrection {
	
	private double[] magnitude;
	private double[] frequency;
	private double[] maxFrequency;
	private double[] desiredFrequency;
	
	public File correct(Storage pitch) {

		double distance;//the distance in Hz between the desired frequency and the current frequency
		
		frequency = pitch.getFrequencies();// the real frequencies of the entire sample
		magnitude = pitch.getMagnitudes();// the real magnitudes of the entire sample
		maxFrequency = pitch.getMaxFrequencies();// the max frequencies of the entire sample
		
		System.out.println(maxFrequency.length);
		
		Note[] holder = pitch.getPitchArray();
		
		desiredFrequency = new double[pitch.getPitchArray().length];
		for(int i = 0; i < desiredFrequency.length; i++)
		{
				desiredFrequency[i] = holder[i].getFrequency();
		}
		
		for(int i = 0; i < desiredFrequency.length; i++)//what is this loop doing?
		{	
				
				if(desiredFrequency[i]!= 0.0)
				{
						System.out.println("frequency: " + maxFrequency[i]);
						System.out.println("desiredFrequency: " + desiredFrequency[i]);
		
						if(desiredFrequency[i] > maxFrequency[i])
						{
							distance = desiredFrequency[i]-maxFrequency[i];
							
						} 
						else 
						{
							distance = maxFrequency[i]-desiredFrequency[i];
						}
						
						System.out.println(distance);
				}
		}
		
		return null;
	}

}
