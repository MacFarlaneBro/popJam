package pitchDetection;

import java.io.File;

import utilities.Pitch;
import utilities.Storage;

public class PitchCorrection {
	
	private double[] magnitude;
	private double[] frequency;
	private double[] desiredFrequency;

	
	
	public File correct(Storage pitch) {
		
		double magHolder;
		double freqHolder;
		double desiredFreq;
		double distance;
		
		frequency = pitch.getFrequencies();
		magnitude = pitch.getMagnitudes();
		
		getFrequencies(pitch.getPitchArray());
		
		for(int i = 0; i < pitch.getFrameSize(); i++)
		{	
				magHolder = magnitude[i];
				freqHolder = frequency[i];
				desiredFreq = desiredFrequency[i];
				
				if(desiredFreq > freqHolder){
					distance = desiredFreq-freqHolder;
				} else {
					distance = freqHolder-desiredFreq;
				}
				
		}
		
		return null;
	}

	
	
	private void getFrequencies(String[] desiredPitch) {
		
		Pitch pitch = new Pitch();
		
		for(int i = 0; i < desiredPitch.length; i++)
		{
			desiredFrequency[i] = pitch.getFrequency(desiredPitch[i]);
		}
		
	}

}
