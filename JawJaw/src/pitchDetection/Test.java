package pitchDetection;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test {
	
	public static void main(String args) throws IOException{
		
		PitchDetection d = new PitchDetection();
		
		 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		 String input = bufferedReader.readLine();
		
		File file = new File("/audio." + input);
		
		byte[][] holder = d.wavToByte(file);
		double[] transfer = new double[holder.length*holder[0].length];
		int n = 0;
		
		for(int i = 0; i < holder.length; i++){
			for(int j = 0; j < holder[i].length; j++){
				transfer[n] = holder[i][j];
				n++;
			}
		}
		
		d.detect(transfer,transfer.length, null);
	}

}
