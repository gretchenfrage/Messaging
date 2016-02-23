package com.phoenixkahlo.messaging.utils;

import java.net.URISyntaxException;

public class FileUtils {
	
	private FileUtils() {}
	
	/*
	 * Returns the path of the given file in the same directory as the jar file the program is running from
	 */
	public static String getParallelPath(String path) {
		try {
			String selfPath = FileUtils.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			return selfPath.substring(0, selfPath.lastIndexOf('/') + 1) + path;
		} catch (URISyntaxException e) {
			System.err.println("URISyntaxException while searching for file " + path);
			e.printStackTrace();
			System.exit(1);
			return null;
		}
	}
	
}
