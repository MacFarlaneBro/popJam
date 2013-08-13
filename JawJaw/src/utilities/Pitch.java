package utilities;

public class Pitch {
	
	public String getPitch(float pitch){
		
		if(pitch >= 80.095 && pitch < 84.86)
		{
			return "E2";// 82.41
		}
		else if(pitch >= 84.86 && pitch < 89.90)
		{
			return "F2";// 87.31
		}
		else if(pitch >= 89.90 && pitch < 95.90)
		{
			return "F#2";// 92.50
		}
		else if(pitch >= 95.90 && pitch  < 100.75)
		{
			return "G2";// 98.00
		}
		else if(pitch >= 100.75 && pitch < 106.91)
		{
			return "G#2";// 103.83
		}
		else if(pitch >= 106.91 && pitch < 113.27)
		{
			return "A2";// 110.00
		}
		else if(pitch >= 113.27 && pitch < 120)
		{
			return "A#2";// 116.54
		}
		else if(pitch >= 120 && pitch < 127.14)
		{
			return "B2";// 123.47
		}
		else if(pitch >= 127.14 && pitch < 134.7)
		{
			return "C3";// 130.81
		}
		else if(pitch >= 134.7 && pitch < 142.71)
		{
			return "C#3";// 138.59
		}
		else if(pitch >= 142.71 && pitch < 151.19)
		{
			return "D3";// 146.83
		}
		else if(pitch >= 151.19 && pitch < 160.18)
		{
			return "D#3";// 155.56
		}
		else if(pitch >= 160.18 && pitch < 169.71)
		{
			return "E3";// 164.81
		}
		else if(pitch >= 169.71 && pitch < 179.8)
		{
			return "F3";// 174.61
		}
		else if(pitch >= 179.8 && pitch < 190.5)
		{
			return "F#3";// 185.00
		}
		else if(pitch >= 190.5 && pitch < 201.82)
		{
			return "G3";// 196.00
		}
		else if(pitch >= 201.82 && pitch < 213.82)
		{
			return "G#3";// 207.65
		}
		else if(pitch >= 213.82 && pitch < 226.54)
		{
			return "A3";// 220.00
		}
		else if(pitch >= 226.54 && pitch < 240.01)
		{
			return "A#3";// 233.08
		}
		else if(pitch >= 240.01 && pitch < 254.28)
		{
			return "B3";// 246.94
		}
		else if(pitch >= 254.28 && pitch < 269.40)
		{
			return "C4";// 261.63
		}
		else if(pitch >= 269.40 && pitch < 285.42)
		{
			return "C#4";// 277.18
		}
		else if(pitch >= 285.42 && pitch < 302.39)
		{
			return "D4";// 293.66
		}
		else if(pitch >= 302.39 && pitch < 320.38)
		{
			return "D#4";// 311.13
		}
		else if(pitch >= 320.38 && pitch < 339.43)
		{
			return "E4";// 329.63
		}
		else if(pitch >= 339.43 && pitch < 359.61)
		{
			return "F4";// 349.23
		}
		else if(pitch >= 359.61 && pitch < 380.99)
		{
			return "F#4";// 369.99
		}
		else if(pitch >= 380.99 && pitch < 403.65)
		{
			return "G4";// 392.00
		}
		else if(pitch >= 403.65 && pitch < 427.65)
		{
			return "G#4";// 415.30
		}
		else if(pitch >= 427.65 && pitch < 453.08)
		{
			return "A4";// 440.00
		}
		else if(pitch >= 453.08 && pitch < 480.02)
		{
			return "A#4";// 466.16
		}
		else if(pitch >= 480.02 && pitch < 508.56)
		{
			return "B4";// 493.88
		}
		else if(pitch >= 508.56 && pitch < 538.81)
		{
			return "C5";// 523.25
		}
		else if(pitch >= 538.81 && pitch < 570.85)
		{
			return "C#5";// 554.37
		}
		else if(pitch >= 570.85 && pitch < 604.79)
		{
			return "D5";// 587.33
		}
		else if(pitch >= 604.79 && pitch < 640.75)
		{
			return "D#5";// 622.25
		}
		else if(pitch >= 640.75 && pitch < 678.86)
		{
			return "E5";// 659.26
		}
		else if(pitch >= 678.86 && pitch < 719.22)
		{
			return "F5";// 698.46
		}
		else if(pitch >= 719.22 && pitch < 761.99)
		{
			return "F#5";// 739.99
		}
		else if(pitch >= 761.99 && pitch < 807.3)
		{
			return "G5";// 783.99
		}
		else if(pitch >= 807.3 && pitch < 855.15)
		{
			return "G#5";// 830.61
		}
		else if(pitch >= 855.15 && pitch < 906.16)
		{
			return "A5";// 880.00
		}
		else if(pitch >= 906.16 && pitch < 960.05)
		{
			return "A#5";// 932.33
		}
		else if(pitch >= 960.05 && pitch < 1017.13)
		{
			return "B5";// 987.77
		}
		else if(pitch >= 1017.13 && pitch < 1077.61)
		{
			return "C6";// 1046.50
		}
		else if(pitch >= 1077.61 && pitch < 1141.69)
		{
			return "C#6";// 1108.73
		}
		else if(pitch >= 1141.69 && pitch < 1209.58)
		{
			return "D6";// 1174.66
		}
		else if(pitch >= 1209.58 && pitch < 1281.51)
		{
			return "D#6";// 1244.51
		}
		else if(pitch >= 1281.51 && pitch < 1357.71)
		{
			return "E6";// 1318.51
		}
		else if(pitch >= 1357.71 && pitch < 1438.45)
		{
			return "F6";// 1396.91
		}
		else if(pitch >= 1438.45 && pitch < 1523.98)
		{
			return "F#6";// 1479.98
		}
		else if(pitch >= 1523.98 && pitch < 1614.6)
		{
			return "G6";// 1567.98
		}
		else if(pitch >= 1614.6) return "Note above range!";//G6 is, according to the Guinness Book of Records, the highest note ever scored for human voice
		
		//else if(pitch <= 80) return "Note Below Range!";
		return "";
	}
	
}
