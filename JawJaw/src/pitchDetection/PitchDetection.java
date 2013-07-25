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
	
	private int sampleSize = 4096;
	private	Pitch pitch = new Pitch();
	
	
	public void detect(byte[][] input){
		
		double[] realArray = new double[sampleSize*2];
		double[] magnitude = new double[sampleSize*2];
		float[] pitches = new float[input.length];
		int counter = 0;
		float freq = 0;
		
				
		while(counter!= input.length)
		{		
				try{
//						Filters filter = new Filters();
//						byte[] postFilter = filter.lowPass(input[counter], 0);
						for(int i = 0; i < sampleSize; i++)//The recorded byte values from the microphone are cast to doubles and stored in realArray
						{
								realArray[i] = (double) input[counter][i];
						}
						
						DoubleFFT_1D fft = new DoubleFFT_1D(sampleSize);
						fft.realForward(realArray);//using JTransform to perform fast fourier transform on the recorded double values (all real)
						int realer = 0;
						int imager = 1;
						for(int i = 0; i < sampleSize; i++)//The magnitude is calculated for the complex numbers recorded by the array
						{
								double real = realArray[realer];
								double imag = realArray[imager];
								magnitude[i] = Math.sqrt((real*real)+(imag*imag));
								realer+=2;
								imager+=2;
						}
						
						System.out.println("realNumber: " + realArray[54]);
						System.out.println("imaginaryNumber: " + realArray[55]);

						double maxMag = 0;
						int maxIndex = -1;
						
						for(int i=0; i < sampleSize; i++)
						{
								if(magnitude[i] > maxMag){
										maxMag = magnitude[i];
										maxIndex = i;
								}
						}
						System.out.println("Magnitude: " + magnitude[54]);
						System.out.println("Max Magnitude: " + maxMag);
						System.out.println("maxIndex: " + maxIndex);
						freq = maxIndex*(44100/sampleSize);//This must be focused on to determine what part of it needs to come first
						System.out.println("Frequency: " + freq);
						System.out.println(pitch.getPitch(freq));
						
				
				} catch(Exception ex){
					ex.printStackTrace();
				}
				if(freq < 2000 && freq > 80.095){
					pitches[counter] = freq;
				}
				System.out.println(counter);
				counter++;
		}
		
		for(int i = 0; i < pitches.length; i++)
		{
				System.out.print(pitch.getPitch(pitches[i]) + ", ");
		}
		
		PitchCorrection pitchCorrector = new PitchCorrection();
		pitchCorrector.correct(pitches);
		
		float finalFrequency = mode(pitches);
		
		System.out.println("Frequency: " + finalFrequency);

		System.out.println("FinalPitch: " + pitch.getPitch(finalFrequency));
		
	}
	
	
	
	
	public byte[][] wavToByte(File newFile){
		byte[] audioBytes = null;
		
		ByteArrayOutputStream out = null; 
		BufferedInputStream in = null;
		
		try {
			in = new BufferedInputStream(new FileInputStream(newFile));
			out = new ByteArrayOutputStream();
			
			int read;
			byte[] output = new byte[1024];
			while((read = in.read(output)) > 0)
			{
				out.write(output, 0, read);
			}
			out.flush();
			audioBytes = out.toByteArray();
			in.close();
			out.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ex){
			ex.printStackTrace();
		}
		System.out.println(audioBytes.length);
		System.out.println(sampleSize);
		System.out.println(audioBytes.length/sampleSize);
		
		byte[][] returner = new byte[(audioBytes.length/sampleSize)][sampleSize];
		
		int j = 0;
		int i = 0;
		
		while(i < (audioBytes.length-sampleSize))//These two loops separate the single enormous output byte array from the wav file into a matrix of 1024 samples to allow for easier processing by the pitch detector
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

	
	public float mode(float[] pitches)
	{
			Mode mode = new Mode();
			return mode.getMode(pitches);
	}
	
	
}
