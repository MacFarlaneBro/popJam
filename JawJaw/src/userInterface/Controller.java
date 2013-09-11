package userInterface;

import java.io.IOException;

public interface Controller {
	
	public void record() throws IOException;
	
	public void play() throws IOException;
			
	public void generate() throws IOException;

	public void melodyMaker();
}
