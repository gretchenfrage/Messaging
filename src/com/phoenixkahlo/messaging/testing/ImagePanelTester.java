package com.phoenixkahlo.messaging.testing;

import java.io.File;
import java.io.IOException;

import com.phoenixkahlo.messaging.server.ServerFrame;
import com.phoenixkahlo.messaging.swingutils.SizeLimitedImagePanel;

public class ImagePanelTester {

	public static void main(String[] args) throws IOException {
		ServerFrame frame = new ServerFrame();
		File image = new File("freeman.jpeg");
		frame.addComponent(new SizeLimitedImagePanel(image, () -> frame.getWidth(), () -> 300));
		frame.addComponent(new SizeLimitedImagePanel(image, () -> frame.getWidth(), () -> 300));
		frame.addComponent(new SizeLimitedImagePanel(image, () -> frame.getWidth(), () -> 300));
		frame.addComponent(new SizeLimitedImagePanel(image, () -> frame.getWidth(), () -> 300));
		frame.addComponent(new SizeLimitedImagePanel(image, () -> frame.getWidth(), () -> 300));
		frame.addComponent(new SizeLimitedImagePanel(image, () -> frame.getWidth(), () -> 300));
		frame.addComponent(new SizeLimitedImagePanel(image, () -> frame.getWidth(), () -> 300));
		frame.addComponent(new SizeLimitedImagePanel(image, () -> frame.getWidth(), () -> 300));
		frame.addComponent(new SizeLimitedImagePanel(image, () -> frame.getWidth(), () -> 300));
		frame.addComponent(new SizeLimitedImagePanel(image, () -> frame.getWidth(), () -> 300));
		frame.addComponent(new SizeLimitedImagePanel(image, () -> frame.getWidth(), () -> 300));
		frame.addComponent(new SizeLimitedImagePanel(image, () -> frame.getWidth(), () -> 300));
		frame.addComponent(new SizeLimitedImagePanel(image, () -> frame.getWidth(), () -> 300));
		frame.addComponent(new SizeLimitedImagePanel(image, () -> frame.getWidth(), () -> 300));
		frame.addComponent(new SizeLimitedImagePanel(image, () -> frame.getWidth(), () -> 300));
		frame.start();
	}
	
}
