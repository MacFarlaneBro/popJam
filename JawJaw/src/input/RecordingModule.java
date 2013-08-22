package input;

import java.io.*;

import javax.sound.sampled.*;

import utilities.AudioData;


//running the recording module as a thread allows the stopping of recording with no busywaiting
public class RecordingModule implements Runnable{
	
	private TargetDataLine line = null;
	private File newFile;
	private DataLine.Info info;
		
	//assigning the user named and generated file to be recorded to
	public RecordingModule(File newFile){	
		this.newFile = newFile;
	}
	
	public File start(){
		openLine();
		return readLine();
	}
	
	public Line getLine(){
		return line;
	}
	
	public void openLine(){
		 info = new DataLine.Info(TargetDataLine.class, AudioData.FORMAT);
	    if(!AudioSystem.isLineSupported(info)){
	    	//checks to see if the line is inaccessible, if so, handles the error
	    	System.out.println("I'm afraid the line is not supported, I don't know what to do in this scenario");
	    } else {
		    try{
		    	//finds the available microphone input line and opens it without beginning recording
		    	line = (TargetDataLine) AudioSystem.getLine(info);
		    	line.open(AudioData.FORMAT);

		    } catch (LineUnavailableException ex){
		    	System.out.println("I'm afraid the line is not available, I don't know what to do in this scenario");
		    }
	    }
	}
	
	public File readLine(){
		
		try{
			line.start();		
			System.out.println("Start recording");
			AudioInputStream ais = new AudioInputStream(line);
			
			//the user named file is written to
			AudioSystem.write(ais, AudioData.FILETYPE, newFile);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newFile;
	}
	


	@Override
	public void run() {
		start();	
	}
	
}
