package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Launcher {
	
	public static void main(String[] args) throws IOException{
		
				
		String entry = "";	
				
		Controller theControl = new ControllerImpl();
		
		while(!entry.equals("exit")){
		System.out.println("Would you like to record new audio (r) or play back a track? (p) (type exit to exit)");
		
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
		System.out.println("Done");
		}
	}
}
