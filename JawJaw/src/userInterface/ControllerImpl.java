package userInterface;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


import analysis.*;
import storage.AudioData;
import synth.AccompanimentGenerator;
import inputOutput.*;

public class ControllerImpl implements Controller {
	
	
	String holder;
	File newFile;
	
	
	public void record() throws IOException{
		
		String entry = "";
				
		System.out.println("You're ready to start recording! Enter the name of your track to begin: ");
		
		//getting the file name from the user
		String userEnteredName = getUserInput();
		newFile = new File(AudioData.AUDIO_FOLDER + userEnteredName + ".wav");
		
		//getting the tempo the metronome should play at from the user
		System.out.println("What tempo would you like to record at?");
		int tempoNum = Integer.parseInt(getUserInput());
		
		System.out.println("Finally, would you like a reference note to help tune? (Y/N)");
		holder = getUserInput();
		if(holder.equals("y")||holder.equals("Y"))
		{	
			System.out.println("Which note would you like to use as a reference?");
			holder = getUserInput();
			AccompanimentGenerator gen = new AccompanimentGenerator();
			gen.playPitch(holder);
		}

		//starting up the thread to record user input with the given metronome and key
		Runnable theRecorder = new RecordingModule(newFile, tempoNum);
		Thread recordingThread = new Thread(theRecorder);
		recordingThread.start();
		
		//stops recording on user input
		entry = getUserInput();
		if(entry!= null){
				RecordingModule stopper = (RecordingModule) theRecorder;
				stopper.getLine().close();
		}
		
		//prevents the program from progressing while the user is still recording
		while(recordingThread.isAlive()){}
	}
	
	public void play() throws IOException{
		
		String entry = "";

		while(!entry.equals("exit")){
			System.out.println("Please enter the name of the audio you would like to play (type exit to exit)");
			
			 entry = getUserInput();
			 if(entry.equals("exit")){
				 break;
			 } else {
				 try{
					Runnable playback = new PlaybackModule(entry + ".wav");
					Thread playbackThread = new Thread(playback);
					playbackThread.run();
				 } catch (RuntimeException ex){
					 ex.printStackTrace();
				 }
			 }
		}
	}

	
	public void generate(){
		
		System.out.println("Which track would you like to generate accompaniment for? ");
		
		newFile = new File(System.getProperty("user.dir") + "/audio/" + getUserInput() + ".wav");

		PostProcessing generator = new PostProcessing(newFile);

	}

	@Override
	public void melodyMaker() {
		
		AccompanimentGenerator synth = new AccompanimentGenerator();
		System.out.println("What speed would you like the metronome to be (bpm)? ");

		int bpm = Integer.parseInt(getUserInput());
		
		System.out.println("and what root note would you like to use? ");
		
		String root = getUserInput();
		
		System.out.println("finally what key type would you like the music to have? (minor, major or minor pentatonic (pentatonic)) ");
		
		String scaleType = getUserInput();
		
		synth.melodyGenerator(root, bpm, scaleType);
	}
	
	public String getUserInput(){
		
		String returnString = null;
		
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			returnString = bufferedReader.readLine();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return returnString;
	}
}

