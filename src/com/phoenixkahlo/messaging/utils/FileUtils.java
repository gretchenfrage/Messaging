package com.phoenixkahlo.messaging.utils;

import java.io.File;
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
	
	/*
	 * Returns a dedicated application directory path, dependent on the operating system
	 */
	public static String getAppDirPath(String folderName) {
		String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("win"))
			return System.getenv("APPDATA") + "\\" + folderName;
		else if (os.contains("mac"))
			return System.getProperty("user.home") + "/Library/Application Support/" + folderName;
		else if (os.contains("nux"))
			return System.getProperty("user.home") + "/." + folderName;
		else
			return System.getProperty("user.dir" + File.separator + folderName);
	}
	
}
