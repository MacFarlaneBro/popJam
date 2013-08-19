package utilities;

public class Note {
	
	String pitch;
	double frequency;
	
	public Note(String pitch){
		
		this.pitch = pitch;
		
		Pitch frequencyRetrieval = new Pitch();
		
		this.frequency = frequencyRetrieval.getFrequency(pitch);
		
	}

	public String getPitch() {
		return pitch;
	}

	public void setPitch(String pitch) {
		this.pitch = pitch;
	}

	public double getFrequency() {
		return frequency;
	}

	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}

}
