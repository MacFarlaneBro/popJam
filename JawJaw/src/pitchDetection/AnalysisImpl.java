package pitchDetection;

import java.util.ArrayList;
import java.util.List;

import utilities.AudioData;
import utilities.Mode;
import utilities.Note;
import utilities.Pitch;

public class AnalysisImpl implements Analysis{

	private AudioData audioData;
	private byte[] byteAudioData;
	private int bytesPerSample = AudioData.FORMAT.getFrameSize();
	private int bitsPerSample = AudioData.FORMAT.getFrameSize()*8;
	private int[] amplitudes;
	private int sampleCount;
	private int[] waveWidth;//marks the width at the start of each samples wave
	private float[] frequencies;
	private Note[] pitches;
	private ArrayList<WaveSegment> segments;
	
	public AnalysisImpl(AudioData audioData) {
			
		this.audioData = audioData;
		this.byteAudioData = audioData.getRawAudioData();
		this.sampleCount = audioData.getSampleCount();
		this.amplitudes = new int[sampleCount];
	}

	public AudioData getData() {
		
		for(int i = 0; i < sampleCount; i++){
			amplitudes[i] = bytesToSample(i);
		}
		
		waveWidth = new int[amplitudes.length];
		frequencies = new float[amplitudes.length];
		
		getMaxAmp();		
		findFrequencies();	
		findWaveSegments();
		
		audioData.setModSamples(amplitudes);
		
		return audioData;
	}
	
	private void findWaveSegments() {
		segments = new ArrayList<WaveSegment>();
		
		int start = -1;
		double pitchFreq = 0;
		double pitchFreqSum = 0;
		
		for(int i = 0; i < amplitudes.length; i++){
			if(pitches[i]!= null){
			if(frequencies[i] == -1 || start != -1 && Math.abs(pitches[i].getFrequency() - pitchFreq) > 81){//if the note is silent or lower than the minimum detectable
				if(start != -1){
					if(i-start > 1000){//1000 is an arbitrary value
						int width = i - start;
						float averagedPitchFreq = (float) ((pitchFreqSum/width)+0.5);
						WaveSegment segment = new WaveSegment(start, i-1, averagedPitchFreq, amplitudes);
						segments.add(segment);
					}
				}
				//reset the start
				start = -1;
			} else {
				if(start == -1){
					start = i;
					if(pitches[i]!= null){
						pitchFreq = pitches[i].getFrequency();
						pitchFreqSum = pitches[i].getFrequency();
					}
				} else {
					pitchFreqSum += pitches[i].getFrequency();
				}		
			}
			}
		}
		audioData.setWaveSegments(segments);
	}

	private void getMaxAmp() {
		
		int firstSample = amplitudes[0];
		int max = firstSample;
		int min = firstSample;
		
		for(int i = 0; i < amplitudes.length; i++){
			
			//amplitude is measured relative to the first sample
			int holder = amplitudes[i] - firstSample;
			
			if(holder > max){
				max = holder;
			}

			//negates holder to find minimum value
			if(-holder < min){
				min = holder;
			}
		}
		audioData.setMaxAmplitude(Math.max(max, -min));
		
		//The cutoff amplitude for a detected pitch is set to 1/10th of the max amplitude
		audioData.setCutOff((int)((double) audioData.getMaxAmplitude()* 0.1));	
	}
	
	/*
	 * @param index - an index number for the byteAudioData array
	 * converts each byte from the byteAudioData array to an integer
	 * inverts any negative values to represent magnitude rather than amplitude
	 * stores the results in the modifiedSamples array
	 */
	private int bytesToSample(int index){
		
		//index must be multiplied by bytes per sample to account for each value in the byte array only accounting for half a sample as 1 sample = 2 bytes
		//therefore only the second half really represents the magnitude
		index*= bytesPerSample;// index *2 
		
		//byte value converted to int
		int holder = (int) byteAudioData[index];
		
		if(holder < 0) holder = -holder;// if value is negative, negate it to represent magnitude rather than amplitude
		
		//records the next value in the byte array (e.g. the second half of the sample)
		int nextValue = (int) byteAudioData[index+1];

		//next value is multiplied by 256 as it represents the first 8 digits of a 2 byte number
		int returner = holder + (nextValue * 256);
		
		return returner;
		
	}
		
	private void findFrequencies() {
		int prevWidth = -1;
		
		for(int i = 0; i < amplitudes.length; i++){
							
				//determines width required to pass autocorrelation
			int width = prevWidth;			
			if(prevWidth < 30){
				
				width = AudioData.WINDOW_SIZE;
			} else {
				
				width = prevWidth * 3;//previous width value multiplied by 3
			}
			
			int result = autoCorrelation(i, width);
			
			int wavWidthHolder = prevWidth;
			
			if(result!= -1){//markFrequencyMethod
				
				wavWidthHolder = result - i;
				
				waveWidth[i] = wavWidthHolder;
				
				float freq = AudioData.SAMPLE_RATE/(float) wavWidthHolder;
				
				for(int j = i; j <= result; j++){
					frequencies[i] = freq;
				}
				
				i = result-1;
			}
			
		}
		
		//marking zero frequencies as silence
		for(int i = 0; i < amplitudes.length; i++){
			if(amplitudes[i] == 0){
				amplitudes[i] = -1;
			}
		}
		
		pitches = new Note[frequencies.length];
		int j = 0;
		for(int i = 0; i < amplitudes.length; i++){
			if(frequencies[i] > 0){
				Pitch newPitch = new Pitch();
				pitches[i] = newPitch.getNote(frequencies[i]);
			} else {
				pitches[i] = null;
			}
			if(pitches[i] != null){
				//System.out.println(pitches[i].getPitch());
				j++;
			}
		}
		System.out.println("Non Null Pitches: " + j);
		
		audioData.setFrequencies(frequencies);
		audioData.setPitches(pitches);
	}
	
	private int autoCorrelation(int index, int windowWidth) {//work on the science behind autocorrelation
		
		double max = -1;
		int indexAtMaximum = -1;
		
		if(index + windowWidth > amplitudes.length){
			return -1;
		}
		
		windowWidth = windowWidth/2;
		
		int end = index + windowWidth;
		
		for(int i = (index+20); i < end; i++){//find out why you add 20
			double difference = 0;
			for(int j = 0; j < windowWidth; j++){
				if(index+j < 0){
					break;
				}
				difference+= amplitudes[index+j] * amplitudes[i+j];
			}
			
			if(difference > max){
				max = difference;
				indexAtMaximum = i;
			}
		}
		
		return indexAtMaximum;
	}
	

}
