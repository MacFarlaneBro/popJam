package utilities;

import java.io.*;

import javax.sound.sampled.*;

public class Wav{
	
	
	public File byteToWav(byte[] data, String filename) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
		
	    InputStream byteArray = new ByteArrayInputStream(data);
	    
	    AudioInputStream ais = new AudioInputStream(byteArray, getAudioFormat(), (long) data.length);
	    
	    File theFile = new File(filename);
	    
	    AudioSystem.write(ais, AudioFileFormat.Type.WAVE, theFile);
	    
	    return theFile;
	    
	}
		
		public AudioData getSoundData(File inputFile){
			
			int numberOfSamples;
			AudioInputStream audioInputStream;
			AudioData storage = null;
			
			try{
				audioInputStream = AudioSystem.getAudioInputStream(inputFile);
				numberOfSamples = (int) audioInputStream.getFrameLength();

				
				int frameLength = (int) audioInputStream.getFrameLength();
				int byteCount = frameLength * AudioData.FORMAT.getFrameSize();
				int sampleCount = byteCount/2;
				byte[] dataForDetection = new byte[byteCount];
				
				audioInputStream.read(dataForDetection, 0, byteCount);
				
				storage = new AudioData(inputFile.getName(), dataForDetection, numberOfSamples, sampleCount);
								
			} catch (Exception ex){
				ex.printStackTrace();
			}
			
			return storage;

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