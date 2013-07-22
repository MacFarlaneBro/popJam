package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import input.*;

public class ControllerImpl implements Controller {
	
	BufferedReader bufferedReader;
	
	public void record() throws IOException{
		
		String entry = "";
		
		System.out.println("You're ready to start recording! Enter the name of your track to begin: ");
		
		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		String userEnteredName = bufferedReader.readLine();
		String holder = userEnteredName;
		System.out.println("You're recording has begun, enter any character to finish");
		
		Runnable theRecorder = new RecordingModule(holder);
		Thread recordingThread = new Thread(theRecorder);
		recordingThread.start();
		
		entry = bufferedReader.readLine();
		
		if(entry!= null){//Need to change this due to unsafe and deprecated method stop()
			recordingThread.interrupt();
		}
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

}
