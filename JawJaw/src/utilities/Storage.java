package utilities;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;

public class Storage {
	
	private double[] magnitudes;
	private double[] frequencies;
	private int frameSize;
	private Note[] pitchArray;
	private double[] maxFreq;
	private double binSize;
	private double expectedPhaseDifference;
	private String fileName;
	private static AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
	private static AudioFormat format = new AudioFormat(22050, 16, 1, true, true);
	
	
	public Storage(double[] magnitudes, double[] frequencies, int frameSize, Note[] pitchArray, double[] maxFreq, double binSize, double expectedPhaseDifference, String fileName){
		this.magnitudes = magnitudes;
		this.frequencies = frequencies;
		this.frameSize = frameSize;
		this.pitchArray = pitchArray;
		this.maxFreq = maxFreq;
		this.binSize = binSize;
		this.expectedPhaseDifference = expectedPhaseDifference;
		this.fileName = fileName;
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


	public int getFrameSize() {
		return frameSize;
	}


	public void setFrameSize(int frameSize) {
		this.frameSize = frameSize;
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


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public static AudioFileFormat.Type getFileType() {
		return fileType;
	}


	public static void setFileType(AudioFileFormat.Type fileType) {
		Storage.fileType = fileType;
	}


	public static AudioFormat getFormat() {
		return format;
	}


	public static void setFormat(AudioFormat format) {
		Storage.format = format;
	}
	
	

}
