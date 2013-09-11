package storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import analysis.Mode;

public class Scale {
	
	private ArrayList<Note> scale;
	private Note root;
	private String scaleType;
	private Note note;
	
	public Scale(Note[] pitchArray){
		Mode mode = new Mode();
		
		double[] temp = new double[10];
		
		for(int i = 0; i < 10; i++){
			temp[i] = pitchArray[i].getFrequency();
		}
		
		Pitch pitch = new Pitch();
		
		root = pitch.getNote(mode.getMode(temp));// finds the most common note in the first 10 pitches and assigns it as the root
	}
	
	public Scale(Note root) {
		
		this.root = root;
	}
	
	public String getScaleType(){
		return scaleType;
	}
	
	public Scale() {
	}

	public ArrayList<Note> getScale(){
		
		return scale;
	}

	public void setScale(String scale){
		
			this.scaleType = scale;
		
			if(scale.equals("minor")){
				setMinor(5);
			} else if(scale.equals("major")){
				setMajor(5);
			} else if(scale.equals("pentatonic")){
				setPentatonic(5);
			} else {
				System.out.println("I'm sorry we don't appear to have that type of scale, please select either major, minor or pentatonic");
			}
	}

	private ArrayList<Note> setPentatonic(int number) {
		
		scale = new ArrayList<Note>();
		
		if(number == 0){//A#
			
			for(int i = 2; i < 6; i++)
			{
				scale.add(note = new Note("A#" + i));
				scale.add(note = new Note("C#" + i));
				scale.add(note = new Note("D#" + i));
				scale.add(note = new Note("F" + i));
				scale.add(note = new Note("G#" + i));
			}
		} else if(number == 1){
			for(int i = 2; i < 6; i++){//C#
				scale.add(note = new Note("C#" + i));
				scale.add(note = new Note("E" + i));
				scale.add(note = new Note("F#" + i));
				scale.add(note = new Note("G#" + i));
				scale.add(note = new Note("B" + i));
			}
		} else if(number == 2){//D#
			for(int i = 2; i < 6; i++)
			{
				scale.add(note = new Note("D#" + i));
				scale.add(note = new Note("F#" + i));
				scale.add(note = new Note("G#" + i));
				scale.add(note = new Note("A#" + i));
				scale.add(note = new Note("C#" + i));
			}
		} else if(number == 3){//F#
			for(int i = 2; i < 6; i++)
			{
				scale.add(note = new Note("F#" + i));
				scale.add(note = new Note("A" + i));
				scale.add(note = new Note("B" + i));
				scale.add(note = new Note("C#" + i));
				scale.add(note = new Note("E" + i));
			}
		} else if(number == 4){//G#
			for(int i = 2; i < 6; i++)
			{
				scale.add(note = new Note("G#" + i));
				scale.add(note = new Note("B" + i));
				scale.add(note = new Note("C#" + i));
				scale.add(note = new Note("D#" + i));
				scale.add(note = new Note("F#" + i));
			}
		} else if(number == 5){//A
			
			for(int i = 2; i < 6; i++)
			{
				scale.add(note = new Note("A" + i));
				scale.add(note = new Note("C" + i));
				scale.add(note = new Note("D" + i));
				scale.add(note = new Note("E" + i));
				scale.add(note = new Note("G" + i));
			}
		} else if(number == 6){//B
			for(int i = 2; i < 6; i++)
			{
				scale.add(note = new Note("B" + i));
				scale.add(note = new Note("D" + i));
				scale.add(note = new Note("E" + i));
				scale.add(note = new Note("F#" + i));
				scale.add(note = new Note("A" + i));
			}
		} else if(number == 7){//C
			for(int i = 2; i < 6; i++)
			{
				scale.add(note = new Note("C" + i));
				scale.add(note = new Note("D#" + i));
				scale.add(note = new Note("F" + i));
				scale.add(note = new Note("G" + i));
				scale.add(note = new Note("A#" + i));
			}
		} else if(number == 8){//D
			for(int i = 2; i < 6; i++)
			{
				scale.add(note = new Note("D" + i));
				scale.add(note = new Note("F" + i));
				scale.add(note = new Note("G" + i));
				scale.add(note = new Note("A" + i));
				scale.add(note = new Note("C" + i));
			}
		} else if(number == 9){//E
			for(int i = 2; i < 6; i++)
			{
				scale.add(note = new Note("E" + i));
				scale.add(note = new Note("G" + i));
				scale.add(note = new Note("A" + i));
				scale.add(note = new Note("B" + i));
				scale.add(note = new Note("D" + i));
			}
		} else if(number == 10){//F
			for(int i = 2; i < 6; i++)
			{
				scale.add(note = new Note("F" + i));
				scale.add(note = new Note("G#" + i));
				scale.add(note = new Note("A#" + i));
				scale.add(note = new Note("C" + i));
				scale.add(note = new Note("D#" + i));
			}
		} else if(number == 11){//G
			for(int i = 2; i < 6; i++)
			{
				scale.add(note = new Note("G" + i));
				scale.add(note = new Note("A#" + i));
				scale.add(note = new Note("C" + i));
				scale.add(note = new Note("D" + i));
				scale.add(note = new Note("F" + i));
			}
		}
		return scale;
	}

