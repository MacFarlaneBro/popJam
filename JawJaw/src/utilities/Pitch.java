package utilities;

public class Pitch {
	
	public String getPitch(double maxFreq){
		
		if(maxFreq >= 80.095 && maxFreq < 84.86)
		{
			return "E2";// 82.41
		}
		else if(maxFreq >= 84.86 && maxFreq < 89.90)
		{
			return "F2";// 87.31
		}
		else if(maxFreq >= 89.90 && maxFreq < 95.90)
		{
			return "F#2";// 92.50
		}
		else if(maxFreq >= 95.90 && maxFreq  < 100.75)
		{
			return "G2";// 98.00
		}
		else if(maxFreq >= 100.75 && maxFreq < 106.91)
		{
			return "G#2";// 103.83
		}
		else if(maxFreq >= 106.91 && maxFreq < 113.27)
		{
			return "A2";// 110.00
		}
		else if(maxFreq >= 113.27 && maxFreq < 120)
		{
			return "A#2";// 116.54
		}
		else if(maxFreq >= 120 && maxFreq < 127.14)
		{
			return "B2";// 123.47
		}
		else if(maxFreq >= 127.14 && maxFreq < 134.7)
		{
			return "C3";// 130.81
		}
		else if(maxFreq >= 134.7 && maxFreq < 142.71)
		{
			return "C#3";// 138.59
		}
		else if(maxFreq >= 142.71 && maxFreq < 151.19)
		{
			return "D3";// 146.83
		}
		else if(maxFreq >= 151.19 && maxFreq < 160.18)
		{
			return "D#3";// 155.56
		}
		else if(maxFreq >= 160.18 && maxFreq < 169.71)
		{
			return "E3";// 164.81
		}
		else if(maxFreq >= 169.71 && maxFreq < 179.8)
		{
			return "F3";// 174.61
		}
		else if(maxFreq >= 179.8 && maxFreq < 190.5)
		{
			return "F#3";// 185.00
		}
		else if(maxFreq >= 190.5 && maxFreq < 201.82)
		{
			return "G3";// 196.00
		}
		else if(maxFreq >= 201.82 && maxFreq < 213.82)
		{
			return "G#3";// 207.65
		}
		else if(maxFreq >= 213.82 && maxFreq < 226.54)
		{
			return "A3";// 220.00
		}
		else if(maxFreq >= 226.54 && maxFreq < 240.01)
		{
			return "A#3";// 233.08
		}
		else if(maxFreq >= 240.01 && maxFreq < 254.28)
		{
			return "B3";// 246.94
		}
		else if(maxFreq >= 254.28 && maxFreq < 269.40)
		{
			return "C4";// 261.63
		}
		else if(maxFreq >= 269.40 && maxFreq < 285.42)
		{
			return "C#4";// 277.18
		}
		else if(maxFreq >= 285.42 && maxFreq < 302.39)
		{
			return "D4";// 293.66
		}
		else if(maxFreq >= 302.39 && maxFreq < 320.38)
		{
			return "D#4";// 311.13
		}
		else if(maxFreq >= 320.38 && maxFreq < 339.43)
		{
			return "E4";// 329.63
		}
		else if(maxFreq >= 339.43 && maxFreq < 359.61)
		{
			return "F4";// 349.23
		}
		else if(maxFreq >= 359.61 && maxFreq < 380.99)
		{
			return "F#4";// 369.99
		}
		else if(maxFreq >= 380.99 && maxFreq < 403.65)
		{
			return "G4";// 392.00
		}
		else if(maxFreq >= 403.65 && maxFreq < 427.65)
		{
			return "G#4";// 415.30
		}
		else if(maxFreq >= 427.65 && maxFreq < 453.08)
		{
			return "A4";// 440.00
		}
		else if(maxFreq >= 453.08 && maxFreq < 480.02)
		{
			return "A#4";// 466.16
		}
		else if(maxFreq >= 480.02 && maxFreq < 508.56)
		{
			return "B4";// 493.88
		}
		else if(maxFreq >= 508.56 && maxFreq < 538.81)
		{
			return "C5";// 523.25
		}
		else if(maxFreq >= 538.81 && maxFreq < 570.85)
		{
			return "C#5";// 554.37
		}
		else if(maxFreq >= 570.85 && maxFreq < 604.79)
		{
			return "D5";// 587.33
		}
		else if(maxFreq >= 604.79 && maxFreq < 640.75)
		{
			return "D#5";// 622.25
		}
		else if(maxFreq >= 640.75 && maxFreq < 678.86)
		{
			return "E5";// 659.26
		}
		else if(maxFreq >= 678.86 && maxFreq < 719.22)
		{
			return "F5";// 698.46
		}
		else if(maxFreq >= 719.22 && maxFreq < 761.99)
		{
			return "F#5";// 739.99
		}
		else if(maxFreq >= 761.99 && maxFreq < 807.3)
		{
			return "G5";// 783.99
		}
		else if(maxFreq >= 807.3 && maxFreq < 855.15)
		{
			return "G#5";// 830.61
		}
		else if(maxFreq >= 855.15 && maxFreq < 906.16)
		{
			return "A5";// 880.00
		}
		else if(maxFreq >= 906.16 && maxFreq < 960.05)
		{
			return "A#5";// 932.33
		}
		else if(maxFreq >= 960.05 && maxFreq < 1017.13)
		{
			return "B5";// 987.77
		}
		else if(maxFreq >= 1017.13 && maxFreq < 1077.61)
		{
			return "C6";// 1046.50
		}
		else if(maxFreq >= 1077.61 && maxFreq < 1141.69)
		{
			return "C#6";// 1108.73
		}
		else if(maxFreq >= 1141.69 && maxFreq < 1209.58)
		{
			return "D6";// 1174.66
		}
		else if(maxFreq >= 1209.58 && maxFreq < 1281.51)
		{
			return "D#6";// 1244.51
		}
		else if(maxFreq >= 1281.51 && maxFreq < 1357.71)
		{
			return "E6";// 1318.51
		}
		else if(maxFreq >= 1357.71 && maxFreq < 1438.45)
		{
			return "F6";// 1396.91
		}
		else if(maxFreq >= 1438.45 && maxFreq < 1523.98)
		{
			return "F#6";// 1479.98
		}
		else if(maxFreq >= 1523.98 && maxFreq < 1614.6)
		{
			return "G6";// 1567.98
		}
		else if(maxFreq >= 1614.6) return "Note above range!";//G6 is, according to the Guinness Book of Records, the highest note ever scored for human voice
		
		//else if(pitch <= 80) return "Note Below Range!";
		return "";
	}
	
}
