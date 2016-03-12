package com.phoenixkahlo.messaging.messagetypes;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JPanel;

import com.phoenixkahlo.messaging.swingutils.SizeLimitedImagePanel;
import com.phoenixkahlo.messaging.utils.IntCallback;
import com.phoenixkahlo.messaging.utils.Protocol;
import com.phoenixkahlo.messaging.utils.ResourceRepository;

public class ImageMessage extends ResourceReadyMessage {

	private String resourceID;
	private IntCallback widthLimiter;
	
	public ImageMessage(String name, String resourceID) {
		super(name);
		this.resourceID = resourceID;
	}
	
	public ImageMessage(InputStream in) throws IOException {
		super(in);
		resourceID = Protocol.readString(in);
	}
	
	@Override
	public void write(OutputStream out) throws IOException {
		super.write(out);
		Protocol.writeString(resourceID, out);
	}
	
	@Override
	public Component toComponent() {
		JPanel wrapper = new JPanel();
		ResourceRepository repository = getResourceRepository();
		repository.waitFor(new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					BufferedImage image = ((ImageResource) repository.getResource(resourceID)).getImage();
					SizeLimitedImagePanel panel = new SizeLimitedImagePanel(image, getFrameWidthGetter(), () -> 300);
					wrapper.add(panel);
					wrapper.revalidate();
					wrapper.updateUI();
				} catch (IOException e) {
					System.err.println("Failed to display ImageMessage image");
					e.printStackTrace();
				}
			}
		}), resourceID);
		return wrapper;
	}
	
	@Override
	public String toString() {
		return super.toString() + "image=" + resourceID;
	}
	
}
