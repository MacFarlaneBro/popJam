package utilities;

public class Pitch {
	
	private Note note;
	
	public Note getPitch(double maxFreq){
		
		if(maxFreq >= 80.095 && maxFreq < 84.86)
		{
			return note = new Note("E2");// 82.41
		}
		else if(maxFreq >= 84.86 && maxFreq < 89.90)
		{
			return note = new Note("F2");// 87.31
		}
		else if(maxFreq >= 89.90 && maxFreq < 95.90)
		{
			return note = new Note("F#2");// 92.50
		}
		else if(maxFreq >= 95.90 && maxFreq  < 100.75)
		{
			return note = new Note("G2");// 98.00
		}
		else if(maxFreq >= 100.75 && maxFreq < 106.91)
		{
			return note = new Note("G#2");// 103.83
		}
		else if(maxFreq >= 106.91 && maxFreq < 113.27)
		{
			return note = new Note("A2");// 110.00
		}
		else if(maxFreq >= 113.27 && maxFreq < 120)
		{
			return note = new Note("A#2");// 116.54
		}
		else if(maxFreq >= 120 && maxFreq < 127.14)
		{
			return note = new Note("B2");// 123.47
		}
		else if(maxFreq >= 127.14 && maxFreq < 134.7)
		{
			return note = new Note("C3");// 130.81
		}
		else if(maxFreq >= 134.7 && maxFreq < 142.71)
		{
			return note = new Note("C#3");// 138.59
		}
		else if(maxFreq >= 142.71 && maxFreq < 151.19)
		{
			return note = new Note("D3");// 146.83
		}
		else if(maxFreq >= 151.19 && maxFreq < 160.18)
		{
			return note = new Note("D#3");// 155.56
		}
		else if(maxFreq >= 160.18 && maxFreq < 169.71)
		{
			return note = new Note("E3");// 164.81
		}
		else if(maxFreq >= 169.71 && maxFreq < 179.8)
		{
			return note = new Note("F3");// 174.61
		}
		else if(maxFreq >= 179.8 && maxFreq < 190.5)
		{
			return note = new Note("F#3");// 185.00
		}
		else if(maxFreq >= 190.5 && maxFreq < 201.82)
		{
			return note = new Note("G3");// 196.00
		}
		else if(maxFreq >= 201.82 && maxFreq < 213.82)
		{
			return note = new Note("G#3");// 207.65
		}
		else if(maxFreq >= 213.82 && maxFreq < 226.54)
		{
			return note = new Note("A3");// 220.00
		}
		else if(maxFreq >= 226.54 && maxFreq < 240.01)
		{
			return note = new Note("A#3");// 233.08
		}
		else if(maxFreq >= 240.01 && maxFreq < 254.28)
		{
			return note = new Note("B3");// 246.94
		}
		else if(maxFreq >= 254.28 && maxFreq < 269.40)
		{
			return note = new Note("C4");// 261.63
		}
		else if(maxFreq >= 269.40 && maxFreq < 285.42)
		{
			return note = new Note("C#4");// 277.18
		}
		else if(maxFreq >= 285.42 && maxFreq < 302.39)
		{
			return note = new Note("D4");// 293.66
		}
		else if(maxFreq >= 302.39 && maxFreq < 320.38)
		{
			return note = new Note("D#4");// 311.13
		}
		else if(maxFreq >= 320.38 && maxFreq < 339.43)
		{
			return note = new Note("E4");// 329.63
		}
		else if(maxFreq >= 339.43 && maxFreq < 359.61)
		{
			return note = new Note("F4");// 349.23
		}
		else if(maxFreq >= 359.61 && maxFreq < 380.99)
		{
			return note = new Note("F#4");// 369.99
		}
		else if(maxFreq >= 380.99 && maxFreq < 403.65)
		{
			return note = new Note("G4");// 392.00
		}
		else if(maxFreq >= 403.65 && maxFreq < 427.65)
		{
			return note = new Note("G#4");// 415.30
		}
		else if(maxFreq >= 427.65 && maxFreq < 453.08)
		{
			return note = new Note("A4");// 440.00
		}
		else if(maxFreq >= 453.08 && maxFreq < 480.02)
		{
			return note = new Note("A#4");// 466.16
		}
		else if(maxFreq >= 480.02 && maxFreq < 508.56)
		{
			return note = new Note("B4");// 493.88
		}
		else if(maxFreq >= 508.56 && maxFreq < 538.81)
		{
			return note = new Note("C5");// 523.25
		}
		else if(maxFreq >= 538.81 && maxFreq < 570.85)
		{
			return note = new Note("C#5");// 554.37
		}
		else if(maxFreq >= 570.85 && maxFreq < 604.79)
		{
			return note = new Note("D5");// 587.33
		}
		else if(maxFreq >= 604.79 && maxFreq < 640.75)
		{
			return note = new Note("D#5");// 622.25
		}
		else if(maxFreq >= 640.75 && maxFreq < 678.86)
		{
			return note = new Note("E5");// 659.26
		}
		else if(maxFreq >= 678.86 && maxFreq < 719.22)
		{
			return note = new Note("F5");// 698.46
		}
		else if(maxFreq >= 719.22 && maxFreq < 761.99)
		{
			return note = new Note("F#5");// 739.99
		}
		else if(maxFreq >= 761.99 && maxFreq < 807.3)
		{
			return note = new Note("G5");// 783.99
		}
		else if(maxFreq >= 807.3 && maxFreq < 855.15)
		{
			return note = new Note("G#5");// 830.61
		}
		else if(maxFreq >= 855.15 && maxFreq < 906.16)
		{
			return note = new Note("A5");// 880.00
		}
		else if(maxFreq >= 906.16 && maxFreq < 960.05)
		{
			return note = new Note("A#5");// 932.33
		}
		else if(maxFreq >= 960.05 && maxFreq < 1017.13)
		{
			return note = new Note("B5");// 987.77
		}
		else if(maxFreq >= 1017.13 && maxFreq < 1077.61)
		{
			return note = new Note("C6");// 1046.50
		}
		else if(maxFreq >= 1077.61 && maxFreq < 1141.69)
		{
			return note = new Note("C#6");// 1108.73
		}
		else if(maxFreq >= 1141.69 && maxFreq < 1209.58)
		{
			return note = new Note("D6");// 1174.66
		}
		else if(maxFreq >= 1209.58 && maxFreq < 1281.51)
		{
			return note = new Note("D#6");// 1244.51
		}
		else if(maxFreq >= 1281.51 && maxFreq < 1357.71)
		{
			return note = new Note("E6");// 1318.51
		}
		else if(maxFreq >= 1357.71 && maxFreq < 1438.45)
		{
			return note = new Note("F6");// 1396.91
		}
		else if(maxFreq >= 1438.45 && maxFreq < 1523.98)
		{
			return note = new Note("F#6");// 1479.98
		}
		else if(maxFreq >= 1523.98 && maxFreq < 1614.6)
		{
			return note = new Note("G6");// 1567.98
		}
		else if(maxFreq >= 1614.6) return null;//G6 is, according to the Guinness Book of Records, the highest note ever scored for human voice
		
		//else if(pitch <= 80) return "Note Below Range!";
		return null;
	}
	
