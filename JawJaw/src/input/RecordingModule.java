package input;

import java.io.ByteArrayOutputStream;

import javax.sound.sampled.*;


public class RecordingModule {
	
	TargetDataLine line = null;
	
	public void record(){
	
		AudioFormat format = new AudioFormat(8000.0f, 8, 1, true, true);

	    
	    DataLine.Info info = new DataLine.Info(TargetDataLine.class,
	            format); 

	    try {
	        line = (TargetDataLine) AudioSystem.getLine(info);
	        line.open(format);
	    } catch (LineUnavailableException ex) {
	        ex.printStackTrace();
	    }

	    line.start();
	    
	}
	
	public void printMicTrace(){
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
	    int numBytesRead;
	    byte[] data = new byte[line.getBufferSize() / 5];

	    while (true) {

	        numBytesRead = line.read(data, 0, data.length);
	        // Save this chunk of data.
	        out.write(data, 0, numBytesRead);
	        for(int i=0; i<numBytesRead; i+=1) {
	            System.out.println(Byte.toString(data[i]));

	        }
	        System.out.println();
	    }
	}
}
