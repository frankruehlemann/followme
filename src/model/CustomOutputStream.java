package model;

import java.io.IOException;
import java.io.OutputStream;

import javafx.scene.control.TextArea;

public class CustomOutputStream extends OutputStream{
	
	private TextArea log;
	
	@Override
	public void write(int arg0) throws IOException {
		log.appendText(String.valueOf((char)arg0));		
	}
	
	public CustomOutputStream(TextArea log) {
		this.log = log;
	}

}
