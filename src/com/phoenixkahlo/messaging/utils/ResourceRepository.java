package com.phoenixkahlo.messaging.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.phoenixkahlo.messaging.messagetypes.Resource;
import com.phoenixkahlo.messaging.messagetypes.SendableCoder;

/*
 * Stores and retrieves resources in a given place on the hard drive
 * Has no memory cache, thus can handle very much data
 */
public class ResourceRepository {

	private SendableCoder coder;
	private String dirPath;
	
	public ResourceRepository(String dirPath, SendableCoder coder) throws IOException {
		this.coder = coder;
		this.dirPath = dirPath;
		File dir = new File(dirPath);
		dir.mkdirs();
	}
	
	public void addResource(Resource resource) {
		File file = new File(dirPath + File.separator + resource.getResourceID());
		try {
			file.createNewFile();
			OutputStream out = new FileOutputStream(file);
			coder.write(resource, out);
		} catch (IOException e) {
			System.err.println("Failed to write resource to repository");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public Resource getResource(String resourceID) {
		File file = new File(dirPath + File.separator + resourceID);
		try {
			InputStream in = new FileInputStream(file);
			return (Resource) coder.read(in);
		} catch (IOException e) {
			System.err.println("Failed to read resource from repository");
			e.printStackTrace();
			System.exit(1);
			throw new RuntimeException();
		}
	}
	
}
