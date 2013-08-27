package generator;


import java.io.File;

import pitchDetection.AnalysisImpl;

import utilities.AudioData;
import utilities.Mode;
import utilities.Note;
import utilities.Pitch;
import utilities.Wav;
import com.jsyn.*;

public class Generator {
	
	private File file;
	private AudioData preDetect;
	private AudioData postDetect;
	private Note[] pitches;
	private double[] noteTimer;
	private SynthModule synth;
	private double[] averagePitch = new double[20];//the size of this array determines the time over which average is calculated is collected
	private int arraySize;
	private Note[] finalGenerationNotes;
	private Note[] averageNotes;

	public Generator(File file){
		this.file = file;
		Wav wav = new Wav();
		preDetect = wav.getSoundData(file);
	}
	
	public void getOutputData(){
		
		//raw data from the sound file is analysed to produce pitch data
		AnalysisImpl analyser = new AnalysisImpl(preDetect);
		postDetect = analyser.getData();
		pitches = postDetect.getPitchArray();
		arraySize = pitches.length/1000;
		System.out.println("number of pitches: " + pitches.length);
		Mode mode = new Mode();
		int j = 0;
		
		int timeRecorder = 0;
		int a = 0; // a is the index incrementer for the noteTimer array
		noteTimer = new double[arraySize];
		
		System.out.print("Average Pitch List: ");
		averageNotes = new Note[arraySize];
		for(int i = 0; i < pitches.length; i++){
			if(pitches[i] != null){
				//System.out.println("Index no: " + i);
				if(j == 0){
					timeRecorder = i;
				}
				averagePitch[j] = (float) pitches[i].getFrequency();
				j++;
			}
			if(j == averagePitch.length-1){
				Pitch pitch = new Pitch();
				System.out.print(pitch.getNote((mode.getMode(averagePitch))).getPitch() + ", ");
				averageNotes[a] = pitch.getNote((mode.getMode(averagePitch)));
				noteTimer[a] =  ((((float) i) - timeRecorder)/AudioData.SAMPLE_RATE);
				//System.out.println(noteTimer[a]/AudioData.SAMPLE_RATE);
				j = 0;
				a++;
			}
			
		}
		postDetect.setAveragePitches(averagePitch);
		postDetect.setNoteTimes(noteTimer);
	}
	
	public void getOutput(){
		System.out.println("Rate of non null results: " + pitches.length/averagePitch.length);
		System.out.println("Pitch per Second: " + AudioData.SAMPLE_RATE/(pitches.length/averagePitch.length));
		System.out.println("Audio Length: " + ((float) postDetect.getSampleCount())/ ((float) AudioData.SAMPLE_RATE) + " seconds");
		
		double timeRecorder = 0;
		double changeNoteTime = 0;
		double timeTracker = 0;
		Note changeNote = new Note("A2");//this is an arbitrary starting point, I'll need to change this
		
		Pitch pitch = new Pitch();
		
		Note finalNote;
		Note currentNote = averageNotes[0];		

		finalGenerationNotes = new Note[noteTimer.length];
		int a = 0;//the index counter for final generation notes.
		
		for(int i = 0; i < noteTimer.length; i++){//calculation of time each note should be played for
			
			if(averageNotes[i]==(null)){
				break;
			}
			
			if(averageNotes[i].getPitch().equals(currentNote.getPitch()))
			{	
				timeRecorder+= noteTimer[i];
				//System.out.println("more of the same pitch: " + currentNote);	
			} 
			else if(averageNotes[i].getPitch().equals(changeNote.getPitch()))
			{
				changeNoteTime += noteTimer[i];
				timeRecorder += noteTimer[i];
				
				if(changeNoteTime > 0.2){//this number represents the cut off for a pitches representation
					System.out.println("adding new final Note: " + currentNote);
					System.out.println("final note time: " + timeRecorder);
					finalNote = currentNote;
					finalNote.setTime(timeRecorder);
					
					finalGenerationNotes[a] = finalNote;
					a++;
					
					timeRecorder = noteTimer[i];
					currentNote = changeNote;
					changeNote = new Note("A2");
					changeNoteTime = 0;
				}
			} else {
				
				System.out.println("newNote: " + changeNote);
				
				changeNote = averageNotes[i]; 
				changeNoteTime = noteTimer[i];
				timeRecorder += noteTimer[i];
			}	
			timeTracker += noteTimer[i];
			
//			System.out.println("Time Elapsed: " + timeTracker);
//			System.out.println("");
		}
		System.out.println("adding new final Note: " + currentNote);
		finalNote = currentNote;
		finalNote.setTime(timeRecorder);
		
		finalGenerationNotes[a] = finalNote;
		
		int i = 0;
		System.out.println("");
		while(finalGenerationNotes[i]!= null){
			System.out.println("Pitch: " + finalGenerationNotes[i].getPitch());
			System.out.println("Duration: " + finalGenerationNotes[i].getTime() + " seconds");
			i++;
		}
	}

}
