package pitchDetection;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import pitchCorrection.PitchCorrection;


import edu.emory.mathcs.jtransforms.fft.*;

public class PitchDetection{
	
	private int sampleSize = 1024;
	private int sampleRate = 44100;
	private int oversamplingRate = 32;
	
	private float bytesPerSample = (float) sampleRate / (float) sampleSize;
	private	Pitch pitch = new Pitch();
	
	
	public void detect(byte[][] input){
		
		double[] realArray = new double[sampleSize*2];
		double[] magnitude = new double[sampleSize*2];
		double[] phase = new double[sampleSize*2];
		double[] trueFreq = new double[sampleSize*2];
		float[] pitches = new float[input.length];
		double[] frequency = new double[sampleSize*2];
		int counter = 0;
		float freq = 0;
		double maxTrueFreq = 0;
		
				
		while(counter!= input.length)
		{		
				try{

						for(int i = 0; i < sampleSize; i++)//The recorded byte values from the microphone are cast to doubles and stored in realArray
						{
								realArray[i] = (double) input[counter][i];
						}
						
						DoubleFFT_1D fft = new DoubleFFT_1D(sampleSize);
						fft.realForward(realArray);//using JTransform to perform fast fourier transform on the recorded double values (all real)
						int realer = 0;
						int imager = 1;
						
						double[] cos = new double[sampleSize];
						double[] sin = new double[sampleSize];
						
						for(int i = 0; i < sampleSize; i++){//separation of the real(cos) and imaginary(sin) parts of the realArray into separate arrays
							if(i % 2 == 1){
								sin[i] = realArray[i];
							} else {
								cos[i] = realArray[i];
							}
						}
				
						for(int i = 0; i < sampleSize; i++)//The magnitude is calculated for the complex numbers recorded by the array
						{
								double real = realArray[realer];
								double imag = realArray[imager];
								frequency[i] = (float) i * bytesPerSample;
								magnitude[i] = Math.sqrt((real*real)+(imag*imag));//sinusoid amplitude calculation
								phase[i] = Math.atan2(real, imag);//sinusoid phase calculation
								realer+=2;
								imager+=2;
								trueFreq[i] = bytesPerSample*(i+phase[i]*0.75/(2*Math.PI));//True frequency calculated using phase difference as bearing point, results have been coming out as two orders of magnitude too low, unsure why
						}


						if(counter ==100)
						{
								for(int i = 300; i < 321; i++)
								{	
									System.out.println("Bin Number: " + i);
									System.out.println("Bin Frequency [hz]: " + frequency[i]);
									System.out.println("Bin Magnitude: " + magnitude[i]);
									System.out.println("Bin Phase Difference: " + phase[i]);
									System.out.println("Estimated True Frequency [hz]: " + trueFreq[i]);
									
									System.out.println("");//What I am printing out here is the true frequencies and their magnitudes.
								}
								break;
						}
						

						double maxMag = 0;
						int maxIndex = 0;
						double maxPhase = 0;
						
						
						for(int i=0; i < sampleSize; i++)
						{
								if(magnitude[i] > maxMag){
										maxMag = magnitude[i];
										maxIndex = i;
										maxPhase = phase[i];
										maxTrueFreq = trueFreq[i];
								}
						}
						System.out.println("Magnitude: " + magnitude[54]);
						System.out.println("Max Magnitude: " + maxMag);
						System.out.println("maxIndex: " + maxIndex);
						freq = maxIndex*bytesPerSample;//This must be focused on to determine what part of it needs to come first
						System.out.println("Frequency: " + freq);
						System.out.println("True Frequency: " + maxTrueFreq);
						System.out.println("Phase: " + maxPhase);
						System.out.println(pitch.getPitch(freq));
						System.out.println("");

						
				
				} catch(Exception ex){
					ex.printStackTrace();
				}
				if(freq < 2000 && freq > 80.095){
					pitches[counter] = (float) maxTrueFreq;
				}
				System.out.println(counter);
				counter++;
			}
		for(int i = 0; i < pitches.length; i++){
			System.out.print(pitch.getPitch(pitches[i]) + ", ");
		}
		
		int loopStopper = 0;
		for(int i = 0; i < trueFreq.length; i++)//though ostensibly this is supposed to print out a list of all the true frequencies so far calculated, in reality it does nothing of the sort and simply prints out the size of the various bins
		{
			if(trueFreq[i] == 0)
			{
				loopStopper++;
			}
			if(loopStopper == 100)
			{//putting in a failsafe to stop the print-outs when there is no data left in the array
				break;
			}
				System.out.print(trueFreq[i] + ",");
		}
	}
	
	
	
	
	public byte[][] wavToByte(File newFile){
		byte[] audioBytes = null;
		
		ByteArrayOutputStream out = null; 
		BufferedInputStream in = null;
		
		try {
			in = new BufferedInputStream(new FileInputStream(newFile));
			out = new ByteArrayOutputStream();
			
			int read;
			byte[] intermediate = new byte[1024];
			
			//the input file is converted into a byte array
			while((read = in.read(intermediate)) > 0)
			{
				out.write(intermediate, 0, read);
			}
			
			audioBytes = out.toByteArray();
			in.close();
			out.close();
			
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex){
			ex.printStackTrace();
		}
		System.out.println(audioBytes.length);
		System.out.println(sampleSize);
		System.out.println(audioBytes.length/sampleSize);
		
		byte[] returner = new byte[];
		
		int j = 0;
		int i = 0;
		
		while(i < (audioBytes.length-sampleSize))//These two loops separate the single enormous output byte array from the wav file into a matrix of 1024 byte samples to allow for easier processing by the pitch detector
		{
				for(int n = 0; n < sampleSize; n++)
				{
						returner[j][n] = audioBytes[i];
						i++;
				}
				j++;
		}	
		return returner;
	}
	
	public double[] windowing(byte[][]){
		
	}

	
	public double mode(double[] modePitches)
	{
			Mode mode = new Mode();
			return mode.getMode(modePitches);
	}
	
	public void correct(float[] inputData, int offSet, int samplesToProcess){
		
			
		
	}
	
	
}
