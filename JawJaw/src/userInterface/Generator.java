package userInterface;

import java.io.File;

import pitchDetection.Analysis;

import utilities.AudioData;
import utilities.Mode;
import utilities.Note;
import utilities.Pitch;
import utilities.Wav;

public class Generator {
	
	private File file;
	private AudioData preDetect;
	private AudioData postDetect;
	private Note[] pitches;

	public Generator(File file){
		this.file = file;
		Wav wav = new Wav();
		preDetect = wav.getSoundData(file);
		
	}
	
	public void constructOutput(){
		//raw data from the sound file is analysed to produce pitch data
				Analysis analyser = new Analysis(preDetect);
				postDetect = analyser.getData();
				pitches = postDetect.getPitchArray();
				System.out.println("number of pitches: " + pitches.length);
				Mode mode = new Mode();
				int j = 0;
				double[] averagePitch = new double[100];
				
				System.out.print("Average Pitch List: ");
				for(int i = 0; i < pitches.length; i++){
					if(pitches[i] != null){
						//System.out.println("Index no: " + i);
						averagePitch[j] = (double) pitches[i].getFrequency();
						j++;
					}
					if(j == averagePitch.length-1){
						Pitch pitch = new Pitch();
						System.out.print(pitch.getNote((mode.getMode(averagePitch))).getPitch() + ", ");
						j = 0;
					}
					
				}
				postDetect.setAveragePitches(averagePitch);
	}

}
