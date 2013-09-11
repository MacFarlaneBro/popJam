package storage;

public class Note {
	
	private String pitch;
	private double frequency;
	private double time;
	
	public Note(String pitch){
		
		this.pitch = pitch;
		
		Pitch frequencyRetrieval = new Pitch();
		
		this.frequency = frequencyRetrieval.getFrequency(pitch);
		
	}
	
	@Override
	public String toString(){
		return pitch;
	}

	public String getPitch() {
		return pitch;
	}
	
	public void setTime(double time){
		this.time = time;
	}

	public double getFrequency() {
		return frequency;
	}

	public double getTime() {
		return time;
	}
}
