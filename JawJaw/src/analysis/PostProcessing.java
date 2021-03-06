package analysis;

import inputOutput.WavHandler;

import java.io.File;

import accompaniment.SynthModule;
import storage.AudioData;
import storage.Note;
import storage.Pitch;
import storage.Scale;


public class PostProcessing {
	
	private File inputFile;//The input audio file selected to have accompaniment generated for it
	private File synthFile;//The accompaniment audio generated by the synth module
	
	private AudioData preDetect;//The data extracted from the input audio by WAV handler
	private AudioData postDetect;//The data from predetect, plus additional pitch information produced by Generator
	
	private Note[] pitches;//all notes, including nulls returned by the analysis
	private double[] noteTimer;//the length of time each note in averageNotes occurs for in the order in which they occur
	private Note[] postModeNotes;//every note (excluding nulls)
	
	private SynthModule synth;//The synthesizer used to generate the accompaniment
	private double[] pitchSampleGroup = new double[10];//the size of this array determines the number of non null pitches and therefore the time over which the average pitches is calculated
	private int nonNullPitches;//the total number of non null pitches
	private int arraySize;//the smaller average pitch is, the larger arraySize needs to be

	
	private Note[] finalGenerationNotes;//array holding the notes inc durations which will be generated by the synthModule
	private Scale scale;//the scale which the input audio most closely adheres to, used as framework for generating output


	public PostProcessing(File file){
		this.inputFile = file;
		WavHandler wav = new WavHandler();
		preDetect = wav.getSoundData(file);
		inputRefinement();
		temporalProcessing();
	}

	private void inputRefinement(){
		
		//raw data from the sound file is analysed to produce pitch data
		PitchDetection analyser = new PitchDetection(preDetect);
		postDetect = analyser.getData();
		pitches = postDetect.getPitchArray();
		
		//loops through the array of mixed null and non pitches, counting the number of non null pitches
		for(int j = 0; j < pitches.length; j++){
			if(pitches[j] != null){
				nonNullPitches++;
			}
		}
		
		System.out.println("Non Null Notes: " + nonNullPitches);
		Note[] nonNullPitchArray = new Note[nonNullPitches+1];
		
		nonNullPitches = 0;
		
		for(int j = 0; j < pitches.length; j++){
			if(pitches[j] != null){
				nonNullPitchArray[nonNullPitches] = pitches[j];
				nonNullPitches++;
			}
		}
		
		System.out.println("Non Null Notes: " + nonNullPitches);
		
		scale = new Scale();
		scale.setScale(nonNullPitchArray);

		arraySize = nonNullPitches/pitchSampleGroup.length;
		arraySize++;
		
		System.out.println("non-Null Pitches: " + nonNullPitches);
		System.out.println("number of pitches: " + pitches.length);
		System.out.println("ArraySize: " + arraySize);
		
		Mode mode = new Mode();
		
		int j = 0;
		int timeRecorder = 0;
		int a = 0; // a is the index for the noteTimer array
		
		noteTimer = new double[arraySize*2];
		postModeNotes = new Note[arraySize*2];
		
		System.out.print("Average Pitch List: ");
		
		for(int i = 0; i < pitches.length; i++){
			
			//if the current array index represents a pitch
			if(pitches[i] != null){
				
				//check to see if no pitches have been set yet, if so add the number of array index spots so far to timeRecorder (1 index spot = 1 sample = 1/22050 seconds)
				if(j == 0){
					timeRecorder = i;
				}
				//add the current pitch to 
				pitchSampleGroup[j] = (float) pitches[i].getFrequency();
				j++;
			}
			
			if(j == pitchSampleGroup.length-1){//if the pitchSampleArray is full
				Pitch pitch = new Pitch();
				System.out.print(pitch.getNote((mode.getMode(pitchSampleGroup))).getPitch() + ", ");
				
				postModeNotes[a] = pitch.getNote((mode.getMode(pitchSampleGroup)));//add the mode of the sample group to the post mode notes array
				
				noteTimer[a] = ((((float) i) - timeRecorder)/AudioData.SAMPLE_RATE);//dividing the number of samples the note occupies, as recorded in timeRecorder by the sample rate gives the time that note was present for
				j = 0;//resetting the mode sample array to zero, ready to be filled with new notes
				a++;
			}
			
		}
		
		System.out.println("");
		postDetect.setAveragePitches(postModeNotes);
		postDetect.setNoteTimes(noteTimer);
		postDetect.setNonNullPitches(nonNullPitchArray);
	}
	
	private void temporalProcessing(){
		
		System.out.println("Rate of non null results: " + pitches.length/nonNullPitches);
		System.out.println("Results under examination: " + postModeNotes.length);
		System.out.println("Pitch per Second: " + (float) (AudioData.SAMPLE_RATE/(pitches.length/pitchSampleGroup.length)));
		System.out.println("Audio Length: " + ((float) postDetect.getNumberOfSamples())/ ((float) AudioData.SAMPLE_RATE) + " seconds");
		
		//reusing the sample array from the previous methods
		pitchSampleGroup = new double[10];
		
		double timeRecorder = 0;
		double changeNoteTime = 0;
		Note changeNote = new Note("A2");//this is an arbitrary starting point, I'll need to change this
				
		Note finalNote;
		Note currentNote = postModeNotes[0];		
		finalGenerationNotes = new Note[noteTimer.length];
		
		int a = 0;//the index counter for final generation notes.
		
		for(int i = 0; i < noteTimer.length; i++){//calculation of time each note should be played for
			
			if(postModeNotes[i]==(null)){//end of viable notes
				break;
			}
			
			if(postModeNotes[i].getPitch().equals(currentNote.getPitch()))//if the note at i is the same as the currently selected note
			{	
				timeRecorder+= noteTimer[i];
			} 
			else if(postModeNotes[i].getPitch().equals(changeNote.getPitch()))
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
								
				changeNote = postModeNotes[i]; 
				changeNoteTime = noteTimer[i];
				timeRecorder += noteTimer[i];
			}	
			
		}
		System.out.println("adding new final Note: " + currentNote);
		finalNote = currentNote;
		finalNote.setTime(timeRecorder);
		
		finalGenerationNotes[a] = finalNote;
		
		int i = 0;
		System.out.println("");
		
		while(finalGenerationNotes[i]!= null){
			System.out.print("Pitch: " + finalGenerationNotes[i].getPitch() + ", ");
			System.out.println("Duration: " + finalGenerationNotes[i].getTime() + " seconds");
			i++;
		}
		
		synth = new SynthModule();

		int s = 0;
		int t = 0;
		
		for(int j = 0; i < postModeNotes.length; i++){
			if(postModeNotes[j] != null){
				s++;
			}
			if(finalGenerationNotes[j]!= null){
				t++;
			}
		}
		System.out.println("average notes: " + s);
		System.out.println("Final Generation Notes: " + t);
		System.out.println("Pitches: " + pitches.length);
		
		synthFile = synth.generateChords(scale, finalGenerationNotes, postDetect.getFileName());
		
		System.out.println("I got here!");
		
		WavHandler combiner = new WavHandler();
		combiner.wavCombiner(synthFile, inputFile);
	}


}
