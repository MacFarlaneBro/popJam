package accompaniment;

import java.io.*;
import java.util.ArrayList;

import storage.AudioData;
import storage.Note;
import storage.Pitch;
import storage.Scale;

import com.jsyn.*;
import com.jsyn.unitgen.*;
import com.jsyn.util.WaveRecorder;
import com.softsynth.shared.time.TimeStamp;

public class AccompanimentGenerator {
	
	private Synthesizer synth;
	private UnitOscillator osc;
	private UnitOscillator osc2;
	private UnitOscillator osc3;
	private LineOut lineOut;
	private LineOut lineOut3;
	private File synthFile;
	private WaveRecorder recorder;
	private LineOut lineOut2;
	private ArrayList<Note> finalNotes;
	private ArrayList<Note> rootedScale;
	private double[] highGenFreq;
	private double[] lowGenFreq;
	private String holder;
	
	public AccompanimentGenerator()
	{
		synth = JSyn.createSynthesizer();
		synth.add(osc = new SineOscillator());
		synth.add(osc2 = new TriangleOscillator());
		synth.add(lineOut = new LineOut());
		synth.add(lineOut2 = new LineOut());
		osc.output.connect(0, lineOut.input, 0);
		osc.output.connect(0, lineOut.input, 1);
		osc2.output.connect(0, lineOut2.input, 0);
		osc2.output.connect(0, lineOut2.input, 1);
	}
	
