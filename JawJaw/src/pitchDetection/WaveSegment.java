package pitchDetection;

public class WaveSegment {

	private int startSample;
	private int endSample;
	private float pitchFreq;
	private int maxHeight;

	/*
	 * The wave segment class is very simple in structure it simply demarcates the beginning, 
	 * end and highest point of each partial in the samples
	 * 
	 * @param start - the sample at which the wave starts
	 * @param end - the sample at which the wave ends
	 * @param averagedPitchFreq - the frequency which the wave represents
	 * @param modifiedSamples - the array of samples from the audio
	 */
	public WaveSegment(int start, int end, float averagedPitchFreq, int[] modifiedSamples) {
		
		this.startSample = start;
		this.endSample = end;
		this.pitchFreq = averagedPitchFreq;
		
		maxHeight = 1;
		
		for(int i = start; i <= end; i++){
			int sample = Math.abs(modifiedSamples[i]);
			if(sample > maxHeight){
				maxHeight = sample;
			}
		}
	}

	public int getStartSample() {
		return startSample;
	}

	public int getEndSample() {
		return endSample;
	}

	public float getPitchFreq() {
		return pitchFreq;
	}

}
