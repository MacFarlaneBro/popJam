package utilities;

public class Storage {
	
	private double[] magnitudes;
	private double[] frequencies;
	private int frameSize;
	private String[] pitchArray;
	
	public Storage(double[] magnitudes, double[] frequencies, int frameSize, String[] pitchArray){
		this.magnitudes = magnitudes;
		this.frequencies = frequencies;
		this.frameSize = frameSize;
		this.pitchArray = pitchArray;
	}
	
	
	public String[] getPitchArray() {
		return pitchArray;
	}


	public void setPitchArray(String[] pitchArray) {
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
