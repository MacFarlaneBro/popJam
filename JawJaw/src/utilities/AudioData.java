package utilities;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;

public class AudioData {
	
	public static final AudioFileFormat.Type FILETYPE = AudioFileFormat.Type.WAVE;
	public static final AudioFormat FORMAT = new AudioFormat(22050, 16, 1, true, true);
	public static final int SAMPLE_SIZE = 8096;
	public static final int FRAME_SIZE = 4096;
	public static final int SAMPLE_RATE = 22050;
	public static final int OVERSAMPLING_RATE = 32;
	
	private double[] magnitudes;
	private double[] frequencies;
	private Note[] pitchArray;
	private double[] maxFreq;
	private double expectedPhaseDifference;
	private String fileName;
	private double[] rawAudioData;
	private int numberOfSamples;
	private float binSize = (float) SAMPLE_SIZE / (float) FRAME_SIZE;
	
	public AudioData(String fileName, double[] rawAudioData, int numberOfSamples){
		this.fileName = fileName;
		this.setRawAudioData(rawAudioData);
		this.setNumberOfSamples(numberOfSamples);
	}
	
	
	public double[] getDesiredFrequencies() {
		double[] maxFrequencies = new double[pitchArray.length];
		
		for(int i = 0; i < maxFrequencies.length; i++){
			maxFrequencies[i] = pitchArray[i].getFrequency();
		}
		
		return maxFrequencies;
	}
	
	public double[] getMaxFrequencies(){
		
		return maxFreq;
		
	}
	
	public double getBinSize(){
		return binSize;
	}


	public Note[] getPitchArray() {
		return pitchArray;
	}


	public void setPitchArray(Note[] pitchArray) {
		this.pitchArray = pitchArray;
	}


	public double[] getMagnitudes() {
		return magnitudes;
	}
	public void setMagnitudes(double[] magnitudes) {
		this.magnitudes = magnitudes;
	}
	public double[] getFrequencies() {
		return frequencies;
	}
	public void setFrequencies(double[] frequencies) {
		this.frequencies = frequencies;
	}


	public double getExpectedPhaseDifference() {
		return expectedPhaseDifference;
	}


	public void setExpectedPhaseDifference(double expectedPhaseDifference) {
		this.expectedPhaseDifference = expectedPhaseDifference;
	}


	public String getFileName() {
		return fileName;
	}


	public double[] getRawAudioData() {
		return rawAudioData;
	}


	public void setRawAudioData(double[] rawAudioData) {
		this.rawAudioData = rawAudioData;
	}


	public int getNumberOfSamples() {
		return numberOfSamples;
	}


	public void setNumberOfSamples(int numberOfSamples) {
		this.numberOfSamples = numberOfSamples;
	}


}
