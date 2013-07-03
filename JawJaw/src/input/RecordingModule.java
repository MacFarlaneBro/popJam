package input;

import java.io.ByteArrayOutputStream;
import javax.sound.sampled.*;


public class RecordingModule {
	
	private TargetDataLine line = null;
	private AudioFormat format = new AudioFormat(16000, 8, 2, true, true);
	private String trackName;
	
	public RecordingModule(String trackName){
		this.trackName = trackName;
		openLine();
		record(trackName);
	}
	
	public void openLine(){
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
	    if(!AudioSystem.isLineSupported(info)){
	    	//checks to sdsee if the line is inaccessible, if so, handles the error
	    } else {
		    try{
		    	line = (TargetDataLine) AudioSystem.getLine(info);
		    	line.open(format);
		    } catch (LineUnavailableException ex){
		    	ex.printStackTrace();
		    }
	    }
	}
	
	public void record(String trackName){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int numBytesRead;
		byte[] data = new byte[line.getBufferSize()/5];
		
		line.start();
		
		while(line.isActive()){//continually write out buffer contents to disk during recording
			System.out.println("Recording");
			numBytesRead = line.read(data, 0, data.length);
			out.write(data, 0, numBytesRead);
		}
	}
	
	
}
