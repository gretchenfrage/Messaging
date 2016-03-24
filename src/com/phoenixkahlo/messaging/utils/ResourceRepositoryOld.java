package com.phoenixkahlo.messaging.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.phoenixkahlo.messaging.messagetypes.ResourceOld;
import com.phoenixkahlo.messaging.messagetypes.SendableCoder;

/*
 * Stores and retrieves resources in a given place on the hard drive
 * Has no memory cache, thus can handle very much data
 */
public class ResourceRepositoryOld {

	private SendableCoder coder;
	private String dirPath;
	// The following threads are waiting to be called once the resources with the given ID are present
	private Map<String, List<Thread>> waiting = new HashMap<String, List<Thread>>();
	
	public ResourceRepositoryOld(String dirPath, SendableCoder coder) throws IOException {
		this.coder = coder;
		this.dirPath = dirPath;
		File dir = new File(dirPath);
		dir.mkdirs();
	}
	
	public void waitFor(Thread callback, String resourceID) {
		synchronized (waiting) {
			if (resourceExists(resourceID)) { // Resource already exists, run immediately
				callback.start();
			} else { // Resource does not exist, run later
				if (!waiting.containsKey(resourceID))
					waiting.put(resourceID, new ArrayList<Thread>());
				waiting.get(resourceID).add(callback);
			}
		}
	}
	
	public void addResource(ResourceOld resource) {
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
		synchronized (waiting) {
			if (waiting.containsKey(resource.getResourceID())) {
				for (Thread thread : waiting.get(resource.getResourceID())) {
					thread.start();
				}
				waiting.remove(resource.getResourceID());
			}
		}
	}
	
	public boolean resourceExists(String resourceID) {
		File file = new File(dirPath + File.separator + resourceID);
		return file.exists();
	}
	
	/*
	 * Assumes resource exists, will crash if it doesn't
	 * Should be called after calling resourceExists to ensure exists
	 */
	public ResourceOld getResource(String resourceID) {
		File file = new File(dirPath + File.separator + resourceID);
		try {
			InputStream in = new FileInputStream(file);
			return (ResourceOld) coder.read(in);
		} catch (IOException e) {
			System.err.println("Failed to read resource from repository");
			e.printStackTrace();
			System.exit(1);
			throw new RuntimeException();
		}
	}
	
}
