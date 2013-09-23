package inputOutput;

import java.io.*;
import com.jsyn.*;
import com.jsyn.data.FloatSample;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.VariableRateMonoReader;
import com.jsyn.unitgen.VariableRateStereoReader;
import com.jsyn.util.SampleLoader;
import com.jsyn.util.WaveRecorder;

import javax.sound.sampled.*;

import storage.AudioData;

public class WavHandler{
	
	private File combinedFile;
	
	public File byteToWav(byte[] data, String filename) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
		
	    InputStream byteArray = new ByteArrayInputStream(data);
	    
	    AudioInputStream ais = new AudioInputStream(byteArray, AudioData.FORMAT, (long) data.length);
	    
	    File theFile = new File(filename);
	    
	    AudioSystem.write(ais, AudioFileFormat.Type.WAVE, theFile);
	    
	    return theFile;
	    
	}
		
	public AudioData getSoundData(File inputFile){
			
			int numberOfSamples;
			AudioInputStream audioInputStream;
			AudioData storage = null;
			
			try{
				audioInputStream = AudioSystem.getAudioInputStream(inputFile);
				numberOfSamples = (int) audioInputStream.getFrameLength();

				
				int frameLength = (int) audioInputStream.getFrameLength();
				int byteCount = frameLength * AudioData.FORMAT.getFrameSize();
				int sampleCount = byteCount/2;
				byte[] dataForDetection = new byte[byteCount];
				
				audioInputStream.read(dataForDetection, 0, byteCount);
				
				storage = new AudioData(inputFile.getName(), dataForDetection, numberOfSamples, sampleCount);
								
			} catch (Exception ex){
				ex.printStackTrace();
			}
			
			return storage;

	}
	
	public File wavCombiner(File synthFile, File inputFile){
		
		Synthesizer synth = JSyn.createSynthesizer();
		LineOut lineOut;
		
		synth.add(lineOut = new LineOut());
		synth.start();
		FloatSample sample;
		FloatSample sample2;
		
		try {
			sample = SampleLoader.loadFloatSample(synthFile);
			sample2 = SampleLoader.loadFloatSample(inputFile);
		
			VariableRateStereoReader samplePlayer = new VariableRateStereoReader();
			VariableRateMonoReader samplePlayer2 = new VariableRateMonoReader();
			
			synth.add(samplePlayer);
			synth.add(samplePlayer2);
			
			samplePlayer.rate.set(sample.getFrameRate());
			samplePlayer2.rate.set(sample2.getFrameRate());
			
			samplePlayer2.dataQueue.queue(sample2);
			samplePlayer.dataQueue.queue(sample);
			
			samplePlayer.output.connect(0,lineOut.input, 0);
			samplePlayer2.output.connect(0,lineOut.input, 1);
			
			WaveRecorder recorder;
			String fileName = null;
	
			//removing the wav suffix before renaming the file
			for(int i = 0; i < inputFile.getName().length(); i++){
				if(inputFile.getName().charAt(i)=='.'){
					fileName = inputFile.getName().substring(0, i);
				}
			}
			
			synthFile = new File(AudioData.AUDIO_FOLDER + fileName + "C.wav");
			recorder = new WaveRecorder(synth, synthFile);
			
			samplePlayer.output.connect(0, recorder.getInput(), 0);
			samplePlayer.output.connect(0, recorder.getInput(), 1);
			
			//starting the input and accompaniment samples and the synth and recorder
			synth.startUnit(lineOut);
			samplePlayer.start();
			samplePlayer2.start();
			recorder.start();

			do
			{
				synth.sleepFor(1.0);
			} while(samplePlayer.dataQueue.hasMore());
			
			samplePlayer.stop();
			samplePlayer2.stop();
			recorder.stop();
			synth.stop();
			
		} catch (Exception ex){
			ex.printStackTrace();
		}
		return combinedFile;
	}

}