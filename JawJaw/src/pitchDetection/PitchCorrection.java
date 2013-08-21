package pitchDetection;

import input.RecordingModule;

import java.io.*;

import com.sun.media.sound.WaveFileWriter;
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
	
	public void correct(Storage pitch) {
		System.out.println("calculating pitch");
		this.pitch = pitch;

		double distance;//the distance in Hz between the desired frequency and the current frequency
		
		inputFrequency = pitch.getFrequencies();// the real frequencies of the entire sample
		inputMagnitude = pitch.getMagnitudes();// the real magnitudes of the entire sample
		maxFrequency = pitch.getMaxFrequencies();// the max frequencies of the entire sample
		outputMagnitude = new double[inputMagnitude.length];
		outputFrequency = new double[inputFrequency.length];
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
								correctedFrames+= pitch.getFrameSize();
						} 
						else 
						{
								distance = maxFrequency[i] - desiredFrequency[i];
								double shiftValue = distance/maxFrequency[i];
								shiftValue = 1+shiftValue; 
								System.out.println("ShiftValue: " + shiftValue);
								shift(shiftValue);
								correctedFrames+= pitch.getFrameSize();
						}
												
				}
		}
		for(int i = 0; i < outputMagnitude.length; i++){
			if(outputMagnitude[i]!= 0){
				System.out.println(outputMagnitude[i]);
			}
		}
		constructOutput();
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
		
			for(int i = 0; i < fourierTarget.length; i++){
				if(fourierTarget[i]!= 0){
					System.out.println(fourierTarget[i]);
				}
			}	
		
		//zeroing negative frequencies
		for(int i = pitch.getFrameSize(); i< 2*pitch.getFrameSize(); i++){
			fourierTarget[i] = 0;
		}
		
		DoubleFFT_1D transform = new DoubleFFT_1D(pitch.getFrameSize()*2);
		transform.complexInverse(fourierTarget, false);//inverse fourier transform calculation
		
		finalOutput = new double[fourierTarget.length];
		
		for(int i = 0; i < pitch.getFrameSize()*2; i++){
			double window =  0.5 * (1- Math.cos((2 * Math.PI * i)/(pitch.getFrameSize()-1)));;//windowing function may well work better now
			finalOutput[i] = 2*window*fourierTarget[2*i]/(pitch.getFrameSize()*32);
		}
		
		int zero = 0;
		int nonZero = 0;
		for(int i = 0; i < fourierTarget.length; i++){
				if(fourierTarget[i]==0){
					zero++;
				} else {
					nonZero++;
				}
		}
		System.out.println("InputArraySize: " + inputMagnitude.length);
		System.out.println("Zero: " + zero);
		System.out.println("non Zero: " + nonZero);
		System.out.println("maxFrequencies: " + maxFrequency.length);
		
		byte[] converter = new byte[finalOutput.length];
		
		for(int i = 0; i < converter.length; i++){
			converter[i] = (byte) finalOutput[i];//double array reconverted to byte format in preparation for wav conversion
		}
		
		
		String fileName = System.getProperty("user.dir") + "/audio/" + pitch.getFileName() + "corrected.wav";
		
		Wav wav = new Wav();
		
		try {
			wav.save(converter, fileName);
		} catch (IOException | LineUnavailableException
				| UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	

	public void shift(double shiftValue){

		System.out.println("shifting");
		System.out.println(pitch.getFrameSize());
		System.out.println(inputMagnitude.length);
		
		for(int i = 0; i < pitch.getFrameSize()/2; i++){
			System.out.println(i);
			System.out.println(correctedFrames);
			int index = (int) (i * shiftValue);
			if(index <= pitch.getFrameSize()/2){
				outputMagnitude[index] += inputMagnitude[correctedFrames];
				outputFrequency[index] = inputFrequency[correctedFrames] * shiftValue;
			}
			correctedFrames++;
		}
		correctedFrames-= correctedFrames*(1-(1/32));
		
	}

	
	

}