	public void playPitch(String pitchString){

			try {
				Pitch pitch = new Pitch();;
				
				synth.add(osc = new SineOscillator());
				synth.add(lineOut = new LineOut());
				
				osc.output.connect(0, lineOut.input, 0);
				osc.output.connect(0, lineOut.input, 1);
				osc.frequency.set(pitch.getFrequency(pitchString));
				osc.amplitude.set(0.2);
					
				synth.start();
				osc.start();
				lineOut.start();
					
				System.out.println("Press any key to stop the reference tone and begin recording.");
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				pitchString = in.readLine();
				
				lineOut.stop();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public void melodyGenerator(String root, double bpm, String scaleType){

		Scale scale = new Scale(new Note(root));
		scale.setScale(scaleType);
		
		synth.add(osc = new SineOscillator());
		synth.add(osc2 = new TriangleOscillator());
		synth.add(osc3 = new SquareOscillator());
		synth.add(lineOut = new LineOut());
		synth.add(lineOut2 = new LineOut());
		synth.add(lineOut3 = new LineOut());
				
		double sumTime = 60;
		double timeMultiplier = bpm/sumTime;//this generates the number i must be multiplied by to produce the appropriate bpm
				
		osc.output.connect(0, lineOut.getInput(), 0);
		osc.output.connect(0, lineOut.getInput(), 1);
		osc2.output.connect(0, lineOut2.getInput(), 0);
		osc2.output.connect(0, lineOut2.getInput(), 1);
		osc3.output.connect(0, lineOut3.getInput(), 0);
		osc3.output.connect(0, lineOut3.getInput(), 1);
		
		osc.amplitude.set(0.2);
		osc2.amplitude.set(0.2);
		osc3.amplitude.set(0.1);
		

		for(int i = 5; i < 1000; i++)
		{
			osc.frequency.set(scale.getLowNote().getFrequency(), i/timeMultiplier);
			osc2.frequency.set(scale.getMidNote().getFrequency(), i/timeMultiplier);
			osc3.frequency.set(scale.getHighNote().getFrequency(), i/timeMultiplier/2);
		}
		
		synth.start();
		osc.start();
		osc2.start();
		osc3.start();
		lineOut.start();
		lineOut2.start();
		lineOut3.start();
		
		try{
			System.out.print("Press any key to exit melody: ");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			holder = bufferedReader.readLine();
		} catch (IOException ex){
			ex.printStackTrace();
		}
		
		lineOut.stop();
		lineOut2.stop();
		lineOut3.stop();
		osc.stop();
		osc2.stop();
		osc3.stop();
		synth.stop();
	}
		

	private int[] getChordInfo(Note[] notes, Scale scale){
		
		Note root = scale.getRoot();
		
		int m = 0;
		Note[] inScale = new Note[notes.length];
		
		ArrayList<Note> scaleNotes = scale.getScale();
		
		for(int i = 0; i < notes.length; i++){//filling the array inScale with only the recorded notes found in the scale
			for(int j = 0; j < scaleNotes.size(); j++){
				if(notes[i]!=null){
					if(notes[i].getPitch().equals(scaleNotes.get(j).getPitch())){
						inScale[m] = notes[i];
						m++;
					}
				}
			}
		}
		
		m = 0;
		finalNotes = new ArrayList<Note>();
		finalNotes.add(inScale[0]);
		
		int i = 1;
		while(inScale[i]!= null){//this loop creates a list of in-order, in-scale input notes with the length of time they last
			if(inScale[i]
					.getPitch().equals(finalNotes.get(m).getPitch()))//if the current note is the same as the new Note
			{
				finalNotes.get(m).setTime(finalNotes.get(m).getTime() + inScale[i].getTime());//add the new note time to the current note time
			}
			else
			{
				finalNotes.add(inScale[i]);//add new notes to the list
				m++;
			}
			i++;
		}
		
		int[] distancesFromRoot = new int[finalNotes.size()];
		int l = 0;
		for(int j = 0; j < finalNotes.size(); j++){//this determines the individual note values in the context of the scale
			Note newNote = finalNotes.get(j);
			
			for(int k = 0; k < scaleNotes.size(); k++)
			{//finding the distance between the root and the current note
				if(scaleNotes.get(i).getPitch().equals(root.getPitch()))
				{
					while(!scaleNotes.get(i).getPitch().substring(1).equals(newNote.getPitch().substring(1)))//while newNote != root
					{
						l++;
					}
					break;
				}
			}
			
			distancesFromRoot[j] = l;
		}
		//finalNotes = the notes themselves and the length of times they play for
		//distances from root = the relative positions of the input notes in the scale
		
		return distancesFromRoot;
	}
	
	public File generateChords(Scale inScale, Note[] notes, String fileName) {
		
		double sumTime = 0;
		
		
		for(int i = 0; i < inScale.getScale().size(); i++){//these loops create a list of notes in the current scale where the first note is the root note of the scale, this is to simplify the note choosing process
			if(inScale.getScale().get(i).getPitch().equals(inScale.getRoot().getPitch())){
				rootedScale = new ArrayList<Note>();
				
				while(i < inScale.getScale().size()){
					rootedScale.add(inScale.getScale().get(i));
					i++;
				}
			}
		}
		
		
		int[] relativeDist = getChordInfo(notes, inScale);
		
		try 
		{	
			//removing the wav suffix before renaming the file
			for(int i = 0; i < fileName.length(); i++){
				if(fileName.charAt(i)=='.'){
					fileName = fileName.substring(0, i);
				}
			}
			synthFile = new File(AudioData.AUDIO_FOLDER + fileName + "synth.wav");
			recorder = new WaveRecorder(synth, synthFile);
			
			osc.output.connect(0, recorder.getInput(), 0);
			osc.output.connect(0, recorder.getInput(), 1);
			osc2.output.connect(0, recorder.getInput(), 0);
			osc2.output.connect(0, recorder.getInput(), 1);
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		highGenFreq  = new double[finalNotes.size()];
		lowGenFreq  = new double[finalNotes.size()];
		
		for(int i = 0; i < finalNotes.size(); i++){//This loop performs the low/high differential calculations
			int suffix;
			System.out.println("Final Note Times: " + finalNotes.get(i).getTime());
			if(finalNotes.get(i).getPitch().length() == 2){
				suffix = Integer.parseInt(finalNotes.get(i).getPitch().substring(1));	
			} else {
				suffix = Integer.parseInt(finalNotes.get(i).getPitch().substring(2));	
			}
			getChordNotes(i, relativeDist[i], suffix, inScale.getScaleType());
		}
		
		try {
			int i = 0;
			osc.frequency.set(notes[i].getFrequency(), synth.getCurrentTime());
			System.out.println(finalNotes.size());
			while(i != finalNotes.size())
			{	
				sumTime += finalNotes.get(i).getTime()+ synth.getCurrentTime();
				osc2.noteOff(new TimeStamp((int) sumTime));//at the moment casting the time to int fixes at one note per second
				osc.noteOff(new TimeStamp((int)sumTime));
				osc.noteOn(highGenFreq[i], 0.5, new TimeStamp((int)sumTime));
				osc2.noteOn(lowGenFreq[i], 0.5, new TimeStamp((int)sumTime));
				i++;
			}
			
			osc.noteOff(new TimeStamp(sumTime + 1));
			osc2.noteOff(new TimeStamp(sumTime + 1));
			
			System.out.println(sumTime);
			System.out.println("");
			System.out.println("press any key to play:");
			
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			holder = in.readLine();
			
			synth.start();
			osc.start();
			osc2.start();
			recorder.start();
			lineOut.start();
			lineOut2.start();

			try
			{
				double time = synth.getCurrentTime();
				// Sleep for a while, this seems to be the only way to allow the thing to play without stopping or requiring input
                synth.sleepUntil( time + sumTime + 1);
			} 
			catch( InterruptedException e )
			{
				e.printStackTrace();
			}
			
			lineOut.stop();
			recorder.stop();
			recorder.close();
			osc.stop();
			synth.stop();
			
		} catch (Exception ex){
			ex.printStackTrace();
		}
		
		return synthFile;
	}

	private void getChordNotes(int index, int i, int suffix, String scaleType) {
		
		//the two numerical suffixes for the high and low notes to be generated
		int highSuffix;
		int lowSuffix;
		
		Note highMod = new Note("N");//equivalent of null, returns 1.00
		Note lowMod = new Note("N");
		Note finalHigh;
		Note finalLow;
		
		double random = Math.random()*6;
		int noteChooser = (int) random;
		System.out.println("Note Chooser: " + noteChooser);
		
		//the following two if/else branches prevent the synthesized notes from being too near the input note
		if(suffix > 5){
			highSuffix = ((int) Math.random()*2)+4;
		} else {
			highSuffix = ((int) Math.random()*2)+5;
		}
		
		if(suffix < 3){
			lowSuffix = ((int) Math.random()*2+3);
		} else {
			lowSuffix = ((int) Math.random()*2+2);
		}
		
		
		//both minor and major triads are built using thirds and fifths so they can use the same constructor framework
		if(scaleType.equals("minor")||scaleType.equals("major")){
			if(i == 0){
					
				if(noteChooser == 0){// random number determines the other two notes generated
					highMod = rootedScale.get(2);
					lowMod = rootedScale.get(4);
				} else if(noteChooser == 1){
					lowMod = rootedScale.get(4);
					highMod = rootedScale.get(2);
				} else if(noteChooser == 2){
					highMod = rootedScale.get(3);
					lowMod = rootedScale.get(5);
				} else if(noteChooser == 3){
					highMod = rootedScale.get(5);
					lowMod = rootedScale.get(3);
				} else if(noteChooser == 4){
					highMod = rootedScale.get(5);
					lowMod = rootedScale.get(2);
				} else if(noteChooser == 5){
					highMod = rootedScale.get(2);
					lowMod = rootedScale.get(5);
				}
					
			}else if(i == 1){
				
				if(noteChooser == 0){// random number determines the other two notes generated
					highMod = rootedScale.get(3);
					lowMod = rootedScale.get(5);
				} else if(noteChooser == 1){
					lowMod = rootedScale.get(5);
					highMod = rootedScale.get(3);
				} else if(noteChooser == 2){
					highMod = rootedScale.get(4);
					lowMod = rootedScale.get(6);
				} else if(noteChooser == 3){
					highMod = rootedScale.get(6);
					lowMod = rootedScale.get(4);
				} else if(noteChooser == 4){
					highMod = rootedScale.get(3);
					lowMod = rootedScale.get(6);
				} else if(noteChooser == 5){
					highMod = rootedScale.get(6);
					lowMod = rootedScale.get(3);
				}
				
			}else if(i == 2){
				
				if(noteChooser == 0){// random number determines the other two notes generated
					highMod = rootedScale.get(4);
					lowMod = rootedScale.get(6);
				} else if(noteChooser == 1){
					lowMod = rootedScale.get(6);
					highMod = rootedScale.get(4);
				} else if(noteChooser == 2){
					highMod = rootedScale.get(0);
					lowMod = rootedScale.get(4);
				} else if(noteChooser == 3){
					highMod = rootedScale.get(4);
					lowMod = rootedScale.get(0);
				} else if(noteChooser == 4){
					highMod = rootedScale.get(5);
					lowMod = rootedScale.get(0);
				} else if(noteChooser == 5){
					highMod = rootedScale.get(0);
					lowMod = rootedScale.get(5);
				}
				
			}else if(i == 3){
				
				if(noteChooser == 0){// random number determines the other two notes generated
					highMod = rootedScale.get(5);
					lowMod = rootedScale.get(0);
				} else if(noteChooser == 1){
					lowMod = rootedScale.get(0);
					highMod = rootedScale.get(5);
				} else if(noteChooser == 2){
					highMod = rootedScale.get(1);
					lowMod = rootedScale.get(5);
				} else if(noteChooser == 3){
					highMod = rootedScale.get(5);
					lowMod = rootedScale.get(1);
				} else if(noteChooser == 4){
					highMod = rootedScale.get(1);
					lowMod = rootedScale.get(6);
				} else if(noteChooser == 5){
					highMod = rootedScale.get(6);
					lowMod = rootedScale.get(1);
				}
				
			}else if(i == 4){
				
				if(noteChooser == 0){// random number determines the other two notes generated
					highMod = rootedScale.get(6);
					lowMod = rootedScale.get(1);
				} else if(noteChooser == 1){
					lowMod = rootedScale.get(1);
					highMod = rootedScale.get(6);
				} else if(noteChooser == 2){
					highMod = rootedScale.get(6);
					lowMod = rootedScale.get(2);
				} else if(noteChooser == 3){
					highMod = rootedScale.get(2);
					lowMod = rootedScale.get(6);
				} else if(noteChooser == 4){
					highMod = rootedScale.get(0);
					lowMod = rootedScale.get(2);
				} else if(noteChooser == 5){
					highMod = rootedScale.get(2);
					lowMod = rootedScale.get(0);
				}
			}else if(i == 5){
				
				if(noteChooser == 0){// random number determines the other two notes generated
					highMod = rootedScale.get(0);
					lowMod = rootedScale.get(2);
				} else if(noteChooser == 1){
					lowMod = rootedScale.get(2);
					highMod = rootedScale.get(0);
				} else if(noteChooser == 2){
					highMod = rootedScale.get(3);
					lowMod = rootedScale.get(0);
				} else if(noteChooser == 3){
					highMod = rootedScale.get(0);
					lowMod = rootedScale.get(3);
				} else if(noteChooser == 4){
					highMod = rootedScale.get(1);
					lowMod = rootedScale.get(3);
				} else if(noteChooser == 5){
					highMod = rootedScale.get(1);
					lowMod = rootedScale.get(3);
				}
				
			}else if(i == 6){
				
				if(noteChooser == 0){// random number determines the other two notes generated
					highMod = rootedScale.get(1);
					lowMod = rootedScale.get(3);
				} else if(noteChooser == 1){
					lowMod = rootedScale.get(3);
					highMod = rootedScale.get(1);
				} else if(noteChooser == 2){
					highMod = rootedScale.get(4);
					lowMod = rootedScale.get(1);
				} else if(noteChooser == 3){
					highMod = rootedScale.get(1);
					lowMod = rootedScale.get(4);
				} else if(noteChooser == 4){
					highMod = rootedScale.get(4);
					lowMod = rootedScale.get(2);
				} else if(noteChooser == 5){
					highMod = rootedScale.get(2);
					lowMod = rootedScale.get(4);
				}
			}
						
			
		} else {// if the scale is pentatonic
			
			if(i == 0){
				
				if(noteChooser == 0){// random number determines the other two notes generated
					highMod = rootedScale.get(3);
					lowMod = rootedScale.get(5);
				} else if(noteChooser == 1){
					lowMod = rootedScale.get(4);
					highMod = rootedScale.get(2);
				} else if(noteChooser == 2){
					highMod = rootedScale.get(3);
					lowMod = rootedScale.get(5);
				} else if(noteChooser == 3){
					highMod = rootedScale.get(5);
					lowMod = rootedScale.get(3);
				} else if(noteChooser == 4){
					highMod = rootedScale.get(5);
					lowMod = rootedScale.get(2);
				} else if(noteChooser == 5){
					highMod = rootedScale.get(2);
					lowMod = rootedScale.get(5);
				}
				
				
		}else if(i == 1){
			
		}else if(i == 2){
			
		}else if(i == 3){
			
		}else if(i == 4){
			
		}else if(i == 5){
			
		}else if(i == 6){
			
		}
			
		}
		
		if(highMod.getPitch().length() ==3){//if the note is sharp take the first two characters, otherwise just the first character
			System.out.println("sharp" +highMod.getPitch().substring(0,2) + highSuffix);
			finalHigh =  new Note(highMod.getPitch().substring(0,2) + highSuffix);
		} else {
			System.out.println(highMod.getPitch().substring(0,1) + highSuffix);
			finalHigh =  new Note(highMod.getPitch().substring(0,1) + highSuffix);
		}
		highGenFreq[index] = finalHigh.getFrequency();
		
		if(lowMod.getPitch().length() ==3){
			System.out.println("sharp" +lowMod.getPitch().substring(0,2) + lowSuffix);
			finalLow =  new Note(lowMod.getPitch().substring(0,2) + lowSuffix);
		} else {
			System.out.println(lowMod.getPitch().substring(0,1) + lowSuffix);
			finalLow =  new Note(lowMod.getPitch().substring(0,1) + lowSuffix);
		}
		
		lowGenFreq[index] = finalLow.getFrequency();
		
		System.out.println("New Chord!");
	}
}
