package userInterface;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import pitchDetection.*;
import playback.PlaybackModule;
import utilities.AudioData;
import generator.Generator;
import generator.SynthModule;
import input.*;

public class ControllerImpl implements Controller {
	
	BufferedReader bufferedReader;
	String holder;
	File newFile;
	
	
	public void record() throws IOException{
		
		String entry = "";
				
		System.out.println("You're ready to start recording! Enter the name of your track to begin: ");
		
		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		String userEnteredName = bufferedReader.readLine();
		holder = userEnteredName;
		System.out.println("You're recording has begun, enter any character to finish");
		
		newFile = new File(System.getProperty("user.dir") + "/audio/" + holder + ".wav");
		
		Runnable theRecorder = new RecordingModule(newFile);
		Thread recordingThread = new Thread(theRecorder);
		recordingThread.start();
		
		entry = bufferedReader.readLine();
		
		if(entry!= null){
				RecordingModule stopper = (RecordingModule) theRecorder;
				stopper.getLine().close();
		}
		
		while(recordingThread.isAlive()){}
	}
	
	public void play() throws IOException{
		
		String entry = "";

		while(!entry.equals("exit")){
			System.out.println("Please enter the name of the audio you would like to play (type exit to exit)");
			
			 bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			 entry = bufferedReader.readLine();
			 
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

	@Override
	public void correct() throws IOException {
		
		System.out.println("Which track would you like to correct? ");
		
		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		String userEnteredName = bufferedReader.readLine();
		holder = userEnteredName;
		
		newFile = new File(System.getProperty("user.dir") + "/audio/" + holder + ".wav");
		
		PitchCorrection corrector = new PitchCorrection(newFile);
		corrector.correct();

		holder = null;
	}
	
	public void playNote() throws IOException{
	
		SynthModule synth = new SynthModule();
		
		synth.playPitch();

	}
	
	public void generate(){
		
		System.out.println("Which track would you like to generate accompaniment for? ");
		
		try{
			
		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		String userEnteredName = bufferedReader.readLine();
		holder = userEnteredName;
		
		newFile = new File(System.getProperty("user.dir") + "/audio/" + holder + ".wav");
		
		if(newFile.exists()){

		Generator generator = new Generator(newFile);
		generator.getOutputData();
		generator.getOutput();
		} else {
			System.out.println("I'm afraid the selected file could not be located.");
		}
		
		} catch (FileNotFoundException ex){
			System.out.println("I'm afraid the selected file could not be located.");
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		holder = null;
	}
}

