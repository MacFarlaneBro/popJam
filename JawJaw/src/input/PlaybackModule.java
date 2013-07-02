package input;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class PlaybackModule {
	
	private AudioFormat format;
	private DataLine.Info info;
	
	PlaybackModule(){
		this.info = new DataLine.Info(Clip.class, format);
		getLine();
	}
	
	private void getLine(){
		
		try{
			File audioFile = new File(System.getProperty("user.dir") + "/calm_down.wav");
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(audioFile);
			Clip clip = (Clip) AudioSystem.getLine(info);
			clip.open(audioIn);
			clip.start();
		} catch (UnsupportedAudioFileException ex){
			ex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

}
