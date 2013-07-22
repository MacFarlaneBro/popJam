package pitchDetection;

import edu.emory.mathcs.jtransforms.fft.*;

public class PitchDetection extends Thread {
	
	private byte[][] dataStorage;
	
	public void detect(byte[] inputArray){
		int sampleSize = 4096;
		byte[] inputData = new byte[sampleSize*2];
		double[] realArray = new double[sampleSize*2];
		double[] magnitude = new double[sampleSize*2];
		int[] pitches = new int[100];
		int counter = 0;
		int freq = 0;
					
//		while(((line.read(inputData, 0, sampleSize))>0))
//		{
//			if(counter == 99){
//				break;
//			}
				
				try{
						for(int i = 0; i < sampleSize; i++)//The recorded byte values from the microphone are cast to doubles and stored in realArray
						{
								realArray[i] = (double) inputData[i];
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
						freq = maxIndex*44100/sampleSize;
						System.out.println("Frequency: " + freq);
						
				
				} catch(Exception ex){
					ex.printStackTrace();
				}
				if(freq < 2000){
					pitches[counter] = freq;
				}
				System.out.println(counter);
				counter++;
//		}
		int finalFrequency = mode(pitches);
		
		dataStorage[counter] = inputData;
		
		Pitch pitch = new Pitch();
		
		System.out.println("Frequency: " + finalFrequency);

		System.out.println("FinalPitch: " + pitch.getPitch(finalFrequency));
		
//		return dataStorage;
	}

	
	public int mode(int[] pitches)
	{
			Mode mode = new Mode();
			return mode.getMode(pitches);
	}
	
	
}
