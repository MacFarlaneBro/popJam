package input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Launcher {
	
	
	public static void main(String[] args) throws IOException{
		RecordingModule theRecorder = new RecordingModule("Track1");
		
		String entry = "";
		
//		while(!entry.equals("exit")){
//			System.out.println("Please enter the name of the audio you would like to play");
//			
//			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//			 entry = bufferedReader.readLine();
//			 
//			 PlaybackModule playback = new PlaybackModule(entry + ".wav");
//				playback.play();
//			
//		}
		
		
		//PlaybackModule playback1 = new PlaybackModule("fallen.wav");
		//playback1.play();
		//playback1.loop();
		System.out.println("Done");
		
		//theRecorder.record();
		//theRecorder.printMicTrace();
		
	}


}
