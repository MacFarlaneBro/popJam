package input;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.sound.sampled.*;

public class PlaybackModule {
	
	private Clip theClip;
	
    public PlaybackModule(String fileName) {
            try {
                File file = new File(fileName);
                if (file.exists()) {
                    theClip = AudioSystem.getClip();
                    AudioInputStream stream = AudioSystem.getAudioInputStream(file.toURI().toURL());
                    theClip.open(stream);
                }
                else {
                    throw new RuntimeException("Sound: file not found: " + fileName);
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
            }
    }
    public void play(){
        theClip.setFramePosition(0);  // Must always rewind!
        theClip.loop(0);
        theClip.start();
        while(theClip.isActive()){
        	//This loop keeps the java file running while the audio is still playing
        	System.out.println("Playing");
        }
    }
    public void loop(){
        theClip.loop(3);
        while(theClip.isActive()){
        	//This loop keeps the java file running while the audio is still looping
        	System.out.println("Looping");
        }
    }
    public void stop(){
        theClip.stop();
    }
}
