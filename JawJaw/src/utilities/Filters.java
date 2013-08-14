package utilities;

public class Filters {
	
	public byte[] lowPass( byte[] values , int smoothing ){
		  byte value = values[0]; // start with the first input
		  int length = values.length;
		  for (int i=1 ; i < length ; ++i){
		    double currentValue = values[i];
		    value += (currentValue - value) / smoothing;
		    values[i] = value;
		  }
		  return values;
		}

}
