package utilities;

import java.io.*;

import javax.sound.sampled.*;

public class Wav{
	
	
	public File byteToWav(byte[] data, String filename) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
		
	    InputStream byteArray = new ByteArrayInputStream(data);
	    
	    AudioInputStream ais = new AudioInputStream(byteArray, AudioData.FORMAT, (long) data.length);
	    
	    File theFile = new File(filename);
	    
	    AudioSystem.write(ais, AudioFileFormat.Type.WAVE, theFile);
	    
	    return theFile;
	    
	}
		
	public AudioData getSoundData(File inputFile){
		
		int sampleCount;// contains the number of samples in the wav file
		AudioInputStream audioInputStream;
		AudioData storage = null;
		int byteCount;
		
		try{
			//extract the byte data from the file
			audioInputStream = AudioSystem.getAudioInputStream(inputFile);
			//get number of samples file contains
			sampleCount = (int) audioInputStream.getFrameLength();		
			//determine number of bytes contained in WAV file
			byteCount = sampleCount * AudioData.FORMAT.getFrameSize();//format frame size in bytes, should be 2 as bit depth is 16
			//byte array to hold WAV file samples
			byte[] dataForDetection = new byte[byteCount];		
			//copy WAV byte data into dataForDetection
			audioInputStream.read(dataForDetection, 0, byteCount);
			//create new storage object AudioData
			storage = new AudioData(inputFile.getName(), dataForDetection, sampleCount);
							
		} catch (Exception ex){
			ex.printStackTrace();
		}
		
		return storage;
	}
}