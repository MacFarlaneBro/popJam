package utilities;

import java.util.ArrayList;

public class Scale {
	
	private ArrayList<Note> scale;
	private Note root;
	private String scaleType;
	
	public Scale(Note[] pitchArray){
		Mode mode = new Mode();
		
		double[] temp = new double[10];
		
		for(int i = 0; i < 10; i++){
			temp[i] = pitchArray[i].getFrequency();
		}
		
		Pitch pitch = new Pitch();
		
		root = pitch.getPitch(mode.getMode(temp));// finds the most common note in the first 10 pitches and assigns it as the root
	}
	
	public void setScale(String scale){
		
			this.scaleType = scale;
		
			if(scale.equals("minor")){
				setMinor(root.getPitch());
			} else if(scale.equals("major")){
				setMajor(root.getPitch());
			} else if(scale.equals("pentatonic")){
				setPentatonic(root.getPitch());
			} else {
				System.out.println("I'm sorry we don't appear to have that type of scale, please select either major, minor or pentatonic");
			}
	}

	private void setPentatonic(String root) {
		// TODO Auto-generated method stub
		
	}

	private void setMajor(String root) {
		// TODO Auto-generated method stub
		
	}

	private void setMinor(String root) {
		
		Note note;
		scale = new ArrayList<Note>();
		
		if(root.startsWith("A#")){
			
			for(int i = 0; i < 7; i++)
			{
				scale.add(note = new Note("A#" + i));
				scale.add(note = new Note("C" + i));
				scale.add(note = new Note("C#" + i));
				scale.add(note = new Note("D#" + i));
				scale.add(note = new Note("F" + i));
				scale.add(note = new Note("F#" + i));
				scale.add(note = new Note("A" + i));
			}
		} else if(root.startsWith("C#")){
			for(int i = 0; i < 7; i++){
				scale.add(note = new Note("C#" + i));
				scale.add(note = new Note("D#" + i));
				scale.add(note = new Note("E" + i));
				scale.add(note = new Note("F#" + i));
				scale.add(note = new Note("G#" + i));
				scale.add(note = new Note("A" + i));
				scale.add(note = new Note("C" + i));
			}
		} else if(root.startsWith("D#")){
			for(int i = 0; i < 7; i++)
			{
				scale.add(note = new Note("D#" + i));
				scale.add(note = new Note("F" + i));
				scale.add(note = new Note("F#" + i));
				scale.add(note = new Note("G#" + i));
				scale.add(note = new Note("A#" + i));
				scale.add(note = new Note("B" + i));
				scale.add(note = new Note("D" + i));
			}
		} else if(root.startsWith("F#")){
			for(int i = 0; i < 7; i++){
				scale.add(note = new Note("F#" + i));
				scale.add(note = new Note("G#" + i));
				scale.add(note = new Note("A" + i));
				scale.add(note = new Note("B" + i));
				scale.add(note = new Note("C#" + i));
				scale.add(note = new Note("D" + i));
				scale.add(note = new Note("F" + i));
			}
		} else if(root.startsWith("G#")){
			for(int i = 2; i < 7; i++)
			{
				scale.add(note = new Note("G#" + i));
				scale.add(note = new Note("A#" + i));
				scale.add(note = new Note("B" + i));
				scale.add(note = new Note("C#" + i));
				scale.add(note = new Note("D#" + i));
				scale.add(note = new Note("E" + i));
				scale.add(note = new Note("G" + i));
			}
		} else if((root.startsWith("A"))){
			
			for(int i = 0; i < 7; i++)
			{
				scale.add(note = new Note("A" + i));
				scale.add(note = new Note("B" + i));
				scale.add(note = new Note("C" + i));
				scale.add(note = new Note("D" + i));
				scale.add(note = new Note("E" + i));
				scale.add(note = new Note("F" + i));
				scale.add(note = new Note("G#" + i));
			}
		} else if(root.startsWith("B")){
			for(int i = 0; i < 7; i++)
			{
				scale.add(note = new Note("B" + i));
				scale.add(note = new Note("C#" + i));
				scale.add(note = new Note("D" + i));
				scale.add(note = new Note("E" + i));
				scale.add(note = new Note("F#" + i));
				scale.add(note = new Note("G" + i));
				scale.add(note = new Note("A#" + i));
			}
		} else if(root.startsWith("C")){
			for(int i = 0; i < 7; i++)
			{
				scale.add(note = new Note("C" + i));
				scale.add(note = new Note("D" + i));
				scale.add(note = new Note("D#" + i));
				scale.add(note = new Note("F" + i));
				scale.add(note = new Note("G" + i));
				scale.add(note = new Note("G#" + i));
				scale.add(note = new Note("B" + i));
			}
		} else if(root.startsWith("D")){
			for(int i = 0; i < 7; i++)
			{
				scale.add(note = new Note("D" + i));
				scale.add(note = new Note("E" + i));
				scale.add(note = new Note("F" + i));
				scale.add(note = new Note("G" + i));
				scale.add(note = new Note("A" + i));
				scale.add(note = new Note("A#" + i));
				scale.add(note = new Note("C#" + i));
			}
		} else if(root.startsWith("E")){
			for(int i = 0; i < 7; i++)
			{
				scale.add(note = new Note("E" + i));
				scale.add(note = new Note("F#" + i));
				scale.add(note = new Note("G" + i));
				scale.add(note = new Note("A" + i));
				scale.add(note = new Note("B" + i));
				scale.add(note = new Note("C" + i));
				scale.add(note = new Note("D#" + i));
			}
		} else if(root.startsWith("F")){
			for(int i = 0; i < 7; i++)
			{
				scale.add(note = new Note("F" + i));
				scale.add(note = new Note("G" + i));
				scale.add(note = new Note("G#" + i));
				scale.add(note = new Note("A#" + i));
				scale.add(note = new Note("C" + i));
				scale.add(note = new Note("C#" + i));
				scale.add(note = new Note("E" + i));
			}
		} else if(root.startsWith("G")){
			for(int i = 0; i < 7; i++)
			{
				scale.add(note = new Note("G" + i));
				scale.add(note = new Note("A" + i));
				scale.add(note = new Note("A#" + i));
				scale.add(note = new Note("C" + i));
				scale.add(note = new Note("D" + i));
				scale.add(note = new Note("D#" + i));
				scale.add(note = new Note("F#" + i));
			}
		}
	}
	
	
}
