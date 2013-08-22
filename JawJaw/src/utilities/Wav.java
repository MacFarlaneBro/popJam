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
			
			AudioFormat audioFormat;
			int numberOfSamples;
			byte[] songData;
			double[] dataForDetection;
			AudioInputStream audioInputStream;
			AudioData storage = null;
			
			try{
				audioInputStream = AudioSystem.getAudioInputStream(inputFile);
				audioFormat = audioInputStream.getFormat();
				numberOfSamples = (int) audioInputStream.getFrameLength();
				
				int size = (int) (audioInputStream.getFrameLength() * audioFormat.getFrameSize());
				songData = new byte[size];
				
				DataInputStream dataInputStream = new DataInputStream(audioInputStream);
				
				dataInputStream.readFully(songData);	//The data from the audiofile is moved into the songData byteArray		
				
				ArrayConversion toShort = new ArrayConversion(songData, false);
				ArrayConversion toDouble = new ArrayConversion(toShort.getShortArray());
				
				dataForDetection = toDouble.getDoubleArray();
				
				storage = new AudioData(inputFile.getName(), dataForDetection, numberOfSamples);
				
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