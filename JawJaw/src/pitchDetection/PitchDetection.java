package pitchDetection;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import edu.emory.mathcs.jtransforms.fft.*;

public class PitchDetection extends Thread {
	
	
	public void detect(byte[] inputArray){
		int sampleSize = inputArray.length;
		System.out.println(inputArray.length);
		double[] realArray = new double[sampleSize*2];
		double[] magnitude = new double[sampleSize*2];
		int freq = 0;
		Filters filter = new Filters();
		byte[] test = filter.lowPass(inputArray, 300);
				try{
						for(int i = 0; i < sampleSize; i++)//The recorded byte values from the microphone are cast to doubles and stored in realArray
						{
								realArray[i] = (double) test[i];
						}
						
						DoubleFFT_1D fft = new DoubleFFT_1D(sampleSize);
						double[] fourierArray = new double[realArray.length];
						int j = 0;
						for(int i = 0; i < realArray.length; i++){
							if(realArray[i]> 0){
								fourierArray[j] = realArray[i];
								j++;
							}
						}
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

						double maxMag = 0;
						int maxIndex = -1;
						int zeros = 0;
						
						for(int i=0; i < sampleSize; i++)
						{
							if(magnitude[i] == 0){
								zeros++;
							}
						
								if(magnitude[i] > maxMag){
										maxMag = magnitude[i];
										maxIndex = i;
								}
						}
						System.out.println("Max Magnitude: " + maxMag);
						System.out.println("maxIndex: " + maxIndex);
						freq = maxIndex*44100/sampleSize;
						System.out.println("Zero's: " + zeros);
				
				} catch(Exception ex){
					ex.printStackTrace();
				}
//				if(freq < 2000){
//					pitches[counter] = freq;
//				}

//		}
		//int finalFrequency = mode(pitches);
		
		Pitch pitch = new Pitch();
		
		System.out.println("Frequency: " + freq);

		System.out.println("FinalPitch: " + pitch.getPitch(freq));
		
//		return dataStorage;
	}
	
	public byte[] wavToByte(File newFile){
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
		
		return audioBytes;
	}

	
	public int mode(int[] pitches)
	{
			Mode mode = new Mode();
			return mode.getMode(pitches);
	}
	
	
}
