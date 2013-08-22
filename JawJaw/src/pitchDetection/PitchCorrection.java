package pitchDetection;

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
	private double[] compiler;
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
		for(int i = 0; i < 500; i++){
			System.out.println("inputfrequencies: " + inputFrequency[i]);
			System.out.println("inputMagnitudes: " + inputMagnitude[i]);
		}
		outputMagnitude = new double[inputMagnitude.length];
		outputFrequency = new double[inputFrequency.length];
		System.out.println("Max Frequency Array Length: " + maxFrequency.length);
		
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
							System.out.println("Bigger");

								distance = desiredFrequency[i]-maxFrequency[i];//calculating the proportion by which every value in the array will be shifted.
								double shiftValue = distance/maxFrequency[i];//shiftvalue is what every value in the replacement array must be multiplied by
								shiftValue = 1-shiftValue; 
								System.out.println("ShiftValue: " + shiftValue);
								shift(shiftValue);
								correctedFrames+= pitch.getFrameSize();
						} 
						else 
						{
							System.out.println("Smaller");
								distance = maxFrequency[i] - desiredFrequency[i];
								double shiftValue = distance/maxFrequency[i];
								shiftValue = 1+shiftValue; 
								System.out.println("ShiftValue: " + shiftValue);
								shift(shiftValue);
								correctedFrames+= pitch.getFrameSize();
						}
												
				}
		}
		
//		for(int i = 0; i < outputMagnitude.length; i++){
//			if(outputMagnitude[i] != 0){
//					System.out.println("Output Magnitude: " + outputMagnitude[i]);
//					System.out.println("Output Frequency: " + outputFrequency[i]);
//			}
//		}
		constructOutput();
	}
	
	private void constructOutput()
	{	
		fourierTarget = new double[outputMagnitude.length];
		System.out.println("Made it here!");
		System.out.println("outputMag: " + outputMagnitude.length);
		int a = 0;
		
			while(a < outputMagnitude.length){
				
				double[] outputPhase = new double[pitch.getFrameSize()];
								
//				System.out.println();

				for(int i = 0; i < pitch.getFrameSize(); i++)
				{		
					if(i+a >= outputMagnitude.length){
						break;
					}
						double tempMag = outputMagnitude[i+a];
						double tempFreq = outputFrequency[i+a];
						
						tempFreq -= (double) i * pitch.getBinSize();// subtract mid bin frequency
						
						tempFreq /= pitch.getBinSize(); //get bin deviation freq
						
						tempFreq = 2*Math.PI*tempFreq/32;//take into account oversampling (32 is oversampling amount)
						
						tempFreq += (double) i*pitch.getExpectedPhaseDifference();//readd overlap phase advance
						
						outputPhase[i] += tempFreq;//reconstruct bin phase
	
						compiler = new double[pitch.getFrameSize()*2];
						compiler[2*i] = (float) (tempMag* Math.cos(outputPhase[i]));
						compiler[2*i+1] = (float) (tempMag*Math.sin(outputPhase[i]));
				}
//				System.out.println(a);
				for(int i = 0; i < pitch.getFrameSize(); i++){
					if(a+i >= fourierTarget.length){
						break;
					}
						fourierTarget[a+i] = 
								compiler[i];
				}
				a+= pitch.getFrameSize();
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
		System.out.println("");
		System.out.println("Input Magnitude Length: " + inputMagnitude.length);
		
		for(int i = 0; i < pitch.getFrameSize()/2; i++){
			int index = (int) (i * shiftValue);
			if(index <= pitch.getFrameSize()){
				outputMagnitude[index] += inputMagnitude[correctedFrames];
				outputFrequency[index] = inputFrequency[correctedFrames] * shiftValue;
//				System.out.println("Output Magnitude: " + outputMagnitude[index]);
//				System.out.println("Output Frequency: " + outputFrequency[index]);
			}
			correctedFrames++;
		}		
	}
}
