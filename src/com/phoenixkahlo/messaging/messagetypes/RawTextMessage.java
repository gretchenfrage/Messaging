package com.phoenixkahlo.messaging.messagetypes;

import java.awt.Component;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JTextArea;

import com.phoenixkahlo.messaging.utils.Protocol;

public class RawTextMessage extends Message {

	private String text;
	
	public RawTextMessage(String text) {
		this.text = text;
	}
	
	public RawTextMessage(InputStream in) throws IOException {
		super(in);
		text = Protocol.readString(in);
	}
	
	@Override
	public void write(OutputStream out) throws IOException {
		super.write(out);
		Protocol.writeString(text, out);
	}
	
	@Override
	public Component toComponent() {
		JTextArea textArea = new JTextArea(text);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setOpaque(false);
		return textArea;
	}
	
	@Override
	public String toString() {
		return super.toString() + text;
	}
	
}