	public double getFrequency(String pitch){
		
        switch (pitch) {
            case "E2": return 82.41;
            
            case "F2": return 87.31;
            
            case "F#2": return 92.50;
            
            case "G2": return 98.00;
            
            case "G#2": return 103.83;
            
            case "A2": return 110.00;
            
            case "A#2": return 116.54;
            
            case "B2": return 123.47;
            
            case "C3": return 130.81;
            
            case "C#3": return 138.59;
            
            case "D3": return 146.83;
            
            case "D#3": return 155.56;
            
            case "E3": return 164.81;
            
            case "F3": return 174.61;
            
            case "F#3": return 185.00;
            
            case "G3": return 196.00;
            
            case "G#3": return 207.65;
            
            case "A3": return 220.00;
            
            case "A#3": return 223.08;
            
            case "B3": return 246.94;
            
            case "C4": return 261.63;
            
            case "C#4": return 277.18;
            
            case "D4": return 293.66;
            
            case "D#4": return 311.13;
            
            case "E4": return 329.63;
            
            case "F4": return 349.23;
            
            case "F#4": return 369.99;
            
            case "G4": return 392.00;
            
            case "G#4": return 415.30;
            
            case "A4": return 440.00;
            
            case "A#4": return 466.16;
            
            case "B4": return 493.88;
            
            case "C5": return 523.25;
            
            case "C#5": return 554.37;
            
            case "D5": return 587.33;
            
            case "D#5": return 622.25;
            
            case "E5": return 659.26;
            
            case "F5": return 698.46;
            
            case "F#5": return 739.99;
            
            case "G5": return 783.99;
            
            case "G#5": return 830.61;
            
            case "A5": return 880.00;
            
            case "A#5": return 932.33;
            
            case "B5": return 987.77;
            
            case "C6": return 1046.50;
            
            case "C#6": return 1108.73;
            
            case "D6": return 1174.66;
            
            case "D#6": return 1244.51;
            
            case "E6": return 1318.51;
            
            case "F6": return 1396.91;
            
            case "F#6": return 1479.98;
            
            case "G6": return 1567.98;
            
            default : System.out.println("pitch not recognised!");
            		return 0.00;
        }
	}
}
