package com.phoenixkahlo.messaging.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import com.phoenixkahlo.messaging.utils.FileUtils;

/*
 * Wraps a Properties object and saves it to the appropriate file with no delays
 */
public class PropertiesRepository {

	private Properties properties;
	private File file;
	
	public PropertiesRepository() throws IOException {
		file = new File(FileUtils.getAppDirPath("Phoenix Messaging" + File.separator + "PropertiesRepository.properties"));
		file.createNewFile();
		InputStream in = new FileInputStream(file);
		properties = new Properties();
		properties.load(in);
		in.close();
	}
	
	public String get(String key) {
		return properties.getProperty(key);
	}
	
	public void set(String key, String value) throws IOException {
		properties.setProperty(key, value);
		OutputStream out = new FileOutputStream(file);
		properties.store(out, "");
	}
	
}
