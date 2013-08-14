package utilities;

public class Storage {
	
	private double[] magnitudes;
	private double[] frequencies;
	
	public Storage(double[] magnitudes, double[] frequencies){
		this.magnitudes = magnitudes;
		this.frequencies = frequencies;
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
