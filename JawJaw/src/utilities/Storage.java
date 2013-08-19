package utilities;

public class Storage {
	
	private double[] magnitudes;
	private double[] frequencies;
	private int frameSize;
	private Note[] pitchArray;
	private double[] maxFrequencies;
	
	public Storage(double[] magnitudes, double[] frequencies, int frameSize, Note[] pitchArray){
		this.magnitudes = magnitudes;
		this.frequencies = frequencies;
		this.frameSize = frameSize;
		this.pitchArray = pitchArray;
	}
	
	
	public double[] getMaxFrequencies() {
		return maxFrequencies;
	}


	public void setMaxFrequencies(double[] maxFrequencies) {
		this.maxFrequencies = maxFrequencies;
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
	
	

}
