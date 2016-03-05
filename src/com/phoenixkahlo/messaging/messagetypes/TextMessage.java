package com.phoenixkahlo.messaging.messagetypes;

import java.awt.Component;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JTextArea;

import com.phoenixkahlo.messaging.utils.Protocol;

public class TextMessage extends Message {

	private String text;
	
	public TextMessage(String name, String text) {
		super(name);
		this.text = text;
	}
	
	public TextMessage(InputStream in) throws IOException {
		super(in);
		text = Protocol.readString(in);
	}
	
	@Override
	public void writeContents(OutputStream out) throws IOException {
		Protocol.writeString(text, out);
	}

	@Override
	public Component toComponent() {
		JTextArea textArea = new JTextArea(getName() + " > " + text);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setOpaque(false);
		return textArea;
	}
	
	@Override
	public String toString() {
		return getName() + " > " + text;
	}

}
