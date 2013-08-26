package pitchDetection;

import java.util.ArrayList;
import java.util.List;

import utilities.AudioData;
import utilities.Mode;
import utilities.Note;
import utilities.Pitch;

public class Analysis {

	private AudioData audioData;
	private byte[] byteAudioData;
	private int bytesPerSample = 2;
	private int bitsPerSample = 16;
	private int[] modifiedSamples;
	private int sampleCount;
	private int[] waveWidth;//marks the width at the start of each samples wave
	private float[] frequencies;
	private Note[] pitches;
	private ArrayList<WaveSegment> segments;
	
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
		
		getMaxAmp();
		
		findFrequencies();
		
		findWaveSegments();
		
		audioData.setModSamples(modifiedSamples);
		
		return audioData;
	}
	
	private void findWaveSegments() {
		segments = new ArrayList<WaveSegment>();
		
		int start = -1;
		double pitchFreq = 0;
		double pitchFreqSum = 0;
		
		for(int i = 0; i < modifiedSamples.length; i++){
			if(pitches[i]!= null){
			if(frequencies[i] == -1 || start != -1 && Math.abs(pitches[i].getFrequency() - pitchFreq) > 81){//if the note is silent or lower than the minimum detectable
				if(start != -1){
					if(i-start > 1000){//1000 is an arbitrary value
						int width = i - start;
						float averagedPitchFreq = (float) ((pitchFreqSum/width)+0.5);
						WaveSegment segment = new WaveSegment(start, i-1, averagedPitchFreq, modifiedSamples);
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
		int firstSample = modifiedSamples[0];
		
		int max = firstSample;
		int min = firstSample;
		
		for(int i = 0; i < modifiedSamples.length; i++){
			int holder = 0; 
			holder = modifiedSamples[i] - firstSample;
			
			if(holder > max){
				max = holder;
			}
			
			if(-holder < min){// I don't know why holder needs to be negative, I need to find that out
				min = holder;
			}
			
		}
		audioData.setMaxAmplitude(Math.max(max, -min));
		
		audioData.setCutOff((int)((double) audioData.getMaxAmplitude()* 0.1));	
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
		
	private void findFrequencies() {//I have modified this method slightly to make it more efficient, so if the program doesn't work, this is the first place to look
		int prevWidth = -1;
		
		for(int i = 0; i < modifiedSamples.length; i++){
							
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
		for(int i = 0; i < modifiedSamples.length; i++){
			if(modifiedSamples[i] == 0){
				modifiedSamples[i] = -1;
			}
		}
		
		pitches = new Note[frequencies.length];
		int j = 0;
		for(int i = 0; i < modifiedSamples.length; i++){
			if(frequencies[i] > 0){
				Pitch newPitch = new Pitch();
				pitches[i] = newPitch.getNote(frequencies[i]);
			} else {
				pitches[i] = null;
			}
			if(pitches[i] != null){
				System.out.println(pitches[i].getPitch());
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
		
		if(index + windowWidth > modifiedSamples.length){
			return -1;
		}
		
		windowWidth = windowWidth/2;
		
		int end = index + windowWidth;
		
		for(int i = (index+20); i < end; i++){//find out why you add 20
			double difference = 0;
			System.out.println(i);
			for(int j = 0; j < windowWidth; j++){
				if(index+j < 0){
					break;
				}
				difference+= modifiedSamples[index+j] * modifiedSamples[i+j];
			}
			
			if(difference > max){
				max = difference;
				indexAtMaximum = i;
			}
		}
		
		return indexAtMaximum;
	}
	

}
