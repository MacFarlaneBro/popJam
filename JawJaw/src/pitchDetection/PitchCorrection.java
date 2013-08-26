package pitchDetection;

import java.io.File;
import java.util.ArrayList;

import utilities.*;

public class PitchCorrection {
	
	private AudioData preDetect;
	private AudioData postDetect;
	private File theFile;
	private int[] modifiedSamples;
	private ArrayList<WaveSegment> segs;
	private Note[] pitches;
	private double[] correctedPitch;
	private double[] noteTimer;

	public PitchCorrection(File theFile){
		this.theFile = theFile;
		Wav wav = new Wav();
		preDetect = wav.getSoundData(theFile);
	}
	
	public void correct() {
		
		//raw data from the sound file is analysed to produce pitch data
		Analysis analyser = new Analysis(preDetect);
		postDetect = analyser.getData();
		pitches = postDetect.getPitchArray();
		System.out.println("number of pitches: " + pitches.length);
		Mode mode = new Mode();
		int j = 0;
		double[] averagePitch = new double[30];
		
		int timeRecorder = 0;
		int a = 0; // a is the index incrementer for the noteTimer array
		noteTimer = new double[averagePitch.length];
		
		System.out.print("Average Pitch List: ");
		for(int i = 0; i < pitches.length; i++){
			if(pitches[i] != null){
				//System.out.println("Index no: " + i);
				if(j == 0){
					timeRecorder = i;
				}
				averagePitch[j] = (double) pitches[i].getFrequency();
				j++;
			}
			if(j == averagePitch.length-1){
				Pitch pitch = new Pitch();
				System.out.print(pitch.getNote((mode.getMode(averagePitch))).getPitch() + ", ");
				noteTimer[a] =  ((float) i) - timeRecorder;
				System.out.println(noteTimer[a]/AudioData.SAMPLE_RATE);
				j = 0;
			}
			
		}
		postDetect.setAveragePitches(averagePitch);
		postDetect.setNoteTimes(noteTimer);
//		modifiedSamples = postDetect.getModSamples();
//		correctedPitch = new double[pitches.length];
//		getCorrectPitch();
	}

	private void getCorrectPitch() {
		
		//array to hold wave segment pitch values
		double[] segmentPitches = new double[modifiedSamples.length];
		
		for(int i = 0; i < segmentPitches.length; i++){
			segmentPitches[i] = -1;
		}
		
		segs = postDetect.getWaveSegments();
		
		for(int i = 0; i < segs.size(); i++)
		{
			WaveSegment segment = segs.get(i);
		
			for(int j = segment.getStartSample(); j <= segment.getEndSample(); j++)
			{
				segmentPitches[j] = segment.getPitchFreq();
			}
		}
		
		double currentPitch = pitches[0].getFrequency();
		correctedPitch[0] = currentPitch;
		
		double targetPitch = 0;
		
		for(int i = 1; i < modifiedSamples.length; i++){
			
			if(pitches[i].getFrequency() < 80){
				
				currentPitch = -1;
				targetPitch = -1;
				
			} else if(pitches[i-1].getFrequency() < 80){
				currentPitch = nearestPitch(segmentPitches[i]);
			} else if(Math.abs(segmentPitches[i-1] - segmentPitches[i]) > 1){
				currentPitch = nearestPitch(segmentPitches[i]);
			} else {
				currentPitch += pitches[i].getFrequency() - pitches[i-1].getFrequency();
				
				targetPitch = nearestPitch(segmentPitches[i]);
				
				currentPitch = targetPitch;// I'm removing a bunch of if/elses here which will likely make the pitch correction I'm introducing too severe but it seems more appropriate for the work I'm doing
			}
			
			correctedPitch[i] = currentPitch;
		}
		
		postDetect.setCorrectedPitch(correctedPitch);
		
		for(int i = 0; i < correctedPitch.length; i++){
			System.out.println(correctedPitch[i]);
		}
	}

	private double nearestPitch(double d) {
		if(d < 80){
			return -1;
		}
		
		int centeredPitch = (int) (d +0.5d);
		
		int scaleNumber = centeredPitch % 12;
		System.out.println("Centered Pitch :" + centeredPitch);
		
		if(scaleNumber > 0){	//I'm intentionally doing this wrong, thereby allowing only chromatic scale
			return (double) centeredPitch;
		}
		
		double toAbove = Math.abs(d - ((double) centeredPitch + 1));
		double toBelow = Math.abs(d - ((double) centeredPitch - 1));	
		
		if(toAbove < toBelow){
			return centeredPitch +1;
		} else {
			return centeredPitch -1;
		}
	}
}