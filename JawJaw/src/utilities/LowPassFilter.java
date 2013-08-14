package utilities;

public class LowPassFilter {
	
	static double lastInput = 0;
	
	
	//very simplistic low pass filter adds the previous input to the current input and divides by 2
	public double twoPointMovingAverageFilter(double input){
		
		double output = (input + lastInput) / 2;
		
		lastInput = input;
				
		return output;
	}

}
