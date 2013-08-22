package pitchDetection;

import java.io.File;

import utilities.*;

public class PitchCorrection {
	
	private AudioData preDetect;
	private AudioData postDetect;
	private File theFile;

	public PitchCorrection(File theFile){
		this.theFile = theFile;
		Wav wav = new Wav();
		preDetect = wav.getSoundData(theFile);
	}
	
	public void correct() {
		PitchDetection detector = new PitchDetection();
		postDetect = detector.detect(preDetect);
		
	}
}