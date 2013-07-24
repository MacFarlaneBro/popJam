package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import pitchDetection.PitchDetection;
import playback.PlaybackModule;

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
		//getPitch(newFile);
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
	
	public void getPitch(File newFile){
		PitchDetection pitch = new PitchDetection();
//		pitch.detect(pitch.wavToByte(newFile));
	}

}