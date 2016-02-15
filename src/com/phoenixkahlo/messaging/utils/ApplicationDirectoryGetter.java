package com.phoenixkahlo.messaging.utils;

import java.io.File;

/*
 * Static class for getting a path to a dedicated directory for the application
 */
public class ApplicationDirectoryGetter {

	private ApplicationDirectoryGetter() {}
	
	public static String getPath(String folderName) {
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
