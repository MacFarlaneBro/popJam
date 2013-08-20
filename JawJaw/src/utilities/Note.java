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


	public double getFrequency() {
		return frequency;
	}
}
