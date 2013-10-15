package inputOutput;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.sound.sampled.*;

public class Playback implements Runnable {
	
	private Clip theClip;
	private boolean fileExists = false;
	
    public Playback(String fileName) {
 
    	 try {
                File file = new File("audio/" + fileName);
                if (file.exists()) 
                {      	
                	fileExists = true;
                	System.out.println("found it!");
	                   
							theClip = AudioSystem.getClip();
						
	                    AudioInputStream stream = AudioSystem.getAudioInputStream(file.toURI().toURL());
	                    theClip.open(stream);
	                    
	                    
                }
                else {
                    System.out.println("I'm sorry, I was unable to find the following file " + fileName);
                }
                
		    } catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
           
    }
    
    public void play(){
    	theClip.start();
    }

    public void stop(){
        theClip.stop();
        theClip.setMicrosecondPosition(0);
    }
    
	@Override
	public void run() {
		if(fileExists){
			play();
		}
	}
}
