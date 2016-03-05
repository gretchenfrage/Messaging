package com.phoenixkahlo.messaging.testing;

import com.phoenixkahlo.messaging.messagetypes.TextMessage;
import com.phoenixkahlo.messaging.server.ServerFrame;

public class FrameTester {

	public static void main(String[] args) throws InterruptedException {
		ServerFrame frame = new ServerFrame();
		frame.start();
		frame.addComponent(new TextMessage("me", "Never gONNA GIVE TYOU UP!").toComponent());
		frame.addComponent(new TextMessage("you", "According to all known laws of aviation, there is no way a bee should be able to fly. Its wings are too small to get its fat little body off the ground. The bee, of course, flies anyway because bees don�t care what humans think is impossible.").toComponent());
		frame.addComponent(new TextMessage("us", "We begin on Christmas Eve with me, Mark, and my roommate, Roger. We live in an industrial loft on the corner of 11th street and Avenue B, the top floor of what was once a music publishing factory. Old rock 'n' roll posters hang on the walls. They have Roger's picture advertising gigs at CBGB's and the Pyramid Club. We have an illegal wood burning stove; its exhaust pipe crawls up to a skylight. All of our electrical appliances are plugged into one thick extension cord which snakes its way out a window. Outside, a small tent city has sprung up in the lot next to our building. Inside, we are freezing because we have no heat.").toComponent());
	}

}
