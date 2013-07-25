package input;

import java.io.*;

import javax.sound.sampled.*;


//running the recording module as a thread allows the stopping of recording with no busywaiting
public class RecordingModule implements Runnable{
	
	private TargetDataLine line = null;
	private AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
	private AudioFormat format = new AudioFormat(44100, 16, 1, true, true);
	private File newFile;
	private DataLine.Info info;
		
	//assigning the user named and generated file to be recorded to
	public RecordingModule(File newFile){	
		this.newFile = newFile;
	}
	
	public void start(){
		openLine();
		readLine();
	}
	
	public Line getLine(){
		return line;
	}
	
	public void openLine(){
		 info = new DataLine.Info(TargetDataLine.class, format);
	    if(!AudioSystem.isLineSupported(info)){
	    	//checks to see if the line is inaccessible, if so, handles the error
	    	System.out.println("I'm afraid the line is not supported, I don't know what to do in this scenario");
	    } else {
		    try{
		    	//finds the available microphone input line and opens it without beginning recording
		    	line = (TargetDataLine) AudioSystem.getLine(info);
		    	line.open(format);

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
			
			System.out.println(ais);
			System.out.println(fileType);
			System.out.println(newFile);
			
			//the user named file is written to
			AudioSystem.write(ais, fileType, newFile);
			
			
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
