package pitchDetection;

public class Mode {
	
	 public float getMode(float[] n){//method for calculating the mode of the pitch array generated
	    float t = 0;
	    for(int i=0; i<n.length; i++){
	        for(int j=1; j<n.length-i; j++){
	            if(n[j-1] > n[j]){
	                t = n[j-1];
	                n[j-1] = n[j];
	                n[j] = t;
	            }
	        }
	    }

	    float mode = n[0];
	    int temp = 1;
	    int temp2 = 1;
	    for(int i=1;i<n.length;i++){
	        if(n[i-1] == n[i]){
	            temp++;
	        }
	        else {
	            temp = 1;
	        }
	        if(temp >= temp2 && n[i] !=0){//removing any zero frequencies from the array
	            mode = n[i];
	            temp2 = temp;
	        }
	    }
	    return mode;
	}

}
