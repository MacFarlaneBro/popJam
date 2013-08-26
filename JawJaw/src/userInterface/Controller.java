package userInterface;

import java.io.IOException;

public interface Controller {
	
	public void record() throws IOException;
	
	public void play() throws IOException;
	
	public void correct() throws IOException;
	
	public void playNote() throws IOException;
}
