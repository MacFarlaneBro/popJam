package playback;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.sound.sampled.*;

public class PlaybackModule implements Runnable {
	
	private Clip theClip;
	
    public PlaybackModule(String fileName) {
            try {
            	
                File file = new File("audio/" + fileName);
                if (file.exists()) 
                {      	
                	
	                    theClip = AudioSystem.getClip();
	                    AudioInputStream stream = AudioSystem.getAudioInputStream(file.toURI().toURL());
	                    theClip.open(stream);
                }
                else {
                    throw new RuntimeException("I'm sorry, I was unable to find the file " + fileName);
                }
            }
            catch (MalformedURLException e) {
                throw new RuntimeException("Sound: Malformed URL: " + e);
            }
            catch (UnsupportedAudioFileException e) {
                throw new RuntimeException("Sound: Unsupported Audio File: " + e);
            }
            catch (IOException e) {
                throw new RuntimeException("Sound: Input/Output Error: " + e);
            }
            catch (LineUnavailableException e) {
                throw new RuntimeException("Sound: Line Unavailable: " + e);
            } finally {
            	stop();
            }
    }
    
    public void play(){
        theClip.setFramePosition(0);  // Must always rewind!
        theClip.loop(0);
        theClip.start();
    }

    public void stop(){
        theClip.stop();
    }
    
	@Override
	public void run() {
		play();
	}
}
