package utilities;

import java.io.*;
import javax.sound.sampled.*;

public class Wav{
	
	public File save(byte[] data, String filename) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
		
	    InputStream byteArray = new ByteArrayInputStream(data);
	    
	    AudioInputStream ais = new AudioInputStream(byteArray, getAudioFormat(), (long) data.length);
	    
	    File theFile = new File(filename);
	    
	    AudioSystem.write(ais, AudioFileFormat.Type.WAVE, theFile);
	    
	    return theFile;
	    
	}

	private AudioFormat getAudioFormat() {
	    return new AudioFormat(
	            44100, // sampleRate
	            16, // sampleSizeInBits
	            1, // channels
	            true, // signed
	            false);      // bigEndian  
	}

	
}