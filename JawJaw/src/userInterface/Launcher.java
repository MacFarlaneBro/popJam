package userInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Launcher {
	
	public static void main(String[] args) throws IOException{
				
		String entry = "";	
				
		Controller theControl = new Controller();
		
		while(!entry.equals("exit")){
		entry = "";	
		System.out.println("Would you like to record new audio (r), play back a track? (p), generate accompaniment for a track (g) or listen to a sample melody (m) (type exit to exit)");
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		entry = bufferedReader.readLine();
		
		if(entry.equals("r"))
		{
				theControl.record();			
		} 
		else if(entry.equals("p"))
		{	
				theControl.play();		
		}
		else if(entry.equals("g"))
		{
				theControl.generate();
		}
		else if(entry.equals("m"))
		{
			theControl.melodyMaker();
		}
		System.out.println("Done");
		}
	}
}
