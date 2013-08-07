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
	private int sampleRate = 44100;
	private	Pitch pitch = new Pitch();
	
	
	public void detect(byte[][] input){
		
		double[] realArray = new double[sampleSize*2];
		double[] magnitude = new double[sampleSize*2];
		double[] phase = new double[sampleSize*2];
		double[] trueFreq = new double[sampleSize*2];
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
								magnitude[i] = Math.sqrt((real*real)+(imag*imag));//sinusoid amplitude calculation
								phase[i] = Math.atan2(real, imag);//sinusoid phase calculation
								realer+=2;
								imager+=2;
								trueFreq[i] = (sampleRate/sampleRate)*(i+phase[i]*4/(2*Math.PI))*100;//True frequency calculated using phase difference as bearing point, results have been coming out as two orders of magnitude too low, unsure why
						}
						
						System.out.println("realNumber (cosine): " + realArray[54]);
						System.out.println("imaginaryNumber (sine): " + realArray[55]);

						double maxMag = 0;
						int maxIndex = -1;
						double maxPhase = 0;
						double maxTrueFreq = 0;
						
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
						freq = maxIndex*(sampleRate/sampleSize);//This must be focused on to determine what part of it needs to come first
						System.out.println("Frequency: " + freq);
						System.out.println("True Frequency: " + maxTrueFreq);
						System.out.println("Phase: " + maxPhase);
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
		System.out.println("");
		
		double[] modePitches = new double[25]; 
		
		int j = 0;
		for(int i = 0; i < trueFreq.length; i++){
			modePitches[j] = trueFreq[i];
			j++;
			if(j % 25 == 0){
				j = 0;
				double averageFreq = mode(modePitches);
				while(j!= 25){
					trueFreq[i] = averageFreq;
					j++;
				}
				j = 0;
			}
		}
		
		for(int i = 0; i < pitches.length; i++){
			System.out.print(pitches[i] + ", ");
		}
				
		//float finalFrequency = mode(pitches);
		
		//System.out.println("Frequency: " + finalFrequency);

		//System.out.println("FinalPitch: " + pitch.getPitch(finalFrequency));
		
		PitchCorrection corrector = new PitchCorrection();
		//corrector.correct(modePitches);
		
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
		
		byte[][] returner = new byte[(audioBytes.length/sampleSize)*4][sampleSize];//the index of the matrix is multiplied by 4 to account for the 75% window overlap implemented to account for smearing and accurate phase derivation calculation
		
		int j = 0;
		int i = 0;
		
		while(i < (audioBytes.length-sampleSize))//These two loops separate the single enormous output byte array from the wav file into a matrix of 1024 byte samples to allow for easier processing by the pitch detector
		{
				for(int n = 0; n < sampleSize; n++)
				{
						returner[j][n] = audioBytes[i];
						i++;
				}
				i = i-((sampleSize/4)*3);//creates a sample window overlap of 75%
				j++;
		}	
		return returner;
	}

	
	public double mode(double[] modePitches)
	{
			Mode mode = new Mode();
			return mode.getMode(modePitches);
	}
	
	
}
