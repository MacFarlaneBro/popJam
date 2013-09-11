package analysis;


import storage.AudioData;
import storage.Note;
import storage.Pitch;

public class Analysis {

	private AudioData audioData;
	private byte[] byteAudioData;
	private int bytesPerSample = AudioData.FORMAT.getFrameSize();
	private int[] modifiedSamples;
	private int sampleCount;
	private int[] waveWidth;//marks the width at the start of each samples wave
	private float[] frequencies;
	private Note[] pitches;
	
	public Analysis(AudioData audioData) {
			
		this.audioData = audioData;
		this.byteAudioData = audioData.getRawAudioData();
		this.sampleCount = audioData.getSampleCount();
		this.modifiedSamples = new int[sampleCount];
	}

	public AudioData getData() {
		
		for(int i = 0; i < sampleCount; i++){
			modifiedSamples[i] = bytesToSample(i);
		}
		waveWidth = new int[modifiedSamples.length];
		frequencies = new float[modifiedSamples.length];
		
		findFrequencies();
				
		audioData.setModSamples(modifiedSamples);
		
		return audioData;
	}

	private int bytesToSample(int index){

		index*= bytesPerSample;// index *2 
		
		if(index >= byteAudioData.length){
			return 0;
		}
		int holder = (int) byteAudioData[index];
		
		if(holder <0) holder = -holder;
		
		int nextValue = (int) byteAudioData[index+1];
		
		int returner = holder + (nextValue * 256);
		
		return returner;
		
	}
		
	private void findFrequencies() {
		
		for(int i = 0; i < modifiedSamples.length; i++){		
			
			int width = AudioData.WINDOW_SIZE;
			
			int result = autoCorrelation(i, width);
			
			if(result!= -1){//markFrequencyMethod
				
				//the autocorrelated max result minus the index starting point gives the width of the significant partial
				int wavWidthHolder = result - i;
				waveWidth[i] = wavWidthHolder;
				
				//the sample rate divided by the size of the partial wavelength gives the frequency of the the partial
				float freq = AudioData.SAMPLE_RATE/(float) wavWidthHolder;
				for(int j = i; j <= result; j++){
					frequencies[i] = freq;
				}
				//the loop skips ahead to the significant value returned by autocorrelation
				//result -1 to compensate for the adding of one at the end of the loop
				i = result-1;
			}
		}
		
		//marking zero frequencies as silence
		for(int i = 0; i < modifiedSamples.length; i++){
			if(modifiedSamples[i] == 0){
				modifiedSamples[i] = -1;
			}
		}
		
		pitches = new Note[frequencies.length];
		
		for(int i = 0; i < modifiedSamples.length; i++){
			if(frequencies[i] > 0){
				Pitch newPitch = new Pitch();
				pitches[i] = newPitch.getNote(frequencies[i]);
			} else {
				pitches[i] = null;
			}
			if(pitches[i] != null){
				//System.out.println(pitches[i].getPitch());
			}
		}
		
		audioData.setFrequencies(frequencies);
		audioData.setPitches(pitches);
	}
	
	private int autoCorrelation(int index, int windowWidth) {

		double max = -1;
		int indexAtMaximum = -1;
		
		if(index + windowWidth > modifiedSamples.length){
			return -1;
		}
		
		windowWidth = windowWidth/2;
		
		//the window is assigned as between i and i + windowWidth
		int end = index + windowWidth;
		
		for(int i = (index+20); i < end; i++){//value added to index dramatically reduces analysis time
			
			double difference = 0;
			
			//the sum of every value in the window (-20) multiplied by every other value in the window
			for(int j = 0; j < windowWidth; j++){
				difference+= modifiedSamples[index+j] * //index + j cycles through every value in the current window
						modifiedSamples[i+j]; //i + j cycles through a window sized range of values starting at index + i
			}
			
			//the largest multiplication of the magnitudes is recorded
			//along with the point in the window that it occurred
			if(difference > max){
				max = difference;
				indexAtMaximum = i;
			}
		}//the index within the window that produced the largest result is returned
		return indexAtMaximum;
	}

}