	private ArrayList<Note> setMajor(int number) {
		
		scale = new ArrayList<Note>();
		
		if(number == 0){//A#
			
			for(int i = 2; i < 6; i++)
			{
				scale.add(note = new Note("A#" + i));
				scale.add(note = new Note("C" + i));
				scale.add(note = new Note("D" + i));
				scale.add(note = new Note("D#" + i));
				scale.add(note = new Note("F" + i));
				scale.add(note = new Note("G" + i));
				scale.add(note = new Note("A" + i));
			}
		} else if(number == 1){//C#
			for(int i = 2; i < 6; i++){
				scale.add(note = new Note("C#" + i));
				scale.add(note = new Note("D#" + i));
				scale.add(note = new Note("F" + i));
				scale.add(note = new Note("F#" + i));
				scale.add(note = new Note("G#" + i));
				scale.add(note = new Note("A#" + i));
				scale.add(note = new Note("C" + i));
			}
		} else if(number == 2){//D#
			for(int i = 2; i < 6; i++)
			{
				scale.add(note = new Note("D#" + i));
				scale.add(note = new Note("F" + i));
				scale.add(note = new Note("G" + i));
				scale.add(note = new Note("G#" + i));
				scale.add(note = new Note("A#" + i));
				scale.add(note = new Note("C" + i));
				scale.add(note = new Note("D" + i));
			}
		} else if(number == 3){//F#
			for(int i = 2; i < 6; i++){
				scale.add(note = new Note("F#" + i));
				scale.add(note = new Note("G#" + i));
				scale.add(note = new Note("A#" + i));
				scale.add(note = new Note("B" + i));
				scale.add(note = new Note("C#" + i));
				scale.add(note = new Note("D#" + i));
				scale.add(note = new Note("F" + i));
			}
		} else if(number == 4){//G#
			for(int i = 2; i < 6; i++)
			{
				scale.add(note = new Note("G#" + i));
				scale.add(note = new Note("A#" + i));
				scale.add(note = new Note("C" + i));
				scale.add(note = new Note("C#" + i));
				scale.add(note = new Note("D#" + i));
				scale.add(note = new Note("F" + i));
				scale.add(note = new Note("G" + i));
			}
		} else if(number == 5){//A
			
			for(int i = 2; i < 6; i++)
			{
				scale.add(note = new Note("A" + i));
				scale.add(note = new Note("B" + i));
				scale.add(note = new Note("C#" + i));
				scale.add(note = new Note("D" + i));
				scale.add(note = new Note("E" + i));
				scale.add(note = new Note("F#" + i));
				scale.add(note = new Note("G#" + i));
			}
		} else if(number == 6){//B
			for(int i = 2; i < 6; i++)
			{
				scale.add(note = new Note("B" + i));
				scale.add(note = new Note("C#" + i));
				scale.add(note = new Note("D#" + i));
				scale.add(note = new Note("E" + i));
				scale.add(note = new Note("F#" + i));
				scale.add(note = new Note("G#" + i));
				scale.add(note = new Note("A#" + i));
			}
		} else if(number == 7){//C
			for(int i = 2; i < 6; i++)
			{
				scale.add(note = new Note("C" + i));
				scale.add(note = new Note("D" + i));
				scale.add(note = new Note("E" + i));
				scale.add(note = new Note("F" + i));
				scale.add(note = new Note("G" + i));
				scale.add(note = new Note("A" + i));
				scale.add(note = new Note("B" + i));
			}
		} else if(number == 8){//D
			for(int i = 2; i < 6; i++)
			{
				scale.add(note = new Note("D" + i));
				scale.add(note = new Note("E" + i));
				scale.add(note = new Note("F#" + i));
				scale.add(note = new Note("G" + i));
				scale.add(note = new Note("A" + i));
				scale.add(note = new Note("B" + i));
				scale.add(note = new Note("C#" + i));
			}
		} else if(number == 9){//E
			for(int i = 2; i < 6; i++)
			{
				scale.add(note = new Note("E" + i));
				scale.add(note = new Note("F#" + i));
				scale.add(note = new Note("G#" + i));
				scale.add(note = new Note("A" + i));
				scale.add(note = new Note("B" + i));
				scale.add(note = new Note("C#" + i));
				scale.add(note = new Note("D#" + i));
			}
		} else if(number == 10){//F
			for(int i = 2; i < 6; i++)
			{
				scale.add(note = new Note("F" + i));
				scale.add(note = new Note("G" + i));
				scale.add(note = new Note("A" + i));
				scale.add(note = new Note("A#" + i));
				scale.add(note = new Note("C" + i));
				scale.add(note = new Note("D" + i));
				scale.add(note = new Note("E" + i));
			}
		} else if(number == 11){//G
			for(int i = 2; i < 6; i++)
			{
				scale.add(note = new Note("G" + i));
				scale.add(note = new Note("A" + i));
				scale.add(note = new Note("B" + i));
				scale.add(note = new Note("C" + i));
				scale.add(note = new Note("D" + i));
				scale.add(note = new Note("E" + i));
				scale.add(note = new Note("F#" + i));
			}
		}
		
		return scale;
		
	}

