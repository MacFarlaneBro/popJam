package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import pitchDetection.FileConverter;
import pitchDetection.PitchDetection;
import pitchDetection.PitchDetectionOriginal;
import playback.PlaybackModule;

import input.*;

public class ControllerImpl implements Controller {
	
	BufferedReader bufferedReader;
	String holder;
	String entry = "";
	File newFile;
	
	
	public void record() throws IOException{
				
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
		getPitch(newFile);
	}
	
	public void play() throws IOException{
				
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
		FileConverter converter = new FileConverter();
		double[] convertedData = converter.getSoundData(newFile);
		int numberOfSamples = converter.getNumberOfSamples();
		
		pitch.detect(convertedData, numberOfSamples);
	}

	@Override
	public void analyse() throws IOException {	
		
		Clip theClip;
		
		while(!entry.equals("exit")){
		System.out.println("Please enter the name of the audio you would like to analyse (type exit to exit)");
		
		 bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		 entry = bufferedReader.readLine();
		 
		 if(entry.equals("exit")){
			 break;
		 } else {
			 try{
				PitchDetectionOriginal pitch = new PitchDetectionOriginal();
				try {
	            	
	                File file = new File("audio/" + entry);
	                if (file.exists()) 
	                {      	
	                	
		                    theClip = AudioSystem.getClip();
		                    AudioInputStream stream = AudioSystem.getAudioInputStream(file.toURI().toURL());
		                    theClip.open(stream);
		    				pitch.detect(pitch.wavToByte(file));

	                }
	                else {
	                    throw new RuntimeException("I'm sorry, I was unable to find the file " + entry);
	                }
				
			 } catch (RuntimeException ex){
				 ex.printStackTrace();
			 } catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 } finally {
				 
			 }
		 }
		}
	}
}

