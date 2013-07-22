package input;

import java.io.*;

import javax.sound.sampled.*;

import pitchDetection.PitchDetection;


public class RecordingModule implements Runnable{
	
	private TargetDataLine line = null;
	private AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
	private AudioFormat format = new AudioFormat(16000, 8, 1, true, true);
	private File newFile;
	private DataLine.Info info;
		
	public RecordingModule(String trackName){
		newFile = new File(System.getProperty("user.dir") + "/audio/" + trackName + ".wav");
		
	}
	
	public void start(){
		openLine();
		readLine();
		pitchDetect(wavToByte(newFile));
	}
	
	public void openLine(){
		 info = new DataLine.Info(TargetDataLine.class, format);
	    if(!AudioSystem.isLineSupported(info)){
	    	//checks to see if the line is inaccessible, if so, handles the error
	    	System.out.println("I'm afraid the line is not available, I don't know what to do in this scenario");
	    } else {
		    try{
		    	line = (TargetDataLine) AudioSystem.getLine(info);
		    	line.open(format);

		    } catch (LineUnavailableException ex){
		    	ex.printStackTrace();
		    }
	    }
	}
	
	public void readLine(){
		
		try{
			line.start();
			
			System.out.println("Start recording");
			
			AudioInputStream ais = new AudioInputStream(line);
			
			System.out.println(ais);
			System.out.println(fileType);
			System.out.println(newFile);
			
			AudioSystem.write(ais, fileType, newFile);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	
	
	public void pitchDetect(byte[] audioDataArray){
		PitchDetection pitchInfo = new PitchDetection();
		pitchInfo.detect(audioDataArray);
	}

	@Override
	public void run() {
		start();	
	}
	
}