	private ArrayList<Note> setMinor(int number) {
		
		scale = new ArrayList<Note>();;
		
		if(number == 0){//A#
			
			for(int i = 2; i < 6; i++)
			{
				scale.add(note = new Note("A#" + i));
				scale.add(note = new Note("C" + i));
				scale.add(note = new Note("C#" + i));
				scale.add(note = new Note("D#" + i));
				scale.add(note = new Note("F" + i));
				scale.add(note = new Note("F#" + i));
				scale.add(note = new Note("A" + i));
			}
		} else if(number == 1){//C#
			for(int i = 2; i < 6; i++){
				scale.add(note = new Note("C#" + i));
				scale.add(note = new Note("D#" + i));
				scale.add(note = new Note("E" + i));
				scale.add(note = new Note("F#" + i));
				scale.add(note = new Note("G#" + i));
				scale.add(note = new Note("A" + i));
				scale.add(note = new Note("C" + i));
			}
		} else if(number == 2){//D#
			for(int i = 2; i < 6; i++)
			{
				scale.add(note = new Note("D#" + i));
				scale.add(note = new Note("F" + i));
				scale.add(note = new Note("F#" + i));
				scale.add(note = new Note("G#" + i));
				scale.add(note = new Note("A#" + i));
				scale.add(note = new Note("B" + i));
				scale.add(note = new Note("D" + i));
			}
		} else if(number == 3){//F#
			for(int i = 2; i < 6; i++){
				scale.add(note = new Note("F#" + i));
				scale.add(note = new Note("G#" + i));
				scale.add(note = new Note("A" + i));
				scale.add(note = new Note("B" + i));
				scale.add(note = new Note("C#" + i));
				scale.add(note = new Note("D" + i));
				scale.add(note = new Note("F" + i));
			}
		} else if(number == 4){//G#
			for(int i = 2; i < 6; i++)
			{
				scale.add(note = new Note("G#" + i));
				scale.add(note = new Note("A#" + i));
				scale.add(note = new Note("B" + i));
				scale.add(note = new Note("C#" + i));
				scale.add(note = new Note("D#" + i));
				scale.add(note = new Note("E" + i));
				scale.add(note = new Note("G" + i));
			}
		} else if(number == 5){//A
			
			for(int i = 2; i < 6; i++)
			{
				scale.add(note = new Note("A" + i));
				scale.add(note = new Note("B" + i));
				scale.add(note = new Note("C" + i));
				scale.add(note = new Note("D" + i));
				scale.add(note = new Note("E" + i));
				scale.add(note = new Note("F" + i));
				scale.add(note = new Note("G#" + i));
			}
		} else if(number == 6){//B
			for(int i = 2; i < 6; i++)
			{
				scale.add(note = new Note("B" + i));
				scale.add(note = new Note("C#" + i));
				scale.add(note = new Note("D" + i));
				scale.add(note = new Note("E" + i));
				scale.add(note = new Note("F#" + i));
				scale.add(note = new Note("G" + i));
				scale.add(note = new Note("A#" + i));
			}
		} else if(number == 7){//C
			for(int i = 2; i < 6; i++)
			{
				scale.add(note = new Note("C" + i));
				scale.add(note = new Note("D" + i));
				scale.add(note = new Note("D#" + i));
				scale.add(note = new Note("F" + i));
				scale.add(note = new Note("G" + i));
				scale.add(note = new Note("G#" + i));
				scale.add(note = new Note("B" + i));
			}
		} else if(number == 8){//D
			for(int i = 2; i < 6; i++)
			{
				scale.add(note = new Note("D" + i));
				scale.add(note = new Note("E" + i));
				scale.add(note = new Note("F" + i));
				scale.add(note = new Note("G" + i));
				scale.add(note = new Note("A" + i));
				scale.add(note = new Note("A#" + i));
				scale.add(note = new Note("C#" + i));
			}
		} else if(number == 9){//E
			for(int i = 2; i < 6; i++)
			{
				scale.add(note = new Note("E" + i));
				scale.add(note = new Note("F#" + i));
				scale.add(note = new Note("G" + i));
				scale.add(note = new Note("A" + i));
				scale.add(note = new Note("B" + i));
				scale.add(note = new Note("C" + i));
				scale.add(note = new Note("D#" + i));
			}
		} else if(number == 10){//F
			for(int i = 2; i < 6; i++)
			{
				scale.add(note = new Note("F" + i));
				scale.add(note = new Note("G" + i));
				scale.add(note = new Note("G#" + i));
				scale.add(note = new Note("A#" + i));
				scale.add(note = new Note("C" + i));
				scale.add(note = new Note("C#" + i));
				scale.add(note = new Note("E" + i));
			}
		} else if(number == 11){//G
			for(int i = 2; i < 6; i++)
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
		
		return scale;
	}
	
	
	public Note getLowNote(){
		
		int number = 0;
		
		if(!scaleType.equals("pentatonic")){
			
		Random random = new Random();
		number = random.nextInt(10);
		
		} else {
			Random random = new Random();
			number = random.nextInt(6);	
		}
		
		return scale.get(number);// returns note corresponding to number
	}
	
	public Note getMidNote(){
		
int number = 0;
		
		if(!scaleType.equals("pentatonic")){
			
		Random random = new Random();
		number = random.nextInt(10) + 10;
		
		} else {
			Random random = new Random();
			number = random.nextInt(7) + 6;	
		}
		
		return scale.get(number);// returns note corresponding to number
	}
	
	public Note getHighNote(){
		
		int number = 0;
		
		if(!scaleType.equals("pentatonic")){
			
		Random random = new Random();
		number = random.nextInt(10) +18;
		
		} else {
			
			Random random = new Random();
			number = random.nextInt(7) + 13;	
		}
		
		return scale.get(number);// returns note corresponding to number
	}
	
	
	public Scale setScale(Note[] inputNotes){
		
		List<Note>[] minorScaleArray = new ArrayList[11];//one set for each type of scale in order
		List<Note>[] majorScaleArray = new ArrayList[11];
		List<Note>[] pentatonicScaleArray = new ArrayList[11];
		
		for(int i = 0; i < 11; i++)//creating all scale note sets possible
		{
			minorScaleArray[i] = setMinor(i);
			majorScaleArray[i] = setMajor(i);
			pentatonicScaleArray[i] = setPentatonic(i);
		}	
		
		int[] minorCount = new int[11];
		int[] majorCount = new int[11];
		int[] pentatonicCount = new int[11];
		
		for(int i = 0; i < inputNotes.length; i++)//this doesn't take as long as it looks
		{//loop through every input note
			if(inputNotes[i] != null){
				for(int j = 0; j < minorScaleArray.length; j++){
					for(int k = 0; k < minorScaleArray[j].size(); k++){
						if(inputNotes[i].getFrequency() ==minorScaleArray[j].get(k).getFrequency()){
							minorCount[j]++;
						}
					}
				}
				for(int j = 0; j < majorScaleArray.length; j++){
					for(int k = 0; k < majorScaleArray[j].size(); k++){
						if(inputNotes[i].getFrequency() == majorScaleArray[j].get(k).getFrequency()){
							majorCount[j]++;
						}
					}
				}
				for(int j = 0; j < pentatonicScaleArray.length; j++){
					for(int k = 0; k < pentatonicScaleArray[j].size(); k++){
						if(inputNotes[i].getFrequency() == pentatonicScaleArray[j].get(k).getFrequency()){
							pentatonicCount[j]++;
						}
					}
				} 
			}
		}
		
		int max = 0;
		int maxIndex = 13;
		String maxString = "";
		
		for(int j = 0; j < 11; j ++){
			if(majorCount[j] > max){
				maxString = "major";
				maxIndex = j;
				max = majorCount[j];
			}
			if(minorCount[j] > max){
				maxString = "minor";
				maxIndex = j;
				max = minorCount[j];
			}
			if(pentatonicCount[j] > max){
				maxString = "pentatonic";
				maxIndex = j;
				max = pentatonicCount[j];
			}
		}
		
		if(maxString.equals("major")){//this branch sets the scale of the input notes
			setMajor(maxIndex);
		} else if(maxString.equals("minor")){
			setMinor(maxIndex);
		} else {
			setPentatonic(maxIndex);
		}
		
		root = scale.get(0);//set the root note to the root note of the scale
		
		scaleType = maxString;//set the type of scale (minor/major/pentatonic)
		
		System.out.println("Max: " + max + " notes in " + scaler(maxIndex) + " " + maxString);
		
//		for(int m = 0; m < minorCount.length; m++){	 If necessary these three loops print out the number of notes detected
//			System.out.print("Scale: " + scaler(m)); in each scale individually.
//			System.out.print(" Minor");
//			System.out.println(": " + minorCount[m]);
//		}
//		
//		for(int m = 0; m < majorCount.length; m++){	
//			System.out.print("Notes in " + scaler(m));
//			System.out.print(" Major");
//			System.out.println(": " + majorCount[m]);
//		}
//		
//		for(int m = 0; m < pentatonicCount.length; m++){	
//			System.out.print("Scale: " + scaler(m));
//			System.out.print(" Pentatonic");
//			System.out.println(": " + pentatonicCount[m]);
//		}
		return this;
	}
	
	private String scaler(int entry){
		
		switch(entry){
		
		case 0: return "A#";
		
		case 1: return "C#";
		
		case 2: return "D#";
		
		case 3: return "F#";
		
		case 4: return "G#";
		
		case 5: return "A";
		
		case 6: return "B";
		
		case 7: return "C";
		
		case 8: return "D";
		
		case 9: return "E";
		
		case 10: return "F";
		
		case 11: return "G";
		
		default: return "Not Found";
		}
	}

	public Note getRoot() {
		return root;
	}
}
