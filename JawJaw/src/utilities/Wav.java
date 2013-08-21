package utilities;

import java.io.*;
import javax.sound.sampled.*;

public class Wav{
	
	public static void save(byte[] data, String filename) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
		
	    InputStream byteArray = new ByteArrayInputStream(data);
	    
	    AudioInputStream ais = new AudioInputStream(byteArray, getAudioFormat(), (long) data.length);
	    
	    AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new File(filename));
	}

	private static AudioFormat getAudioFormat() {
	    return new AudioFormat(
	            441000, // sampleRate
	            16, // sampleSizeInBits
	            1, // channels
	            true, // signed
	            false);      // bigEndian  
	}

	
}