package utilities;

import java.util.ArrayList;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;

import pitchDetection.Analysis;
import pitchDetection.WaveSegment;

public class AudioData{
	
	public static final AudioFileFormat.Type FILETYPE = AudioFileFormat.Type.WAVE;
	public static final AudioFormat FORMAT = new AudioFormat(22050, 24, 1, true, true);
	public static final int SAMPLE_SIZE = 16000;
	public static final int WINDOW_SIZE = 1024; //The amount of data samples each FFT will use
	public static final int SAMPLE_RATE = 22050;
	public static final int OVERSAMPLING_RATE = 32; //Determines the level of overlap between FFT frames
	public static final int STEP_SIZE = WINDOW_SIZE/OVERSAMPLING_RATE;
	public static final double EXPECTED_PHASE_DIFFERENCE = 2*Math.PI* STEP_SIZE/WINDOW_SIZE;
	public static final float CORRECTION_RATE = 0.0003f;
	
	private Note[] pitchArray;//MIDI VALUE
	
	private float[] magnitudes;
	private float[] frequencies;
	private float[] maxFreq;
	private byte[] rawAudioData;
	
	private float binSize = (float) SAMPLE_SIZE / (float) WINDOW_SIZE;
	private float expectedPhaseDifference;
	private String fileName;
	private int sampleCount;
	private int[] waveWidth;
	private int maxAmplitude;//the maximum observed amplitude of the sample
	private int cutOff; //point at which amplitude is low enough to be considered silence
	private ArrayList<WaveSegment> waveSegments;
	private int[] modSamples;
	private double[] correctedPitch;
	private double[] averagePitches;
	private double[] noteTimes;
	
	
	public AudioData(String fileName, byte[] dataForDetection, int sampleCount){
		this.fileName = fileName;
		this.rawAudioData = dataForDetection;
		this.sampleCount = sampleCount;
		waveWidth =  new int[dataForDetection.length];
	}


	public double[] getDesiredFrequencies() {
		double[] maxFrequencies = new double[pitchArray.length];
		
		for(int i = 0; i < maxFrequencies.length; i++){
			maxFrequencies[i] = pitchArray[i].getFrequency();
		}
		
		return maxFrequencies;
	}
	
	public float[] getMaxFrequencies(){		
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

	public float[] getMagnitudes() {
		return magnitudes;
	}
	
	public void setMagnitudes(float[] magnitudes) {
		this.magnitudes = magnitudes;
	}
	
	public float[] getFrequencies() {
		return frequencies;
	}
	
	public void setFrequencies(float[] frequencies2) {
		this.frequencies = frequencies2;
	}

	public double getExpectedPhaseDifference() {
		return expectedPhaseDifference;
	}

	public void setExpectedPhaseDifference(float expectedPhaseDifference) {
		this.expectedPhaseDifference = expectedPhaseDifference;
	}

	public String getFileName() {
		return fileName;
	}

	public byte[] getRawAudioData() {
		return rawAudioData;
	}
	
	public int getSampleCount() {
		return sampleCount;
	}


	public void setSampleCount(int sampleCount) {
		this.sampleCount = sampleCount;
	}


	public void setMaxAmplitude(int max) {
		this.maxAmplitude = max;
	}


	public double getMaxAmplitude() {
		return maxAmplitude;
	}


	public void setCutOff(int cutOff) {
		
		this.cutOff = cutOff;
	}


	public void setPitches(Note[] pitches) {
		this.pitchArray = pitches;
		
	}

	public void setWaveSegments(ArrayList<WaveSegment> segments) {
		this.waveSegments = segments;	
	}
	
	public ArrayList<WaveSegment> getWaveSegments(){
		return waveSegments;
	}

	public void setModSamples(int[] modifiedSamples) {
		this.modSamples = modifiedSamples;
		
	}


	public int[] getModSamples() {
		return modSamples;
	}


	public void setCorrectedPitch(double[] correctedPitch) {
		this.correctedPitch = correctedPitch;
		
	}


	public void setAveragePitches(double[] averagePitch) {
		this.averagePitches = averagePitch;
		
	}


	public void setNoteTimes(double[] noteTimer) {
		this.noteTimes = noteTimer;
		
	}

}
