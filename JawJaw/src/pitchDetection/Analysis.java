package pitchDetection;

import utilities.AudioData;

public interface Analysis {
	
	/*
	 * Extracts all the frequency and pitch information from the
	 * selected audioFile loaded into the constructor
	 * 
	 * @returns AudioData - a storage file containing the wav metadata and 
	 */
	public AudioData getData();
	
	
	
	

}