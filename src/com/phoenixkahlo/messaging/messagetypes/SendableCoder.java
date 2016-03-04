package com.phoenixkahlo.messaging.messagetypes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

import com.phoenixkahlo.messaging.utils.BiMap;
import com.phoenixkahlo.messaging.utils.Protocol;

/*
 * Respondible for reading and writing Sendables in a continuous stream, preceeding them with headers
 */
public class SendableCoder {

	private BiMap<Integer, Class<? extends Sendable>> map = new BiMap<Integer, Class<? extends Sendable>>();
	
	public SendableCoder() {
		map.link(Protocol.TEXT_MESSAGE_HEADER, TextMessage.class);
		map.link(Protocol.HEARTBEAT_HEADER, HeartBeat.class);
	}
	
	public Sendable read(InputStream in) throws IOException {
		Class<? extends Sendable> clazz = map.getB(in.read());
		if (clazz == null) {
			System.err.println("Header not linked to class");
			System.exit(1);
		}
		try {
			return clazz.getConstructor(InputStream.class).newInstance(in);
		} catch (NoSuchMethodException e) {
			try {
				return (Sendable) clazz.getMethod("construct", InputStream.class).invoke(null, in);
			} catch (InvocationTargetException invocExcep) {
				throw (IOException) invocExcep.getCause();
			} catch (IllegalAccessException | IllegalArgumentException | SecurityException
					| NoSuchMethodException unexpectedExcep) {
				System.err.println(clazz + " is invalid for SendableCoder");
				unexpectedExcep.printStackTrace();
				System.exit(1);
			}
		} catch (InvocationTargetException invocExcep) {
			throw (IOException) invocExcep.getCause();
		} catch (IllegalAccessException | IllegalArgumentException | InstantiationException
				| SecurityException unexpeactedExcep) {
			System.err.println(clazz + " is invalid created for SendableCoder");
			unexpeactedExcep.printStackTrace();
			System.exit(1);
		}
		throw new RuntimeException();
	}
	
	public void write(Sendable sendable, OutputStream out) throws IOException {
		Integer header = map.getA(sendable.getClass());
		if (header == null) {
			System.err.println("Class not linked to header");
			System.exit(1);
		} else {
			out.write(header);
			sendable.write(out);
		}
	}
	
}
