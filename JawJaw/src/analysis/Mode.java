package analysis;


public class Mode {
	
	//This method is directly adapted from a method provided written by StackOverflow user
	//Deyan, the link to the source code can be found in the reference page [6]
	public double getMode(double[] pitches){//method for calculating the mode of the pitch array generated
		 
	    double t = 0;
	    
	    for(int i=0; i<pitches.length; i++){
	        for(int j=1; j<pitches.length-i; j++){
	            if(pitches[j-1] > pitches[j]){
	                t = pitches[j-1];
	                pitches[j-1] = pitches[j];
	                pitches[j] = t;
	            }
	        }
	    }

	    double mode = pitches[0];
	    int temp = 1;
	    int temp2 = 1;
	    for(int i=1;i<pitches.length;i++){
	        if(pitches[i-1] == pitches[i]){
	            temp++;
	        }
	        else {
	            temp = 1;
	        }
	        if(temp >= temp2 && pitches[i] !=0){//removing any zero frequencies from the array
	            mode = pitches[i];
	            temp2 = temp;
	        }
	    }
	    return mode;
	}
}
