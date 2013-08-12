package pitchDetection;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays; 
import edu.emory.mathcs.jtransforms.fft.*;

public class PitchDetection{
	
	private int sampleSize = 8192;
	private int frameSize = 1024;
	private int sampleRate = 44100;
	private int oversamplingRate = 32;
	private float[] outData = new float[frameSize];
	int a = 0;
	
	private float bytesPerSample = (float) sampleRate / (float) sampleSize;
	private float binSize = (float) sampleRate / (float) frameSize;
	private	Pitch pitch = new Pitch();
	
	
	public void detect(double[] input, int numberOfSamples){
		
		float[] realArray = new float[sampleSize*2];//inFIFO
		float[] fourierTarget = new float[sampleSize*2];
		double[] prevPhase = new double[sampleSize];
		double[] frequencyArray = new double[sampleSize*2];
		double[] magnitudeArray = new double[sampleSize*2];
		int counter = 0;//gRover
		int stepSize = frameSize/oversamplingRate;
		int latency = frameSize - stepSize;
		double expectedPhaseDifference = 2*Math.PI* (double) stepSize/ (float) frameSize;//The average difference between phase values
		double window;
		double maxTrueFreq = 0;
		double maxMagnitude = 0;
		double[] sampleArray = new double[sampleSize];	
		
		if(counter == 0) counter = latency;
		
		for(int k = 0; k < numberOfSamples; k++)//input is not blank I have checked
		{		
				//
				realArray[counter] = (float) (input[counter]);
				outData[counter] = outData[counter-latency];
				counter++;

				System.out.println("Here1");
				System.out.println("counter: " + counter + ", numberOfSamples" + numberOfSamples);
				
						//once enough data is ready for processing
						if(counter>=frameSize)
						{
							System.out.println("Here2");

								counter = latency;
								
								//windowing and interleaving of imaginary and real numbers
								for(int i = 0; i < frameSize; i++)
								{
										window = -0.5*Math.cos(2*Math.PI* (double) i/(double) frameSize) + 0.5; /*check this method, it may work without the casting */
										fourierTarget[2*i] = (float) (realArray[i] * window);
										fourierTarget[2*i+1] = 0.f;
								}
								System.out.println("Here3");

								FloatFFT_1D transform = new FloatFFT_1D(frameSize);
								
								transform.complexForward(fourierTarget);
								//Now that the transform has been performed on the array, the analysis can begin
								
								

								for(int i = 0; i < frameSize; i++)//Analysis
								{		
									//System.out.println(i);
										//deinterlacing
										float real = fourierTarget[2*i];
										float imag = fourierTarget[2*i+1];
										
										double magnitude = 2*Math.sqrt(real*real + imag*imag);//calculate magnitude of sine
										double phase  = Math.atan2(imag, real);//calculate phase of sine
										
										//calculation of phase difference for real frequency
										double holder = phase - prevPhase[i];//variable holder used as storage for true frequency calculations
										prevPhase[i] = phase;
										
										//subtraction of expected phase difference
										holder -= (double) i*expectedPhaseDifference;
										
										int qpd = (int) (holder/Math.PI);
										if(qpd >= 0) qpd += qpd&1;//bitwise operator concatenates single bit
										else qpd -= qpd&1;
										holder -= Math.PI*(double) qpd;
										
										holder = oversamplingRate*holder/(2*Math.PI);
										
										//computer the true frequency of the current partial
										double trueFreq = (double) i*binSize + (holder * binSize);
										
										if(trueFreq!= 0) sampleArray[a] = trueFreq;
										
										//store the magnitude and frequency
										magnitudeArray[i] = (float) magnitude;
										frequencyArray[i] = (float) trueFreq;
								}
						}
		}
		
		for(int i=0; i < sampleSize; i++)
			{
			System.out.println(magnitudeArray[i]);
			System.out.println(frequencyArray[i]);
					if(magnitudeArray[i] > maxMagnitude){
							maxMagnitude = magnitudeArray[i];
							maxTrueFreq = frequencyArray[i];
					}
					System.out.print(sampleArray[i]);

			}
			System.out.println("Max Magnitude: " + maxMagnitude);
			System.out.println("True Frequency: " + maxTrueFreq);
			System.out.println("Pitch: " + pitch.getPitch((float)maxTrueFreq));
	}
								
								
//								int realer = 0;
//								int imager = 1;
//								for(int i = 0; i < sampleSize; i++)//The magnitude is calculated for the complex numbers recorded by the array
//								{
//										double real = fourierTarget[realer];
//										double imag = fourierTarget[imager];
//										magnitude[i] = Math.sqrt((real*real)+(imag*imag));//sinusoid amplitude calculation
//										phase[i] = Math.atan2(real, imag);//sinusoid phase calculation
//										realer+=2;
//										imager+=2;
//										trueFreq[i] = (sampleRate/sampleRate)*(i+phase[i]*4/(2*Math.PI))*100;//True frequency calculated using phase difference as bearing point, results have been coming out as two orders of magnitude too low, unsure why
//								}
//								
//								System.out.println("realNumber (cosine): " + realArray[54]);
//								System.out.println("imaginaryNumber (sine): " + realArray[55]);
//
//								double maxMag = 0;
//								int maxIndex = -1;
//								double maxPhase = 0;
//								
//								for(int i=0; i < sampleSize; i++)
//								{
//										if(magnitude[i] > maxMag){
//												maxMag = magnitude[i];
//												maxIndex = i;
//												maxPhase = phase[i];
//												maxTrueFreq = trueFreq[i];
//										}
//								}
//								System.out.println("Magnitude: " + magnitude[54]);
//								System.out.println("Max Magnitude: " + maxMag);
//								System.out.println("maxIndex: " + maxIndex);
//								freq = maxIndex*(sampleRate/sampleSize);//This must be focused on to determine what part of it needs to come first
//								System.out.println("Frequency: " + freq);
//								System.out.println("True Frequency: " + maxTrueFreq);
//								System.out.println("Phase: " + maxPhase);
//								System.out.println(pitch.getPitch(freq));
//								
//						if(freq < 2000 && freq > 80.095){
//							pitches[counter] = freq;
//						}
//						System.out.println(counter);
//						counter++;
//				}
//				
//				for(int i = 0; i < pitches.length; i++)
//				{
//						System.out.print(pitch.getPitch(pitches[i]) + ", ");
//				}
//				System.out.println("");
//				
//				double[] modePitches = new double[25]; 
//				
//				int j = 0;
//				for(int i = 0; i < trueFreq.length; i++){
//					modePitches[j] = trueFreq[i];
//					j++;
//					if(j % 25 == 0){
//						j = 0;
//						double averageFreq = mode(modePitches);
//						while(j!= 25){
//							trueFreq[i] = averageFreq;
//							j++;
//						}
//						j = 0;
//					}
//				}
//				
//				for(int i = 0; i < pitches.length; i++){
//					System.out.print(pitches[i] + ", ");
//				}
//						
//				//float finalFrequency = mode(pitches);
//				
//				//System.out.println("Frequency: " + finalFrequency);
//
//				//System.out.println("FinalPitch: " + pitch.getPitch(finalFrequency));
//				
//				//corrector.correct(modePitches);
//				
//	
//							if(counter ==100)
//							{
//									for(int i = 300; i < 321; i++)
//									{	
//										System.out.println("Bin Number: " + i);
//										System.out.println("Bin Frequency [hz]: " + frequency[i]);
//										System.out.println("Bin Magnitude: " + magnitude[i]);
//										System.out.println("Bin Phase Difference: " + phase[i]);
//										System.out.println("Estimated True Frequency [hz]: " + trueFreq[i]);
//										
//										System.out.println("");//What I am printing out here is the true frequencies and their magnitudes.
//									}
//									break;
//							}
//							
//	
//							double maxMag = 0;
//							int maxIndex = 0;
//							double maxPhase = 0;
//							
//							
//							for(int i=0; i < sampleSize; i++)
//							{
//									if(magnitude[i] > maxMag){
//											maxMag = magnitude[i];
//											maxIndex = i;
//											maxPhase = phase[i];
//											maxTrueFreq = trueFreq[i];
//									}
//							}
//							System.out.println("Magnitude: " + magnitude[54]);
//							System.out.println("Max Magnitude: " + maxMag);
//							System.out.println("maxIndex: " + maxIndex);
//							freq = maxIndex*bytesPerSample;//This must be focused on to determine what part of it needs to come first
//							System.out.println("Frequency: " + freq);
//							System.out.println("True Frequency: " + maxTrueFreq);
//							System.out.println("Phase: " + maxPhase);
//							System.out.println(pitch.getPitch(freq));
//							System.out.println("");
//	
//							
//					
//					
//					if(freq < 2000 && freq > 80.095){
//						pitches[counter] = (float) maxTrueFreq;
//					}
//					System.out.println(counter);
//					counter++;
//				}
//			for(int i = 0; i < pitches.length; i++){
//				System.out.print(pitch.getPitch(pitches[i]) + ", ");
//			}
//			
//			int loopStopper = 0;
//			for(int i = 0; i < trueFreq.length; i++)//though ostensibly this is supposed to print out a list of all the true frequencies so far calculated, in reality it does nothing of the sort and simply prints out the size of the various bins
//			{
//				if(trueFreq[i] == 0)
//				{
//					loopStopper++;
//				}
//				if(loopStopper == 100)
//				{//putting in a failsafe to stop the print-outs when there is no data left in the array
//					break;
//				}
//					System.out.print(trueFreq[i] + ",");

	
	
	
	
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
	
	public void correct(float[] inputData, int offSet, int samplesToProcess){
		
			
		
	}
	
	public void autoCorrelation(int size, float[] x){
	    float[] r = new float[size];
	    float sum;

	    for (int i=0;i<size;i++) {
	        sum=0;
	        for (int j=0;j<size-i;j++) {
	            sum+=x[j]*x[j+i];
	        }
	        r[i]=sum;
	    }
	}
	
	
}
