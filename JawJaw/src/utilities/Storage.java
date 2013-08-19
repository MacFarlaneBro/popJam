package utilities;

public class Storage {
	
	private double[] magnitudes;
	private double[] frequencies;
	private int frameSize;
	private Note[] pitchArray;
	private double[] maxFreq;
	
	public Storage(double[] magnitudes, double[] frequencies, int frameSize, Note[] pitchArray, double[] maxFreq){
		this.magnitudes = magnitudes;
		this.frequencies = frequencies;
		this.frameSize = frameSize;
		this.pitchArray = pitchArray;
		this.maxFreq = maxFreq;
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
