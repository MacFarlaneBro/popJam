package pitchDetection;

import input.RecordingModule;

import java.io.*;
import javax.sound.sampled.*;
import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;
import utilities.*;

public class PitchCorrection {
	
	private double[] inputMagnitude;
	private double[] inputFrequency;
	private double[] outputMagnitude;
	private double[] outputFrequency;
	private double[] maxFrequency;
	private double[] desiredFrequency;
	private double[] fourierTarget;
	private int correctedFrames = 0;
	private Storage pitch;
	private double[] finalOutput;
	
	public File correct(Storage pitch) {
		
		this.pitch = pitch;

		double distance;//the distance in Hz between the desired frequency and the current frequency
		
		inputFrequency = pitch.getFrequencies();// the real frequencies of the entire sample
		inputMagnitude = pitch.getMagnitudes();// the real magnitudes of the entire sample
		maxFrequency = pitch.getMaxFrequencies();// the max frequencies of the entire sample
		
		System.out.println(maxFrequency.length);
		
		Note[] holder = pitch.getPitchArray();
		
		desiredFrequency = new double[pitch.getPitchArray().length];
		for(int i = 0; i < desiredFrequency.length; i++)
		{		
				if(holder[i]!= null){
				desiredFrequency[i] = holder[i].getFrequency();
				} else desiredFrequency[i] = 0.0;

		}
		
		for(int i = 0; i < desiredFrequency.length; i++)//This loop calculates the disparity between actual pitch and desired pitch
		{	
				
				if(desiredFrequency[i]!= 0.0)
				{

						
						if(desiredFrequency[i] < maxFrequency[i])
						{
								distance = desiredFrequency[i]-maxFrequency[i];//calculating the proportion by which every value in the array will be shifted.
								double shiftValue = distance/maxFrequency[i];//shiftvalue is what every value in the replacement array must be multiplied by
								shiftValue = 1+shiftValue; 
								System.out.println("ShiftValue: " + shiftValue);
								shift(shiftValue);
						} 
						else 
						{
								distance = maxFrequency[i] - desiredFrequency[i];
								double shiftValue = distance/maxFrequency[i];
								shiftValue = 1+shiftValue; 
								System.out.println("ShiftValue: " + shiftValue);
								shift(shiftValue);
						}
												
				}
		}
		
		constructOutput();
		
		
		return null;
	}
	
	private void constructOutput()
	{
		int a = 0;
		
			while(a!= outputMagnitude.length){
				
				double[] outputPhase = new double[pitch.getFrameSize()];
				
				for(int i = 0; i < pitch.getFrameSize(); i++)
				{		
					
						double tempMag = outputMagnitude[i+a];
						double tempFreq = outputFrequency[i+a];
						
						
						tempFreq -= (double) i * pitch.getBinSize();// subtract mid bin frequency
						
						tempFreq /= pitch.getBinSize(); //get bin deviation freq
						
						tempFreq = 2*Math.PI*tempFreq/32;//take into account oversampling (32 is oversampling amount)
						
						tempFreq += (double) i*pitch.getExpectedPhaseDifference();//readd overlap phase advance
						
						outputPhase[i] += tempFreq;//reconstruct bin phase
						
						fourierTarget = new double[outputMagnitude.length];
						
						fourierTarget[2*i] = (float) (tempMag* Math.cos(outputPhase[i]));
						fourierTarget[2*i+1] = (float) (tempMag*Math.sin(outputPhase[i]));
				}
				
				a+= pitch.getFrameSize();
			}
		
		//zeroing negative frequencies
		for(int i = pitch.getFrameSize(); i< 2*pitch.getFrameSize(); i++){
			fourierTarget[i] = 0;
			}
		
		DoubleFFT_1D transform = new DoubleFFT_1D(pitch.getFrameSize()*2);
		transform.complexInverse(fourierTarget, false);//inverse fourier transform calculation
		
		finalOutput = new double[fourierTarget.length];
		
		for(int i = 0; i < pitch.getFrameSize()*2; i++){
			double window = -0.5*Math.cos(2*Math.PI*(double)i/(double)pitch.getFrameSize()*2) +0.5;//dewindowning
			finalOutput[i] = 2*window*fourierTarget[2*i]/(pitch.getFrameSize()*32);
		}
		
		byte[] converter = new byte[finalOutput.length];
		
		for(int i = 0; i < converter.length; i++){
			converter[i] = (byte) finalOutput[i];//double array reconverted to byte format in preparation for wav conversion
		}
		
		
		
		File newFile = new File(pitch.getFileName() + "corrected");//new file created
		
		Runnable theRecorder = new RecordingModule(newFile);
		Thread recordingThread = new Thread(theRecorder);
		recordingThread.start();
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));;
		String entry = null;
		try {
			entry = bufferedReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(entry!= null){
				RecordingModule stopper = (RecordingModule) theRecorder;
				stopper.getLine().close();
		}
		
		while(recordingThread.isAlive()){}
	}

	public void shift(double shiftValue){
		
		outputMagnitude = new double[inputMagnitude.length];
		outputFrequency = new double[inputFrequency.length];
		
		for(int i = 0; i < pitch.getFrameSize(); i++){
			int index = (int) (correctedFrames * shiftValue);
			if(index <= pitch.getFrameSize()){
				outputMagnitude[index] += 
						inputMagnitude[correctedFrames];
				outputFrequency[index] = inputFrequency[correctedFrames] * shiftValue;
			}
		}
		
	}

	
	

}
