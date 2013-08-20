package utilities;

public class Storage {
	
	private double[] magnitudes;
	private double[] frequencies;
	private int frameSize;
	private Note[] pitchArray;
	private double[] maxFreq;
	private double binSize;
	private double expectedPhaseDifference;
	private String fileName;
	
	public Storage(double[] magnitudes, double[] frequencies, int frameSize, Note[] pitchArray, double[] maxFreq, double binSize, double expectedPhaseDifference, String fileName){
		this.magnitudes = magnitudes;
		this.frequencies = frequencies;
		this.frameSize = frameSize;
		this.pitchArray = pitchArray;
		this.maxFreq = maxFreq;
		this.binSize = binSize;
		this.setExpectedPhaseDifference(expectedPhaseDifference);
		this.setFileName(fileName);
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
	
	

}
