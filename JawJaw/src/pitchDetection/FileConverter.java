package pitchDetection;

import java.io.DataInputStream;
import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import utilities.ArrayConversion;


public class FileConverter {
	
	AudioFormat audioFormat;
	int numberOfSamples;
	byte[] songData;
	double[] dataForDetection;
	private short[] shortArray;
	private byte[] byteArray;
	
	public double[] getSoundData(File inputFile){
		AudioInputStream audioInputStream;
		
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
			
			
		} catch (Exception ex){
			ex.printStackTrace();
		}
		
		return dataForDetection;
	}

	public AudioFormat getAudioFormat() {
		return audioFormat;
	}

	public int getNumberOfSamples() {
		return numberOfSamples;
	}

	public byte[] getSongData() {
		return songData;
	}

	public double[] getDataForDetection() {
		return dataForDetection;
	}

	public short[] getShortArray() {
		return shortArray;
	}

	public byte[] getByteArray() {
		return byteArray;
	}

	
}
