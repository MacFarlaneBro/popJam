package userInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Launcher {
	
	public static void main(String[] args) throws IOException{
				
		System.out.println( ((12 * Math.log(440/220)) /Math.log(2.0)) + 57.01d);
		String entry = "";	
				
		Controller theControl = new ControllerImpl();
		
		while(!entry.equals("exit")){
		entry = "";	
		System.out.println("Would you like to record new audio (r), play back a track? (p), or correct a track (c)(type exit to exit)");
		
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
		else if(entry.equals("c"))
		{	
				theControl.correct();		
		}
		System.out.println("Done");
		}
	}
}
