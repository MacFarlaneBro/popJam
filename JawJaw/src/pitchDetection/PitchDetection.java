package pitchDetection;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import utilities.LowPassFilter;
import utilities.Mode;
import utilities.Note;
import utilities.Pitch;
import utilities.Storage;
import edu.emory.mathcs.jtransforms.fft.*;

public class PitchDetection{
	
	private int sampleSize = 8192;
	private int frameSize = 4096;
	private int sampleRate = 44100;
	private int oversamplingRate = 32;
	int a = 0;
	private float binSize = (float) sampleRate / (float) frameSize;
	private	Pitch pitch = new Pitch();
	private int indexHolder; //holds the index value for the maximum amplitude, allowing me to find the corresponding frequency
	
	
	public Storage detect(double[] input, int numberOfSamples, String fileName){
		

		int counter = 0;
		int stepSize = frameSize/oversamplingRate;
		int latency = frameSize - stepSize;
		double expectedPhaseDifference = 2*Math.PI* (double) stepSize/ (float) frameSize;//The average difference between phase values
		double[] window;
		double[] sampleArray = new double[sampleSize];	
		double[] maxFreq = new double[frameSize];
		double[] maxAmp = new double[frameSize];
		float[] fourierTarget = new float[frameSize*2];
		double[] prevPhase = new double[sampleSize];
		double[] frequencyArray = new double[sampleSize*2];
		double[] magnitudeArray = new double[sampleSize*2];
		Note[] pitchArray;

		
		if(counter == 0) counter = latency;
		int marker = 0;
		for(int k = 0; k < numberOfSamples; k++)
		{		
				LowPassFilter filter = new LowPassFilter();
				fourierTarget[marker] = 
						(float) filter.twoPointMovingAverageFilter(input[counter]);
				counter++;
				marker++;
						//read enough data to fill the FFT
						if(marker>=frameSize)
						{
								counter -= latency;
								System.out.println("Counter: " + counter);
								window = new double[frameSize];
								
								for(int i = 0; i < frameSize; i++)//loop builds Hann window
								{
										window[i] = 0.5 * (1- Math.cos((2 * Math.PI * i)/(frameSize-1)));
								}
								
								for(int i = 0; i < frameSize; i++)//this loop applies the Hann window function to the data
								{
										fourierTarget[i] *= window[i];
								}
								
								double[] complexTarget = new double[frameSize*2];

								DoubleFFT_1D transform = new DoubleFFT_1D(frameSize);
								
								for(int i = 0; i < frameSize; i++){
									complexTarget[2*i] = fourierTarget[i];
									complexTarget[2*i+1] = 0;
								}
								
								transform.complexForward(complexTarget);

								//Now that the transform has been performed on the array, the analysis can begin
								for(int i = 0; i < frameSize/2; i++)//Analysis
								{		
									//System.out.println(i);
										//deinterlacing
										double real = complexTarget[2*i];//odd numbers are real
										double imag = complexTarget[2*i+1];// even numbers are imaginary
										
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
								System.out.println(magnitudeArray[10]);
								System.out.println(frequencyArray[10]);
								
								maxAmp[a] = maxArray(magnitudeArray);//saving the maximum values from each FFT frame
								maxFreq[a] = frequencyArray[indexHolder];//saving the corresponding frequency
								System.out.println(maxFreq[a]);
								a++;
								marker = 0;
						}
		}
		
		pitchArray = new Note[a];
		
		for(int i = 0; maxFreq[i] != 0; i++)
		{
				System.out.println("Max Magnitude: " + maxAmp[i]);
				System.out.println("True Frequency: " + maxFreq[i]);
				if(pitch.getPitch(maxFreq[i])!= null){
				System.out.println("Pitch: " + pitch.getPitch(maxFreq[i]).getPitch());
				}
				pitchArray[i] = pitch.getPitch(maxFreq[i]);
		}
		
		System.out.println("Pitches Present: ");
		System.out.println(pitchArray.length);
		int i = 0;
		while(i!= a)
		{	
				if(pitch.getPitch(maxFreq[i])!= null)
				{
						System.out.print(pitchArray[i].getPitch() + ", ");
				}	
				i++;
		}
		
		Storage store = new Storage(magnitudeArray, frequencyArray, frameSize/2, pitchArray, maxFreq, binSize, expectedPhaseDifference, fileName);
		
		return store;
		
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
	
	public double maxArray(double[] input){
		double max = 0;
		for(int i = 0; i < input.length; i++)
		{
				if(input[i] > max)
				{
						max = input[i];
						indexHolder = i;
				}	
		}
		
		return max;	
	}	
}
